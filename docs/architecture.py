"""Architecture of finmathly on AWS, drawn as SVG. Rendered into docs/architecture.svg by docs/render.sh.

The layout is explicit (coordinates), because the diagram follows the usual AWS conventions:
a region with a VPC, two availability zones, and a public and a private subnet in each one.
The icons are the official AWS icons shipped with the Python package "diagrams".
"""
import base64
import os
from xml.sax.saxutils import escape

import diagrams

ICONS = os.path.join(os.path.dirname(os.path.dirname(diagrams.__file__)), "resources", "aws")
FONT = "Helvetica, Arial, sans-serif"
INK = "#232f3e"
MUTED = "#5f6b7a"
LINE = "#4b5563"

# Colors of the boundaries follow the AWS architecture diagram conventions.
CLOUD = "#232f3e"
REGION = "#00a4a6"
VPC = "#8c4fff"
ZONE = "#147eba"
PUBLIC = "#7aa116"
PRIVATE = "#00a4a6"
SCALING = "#ed7100"

out = []


def add(svg):
    out.append(svg)


def text(x, y, content, size=13, weight="normal", color=INK, anchor="start", mono=False, italic=False, halo=False):
    family = "Menlo, Consolas, monospace" if mono else FONT
    style = ' font-style="italic"' if italic else ""
    if halo:
        add(f'<text x="{x}" y="{y}" font-family="{family}" font-size="{size}" font-weight="{weight}" '
            f'fill="#ffffff" stroke="#ffffff" stroke-width="5" text-anchor="{anchor}"{style}>{escape(content)}</text>')
    add(f'<text x="{x}" y="{y}" font-family="{family}" font-size="{size}" font-weight="{weight}" '
        f'fill="{color}" text-anchor="{anchor}"{style}>{escape(content)}</text>')


def box(x, y, w, h, stroke, dash=False, fill="none", width=1.6, radius=8):
    dashes = ' stroke-dasharray="7 5"' if dash else ""
    add(f'<rect x="{x}" y="{y}" width="{w}" height="{h}" rx="{radius}" fill="{fill}" '
        f'stroke="{stroke}" stroke-width="{width}"{dashes}/>')


def icon(name, x, y, size):
    with open(os.path.join(ICONS, name + ".png"), "rb") as f:
        data = base64.b64encode(f.read()).decode()
    add(f'<image x="{x}" y="{y}" width="{size}" height="{size}" href="data:image/png;base64,{data}"/>')


def arrow(points, dash=False, color=LINE):
    pts = " ".join(f"{px},{py}" for px, py in points)
    dashes = ' stroke-dasharray="6 5"' if dash else ""
    add(f'<polyline points="{pts}" fill="none" stroke="{color}" stroke-width="1.6"{dashes} marker-end="url(#head)"/>')


def line(points, dash=False, color=LINE):
    pts = " ".join(f"{px},{py}" for px, py in points)
    dashes = ' stroke-dasharray="6 5"' if dash else ""
    add(f'<polyline points="{pts}" fill="none" stroke="{color}" stroke-width="1.6"{dashes}/>')


def badge(n, x, y):
    add(f'<circle cx="{x}" cy="{y}" r="11" fill="{INK}"/>')
    text(x, y + 4.5, str(n), size=13, weight="bold", color="#ffffff", anchor="middle")


def node(icon_name, x, y, title, lines, size=48, label_dx=58):
    icon(icon_name, x, y, size)
    ty = y + size / 2 - 4 * len(lines) + 4
    text(x + label_dx, ty, title, weight="bold")
    for i, content in enumerate(lines):
        text(x + label_dx, ty + 18 * (i + 1), content, size=12, color=MUTED, mono=content.startswith("sg-"))


WIDTH, HEIGHT = 1600, 1135
add(f'<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {WIDTH} {HEIGHT}" width="{WIDTH}" height="{HEIGHT}">')
add('<defs><marker id="head" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" '
    f'orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10 z" fill="{LINE}"/></marker></defs>')
add(f'<rect width="{WIDTH}" height="{HEIGHT}" fill="#ffffff"/>')

# Users and the AWS Cloud
icon("general/users", 60, 62, 64)
text(92, 152, "Users and", anchor="middle", weight="bold")
text(92, 169, "bank systems", anchor="middle", size=12, color=MUTED)
box(200, 30, 1380, 980, CLOUD, width=2.2, radius=6)
text(218, 56, "AWS Cloud", weight="bold")

# CloudFront and the front-end
icon("network/cloudfront", 240, 62, 56)
text(268, 136, "Amazon CloudFront", weight="bold", anchor="middle")
text(268, 152, "Edge locations", size=12, color=MUTED, anchor="middle")
arrow([(126, 90), (238, 90)])
arrow([(302, 90), (1358, 90), (1358, 262)], dash=True)
text(800, 82, "fetches the front-end files", size=12, color=MUTED, anchor="middle")

# Region
box(215, 175, 1350, 820, REGION, dash=True)
text(232, 198, "Region us-east-1 (N. Virginia)", weight="bold", color=REGION)

# Services outside the VPC
node("storage/simple-storage-service-s3", 1330, 265, "Amazon S3", ["React front-end"], size=56, label_dx=68)
node("integration/simple-queue-service-sqs", 1330, 415, "Amazon SQS", ["snapshots and index values"], size=56, label_dx=68)
node("management/cloudwatch", 1330, 545, "Amazon CloudWatch", ["logs and metrics"], size=56, label_dx=68)
node("database/dynamodb", 1330, 675, "Amazon DynamoDB", ["on-demand", "calculation service data"], size=56, label_dx=68)

# VPC
box(245, 240, 1020, 730, VPC)
text(262, 262, "VPC 10.0.0.0/16", weight="bold", color=VPC)
icon("network/internet-gateway", 726, 211, 48)
text(782, 231, "Internet Gateway", weight="bold")

# Availability zones, subnets and instances
for zone, zx, nat_letter, public_cidr, private_cidr in (("a", 268, "a", "10.0.1.0/24", "10.0.2.0/24"),
                                                          ("b", 815, "b", "10.0.3.0/24", "10.0.4.0/24")):
    box(zx, 285, 417, 650, ZONE, dash=True)
    text(zx + 16, 306, f"Availability zone {zone}", weight="bold", color=ZONE)

    sx = zx + 20
    box(sx, 320, 377, 115, PUBLIC, fill="#f4f8ea")
    text(sx + 14, 341, f"Public subnet · {public_cidr}", weight="bold", color=PUBLIC)
    node("network/nat-gateway", sx + 22, 352, f"NAT Gateway {nat_letter}", ["zonal · 1 public IP"])
    text(sx + 14, 424, "route table: 0.0.0.0/0 → Internet Gateway", size=11, color=MUTED)

    box(sx, 490, 377, 420, PRIVATE, fill="#e9f7f7")
    text(sx + 14, 511, f"Private subnet · {private_cidr}", weight="bold", color=PRIVATE)

    box(sx + 15, 525, 347, 110, SCALING, dash=True)
    text(sx + 29, 544, "Auto Scaling group · calculation service · 2 to 4", size=11, weight="bold", color=SCALING)
    node("compute/ec2", sx + 29, 561, "calculation service", ["t4g.micro", "sg-calculation"], size=44, label_dx=54)

    box(sx + 15, 650, 347, 110, SCALING, dash=True)
    text(sx + 29, 669, "Auto Scaling group · registry service · exactly 2", size=11, weight="bold", color=SCALING)
    node("compute/ec2", sx + 29, 686, "registry service", ["t4g.micro", "sg-registry"], size=44, label_dx=54)

    if zone == "a":
        node("database/rds-postgresql-instance", sx + 29, 790, "RDS for PostgreSQL", ["primary · db.t4g.micro", "sg-rds"], size=44, label_dx=54)
    else:
        node("database/rds-postgresql-instance", sx + 29, 790, "RDS for PostgreSQL", ["standby · serves no reads", "sg-rds"], size=44, label_dx=54)
    text(sx + 14, 895, f"route table: 0.0.0.0/0 → NAT Gateway {nat_letter}", size=11, color=MUTED)

# Application Load Balancer, in the middle of the two zones
icon("network/elb-application-load-balancer", 726, 330, 48)
text(750, 396, "Application", size=12, weight="bold", anchor="middle")
text(750, 410, "Load Balancer", size=12, weight="bold", anchor="middle")
text(750, 425, "sg-alb", size=12, color=MUTED, anchor="middle", mono=True)

# Path of a request
arrow([(124, 116), (160, 116), (160, 170), (750, 170), (750, 209)])
badge(1, 420, 170)
text(440, 164, "API calls (HTTPS)", size=12, color=MUTED)
arrow([(750, 259), (750, 328)])
badge(2, 750, 292)
line([(750, 436), (750, 705)])
arrow([(750, 580), (653, 580)])
arrow([(750, 580), (847, 580)])
arrow([(750, 705), (653, 705)])
arrow([(750, 705), (847, 705)])
badge(3, 750, 470)
text(750, 574, "/api/calculations :8080", size=10, color=MUTED, anchor="middle", halo=True)
text(750, 699, "/api/securities :8080", size=10, color=MUTED, anchor="middle", halo=True)

# Registry database
arrow([(333, 758), (333, 788)])
text(341, 778, ":5432", size=11, color=MUTED)
arrow([(600, 812), (860, 812)], dash=True)
text(750, 805, "synchronous copy", size=11, color=MUTED, anchor="middle")

# DynamoDB through the gateway endpoint
arrow([(1197, 580), (1226, 580), (1226, 700), (1243, 700)])
icon("network/endpoint", 1243, 680, 40)
text(1263, 735, "gateway endpoint", size=11, color=MUTED, anchor="middle", halo=True)
arrow([(1283, 700), (1328, 700)])
badge(4, 1226, 640)

# SQS and CloudWatch through the NAT Gateway
add(f'<circle cx="1056" cy="376" r="3.5" fill="{LINE}"/>')
line([(1056, 376), (1300, 376), (1300, 573)])
arrow([(1300, 443), (1328, 443)])
arrow([(1300, 573), (1328, 573)])
badge(5, 1300, 410)
text(1330, 625, "Instances reach SQS and CloudWatch", size=11, color=MUTED, italic=True)
text(1330, 640, "through the NAT Gateway of their own zone.", size=11, color=MUTED, italic=True)

# Legend
box(200, 1030, 1380, 90, "#c3c9d0", width=1.2, radius=6)
text(218, 1052, "Legend", size=12, weight="bold")
arrow([(300, 1048), (340, 1048)])
text(350, 1052, "request or data flow", size=12, color=MUTED)
arrow([(540, 1048), (580, 1048)], dash=True)
text(590, 1052, "file fetch or replication", size=12, color=MUTED)
add(f'<circle cx="803" cy="1048" r="9" fill="{INK}"/>')
text(803, 1052, "1", size=11, weight="bold", color="#ffffff", anchor="middle")
text(820, 1052, "step of the paths described in the README", size=12, color=MUTED)
box(1130, 1038, 36, 20, SCALING, dash=True, width=1.4, radius=3)
text(1176, 1052, "Auto Scaling group", size=12, color=MUTED)
swatches = (("Region", REGION, True), ("VPC", VPC, False), ("Availability zone", ZONE, True),
            ("Public subnet", PUBLIC, False), ("Private subnet", PRIVATE, False))
x = 218
text(x, 1100, "Boundaries follow the AWS colors:", size=12, color=MUTED)
x = 440
for label, color, dashed in swatches:
    box(x, 1088, 30, 16, color, dash=dashed, width=1.4, radius=3)
    text(x + 38, 1100, label, size=12, color=MUTED)
    x += 38 + round(6.2 * len(label)) + 40

add("</svg>")

with open(os.path.join(os.path.dirname(os.path.abspath(__file__)), "architecture.svg"), "w", encoding="utf-8") as f:
    f.write("\n".join(out))
