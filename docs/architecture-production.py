from diagram_kit import (CHIP, CLOUD, CLUSTER, INSTANCE, LINE, MUTED, PRIVATE, PUBLIC, QUERY, REGION, REGISTRATION,
                         SCALING, SHARED, VPC, ZONE, add, arrow, badge, box, icon, node, save, start, text)

WIDTH, HEIGHT = 1924, 1131
start(WIDTH, HEIGHT)
add('<g transform="translate(0, 14)">')

icon("general/users", 60, 58, 64)
text(92, 140, "Users", anchor="middle", weight="bold")
text(92, 156, "browser", size=12, color=MUTED, anchor="middle")
icon("general/traditional-server", 60, 200, 64)
text(92, 282, "Bank systems", anchor="middle", weight="bold")
text(92, 298, "API client", size=12, color=MUTED, anchor="middle")
box(200, 30, 1520, 960, CLOUD, width=2.2, radius=6)

icon("network/cloudfront", 240, 62, 56)
text(268, 136, "Amazon CloudFront", weight="bold", anchor="middle")
text(268, 152, "Edge locations", size=12, color=MUTED, anchor="middle")
arrow([(126, 90), (238, 90)])
text(162, 70, "fetches", size=11, color=MUTED, anchor="middle", halo=True)
text(162, 83, "frontend files", size=11, color=MUTED, anchor="middle", halo=True)
arrow([(302, 90), (1468, 90), (1468, 263)], dash=True)
text(880, 82, "fetches frontend files", size=12, color=MUTED, anchor="middle")

box(215, 175, 1490, 775, REGION, dash=True)
text(232, 198, "Region us-east-1 (N. Virginia)", weight="bold", color=REGION)

node("storage/simple-storage-service-s3", 1440, 265, "Amazon S3", ["React frontend"], size=56, label_dx=68)
node("integration/simple-queue-service-sqs", 1440, 435, "Amazon SQS", ["security and index value snapshots"], size=56, label_dx=68)
node("compute/ec2-container-registry", 1440, 604, "Amazon ECR", ["images, tag production"], size=56, label_dx=68)
node("database/dynamodb", 1440, 774, "Amazon DynamoDB", ["on-demand", "calculation service data"], size=56, label_dx=68)

box(245, 240, 1146, 690, VPC)
text(262, 262, "VPC 10.0.0.0/16", weight="bold", color=VPC)
icon("network/internet-gateway", 794, 211, 48)
text(850, 231, "Internet Gateway", weight="bold")

for zone, zx, public_cidr, private_cidr in (("a", 268, "10.0.1.0/24", "10.0.2.0/24"),
                                           ("b", 938, "10.0.3.0/24", "10.0.4.0/24")):
    box(zx, 285, 430, 610, ZONE, dash=True)
    text(zx + 16, 306, f"Availability zone {zone}", weight="bold", color=ZONE)

    sx = zx + 20
    box(sx, 320, 390, 115, PUBLIC, fill="#f4f8ea")
    text(sx + 14, 341, f"Public subnet · {public_cidr}", weight="bold", color=PUBLIC)
    if zone == "a":
        node("network/nat-gateway", zx + 24, 352, "NAT Gateway a", ["1 public IP"])
    else:
        node("network/nat-gateway", zx + 346, 352, "NAT Gateway b", ["1 public IP"], anchor="end")

    box(sx, 490, 390, 380, PRIVATE, fill="#e9f7f7")
    text(sx + 14, 511, f"Private subnet · {private_cidr}", weight="bold", color=PRIVATE)

    role = "primary" if zone == "a" else "standby"
    rx = zx + 69 if zone == "a" else zx + 49
    node("database/rds-postgresql-instance", rx, 790, "Amazon RDS for PostgreSQL",
         [f"{role} · db.t4g.micro"], size=44, label_dx=54)

box(300, 518, 1036, 226, CLUSTER, width=1.8)
icon("compute/elastic-container-service", 308, 521, 20)
text(334, 536, "Amazon ECS cluster", size=12, weight="bold", color=CLUSTER)
box(312, 545, 1012, 190, SCALING, dash=True)
text(326, 562, "Auto Scaling group · 2 to 4 EC2 instances · both zones", size=11, weight="bold", color=SCALING)


def task(x, y, title, detail, stacked=False):
    if stacked:
        box(x + 6, y - 6, 240, 44, CLUSTER, fill="#ffffff", width=1.2, radius=6)
    box(x, y, 240, 44, CLUSTER, fill="#ffffff", width=1.2, radius=6)
    icon("compute/elastic-container-service-container", x + 8, y + 8, 28)
    text(x + 44, y + 19, title, size=12, weight="bold")
    text(x + 44, y + 35, detail, size=11, color=MUTED)


for ix in (322, 976):
    box(ix, 570, 340, 150, INSTANCE, fill="#ffffff")
    icon("compute/ec2-instance", ix + 10, 574, 20)
    text(ix + 36, 589, "EC2 instance · t4g.small", size=12, weight="bold", color=INSTANCE)
    task(ix + 18, 600, "registry service", "tasks · 1 or 2 per zone")
    task(ix + 18, 658, "calculation service", "tasks · 2 to 4 per zone", stacked=True)

icon("network/elb-application-load-balancer", 794, 330, 48)
text(818, 396, "Application", size=12, weight="bold", anchor="middle")
text(818, 410, "Load Balancer", size=12, weight="bold", anchor="middle")
box(718, 418, 200, 34, CHIP, fill="#f3f4f6", width=1.2, radius=6)
text(818, 432, "registry service", size=11, weight="bold", anchor="middle")
text(818, 446, "target group", size=11, color=MUTED, anchor="middle")
box(752, 460, 132, 34, CHIP, fill="#f3f4f6", width=1.2, radius=6)
text(818, 474, "calculation service", size=11, weight="bold", anchor="middle")
text(818, 488, "target group", size=11, color=MUTED, anchor="middle")

arrow([(124, 110), (160, 110), (160, 170), (818, 170), (818, 209)])
add(f'<polyline points="124,232 160,232 160,170" fill="none" stroke="{LINE}" stroke-width="1.6"/>')
badge(1, 420, 170, SHARED)
text(440, 164, "calls the API", size=12, color=MUTED)
arrow([(818, 259), (818, 328)])
badge(2, 818, 292, SHARED)
text(836, 296, "enters the VPC", size=12, color=MUTED, halo=True)

arrow([(728, 452), (728, 622), (580, 622)])
arrow([(908, 452), (908, 622), (994, 622)])
badge(3, 728, 515, REGISTRATION)
badge(3, 908, 515, REGISTRATION)
text(648, 615, "routes registrations", size=11, color=MUTED, anchor="middle", halo=True)
arrow([(340, 622), (316, 622), (316, 812), (335, 812)])
badge(4, 316, 768, REGISTRATION)
text(332, 772, "writes", size=11, color=MUTED, halo=True)
arrow([(590, 812), (985, 812)], dash=True)
text(818, 805, "replicates synchronously", size=11, color=MUTED, anchor="middle", halo=True)

arrow([(772, 494), (772, 680), (580, 680)])
arrow([(864, 494), (864, 680), (994, 680)])
badge(8, 772, 560, QUERY)
badge(8, 864, 560, QUERY)
text(652, 673, "routes price queries", size=11, color=MUTED, anchor="middle", halo=True)

arrow([(1290, 570), (1290, 402)], dash=True)
text(1278, 468, "pulls images", size=11, color=MUTED, anchor="end", halo=True)
arrow([(1234, 622), (1302, 622), (1302, 402)])
badge(5, 1302, 595, REGISTRATION)
text(1288, 599, "publishes", size=11, color=MUTED, anchor="end", halo=True)
arrow([(1234, 680), (1314, 680), (1314, 402)])
badge(6, 1314, 651, REGISTRATION)
text(1300, 655, "consumes", size=11, color=MUTED, anchor="end", halo=True)
arrow([(1332, 376), (1420, 376), (1420, 463), (1438, 463)])
arrow([(1332, 392), (1406, 392), (1406, 632), (1438, 632)], dash=True)

arrow([(1314, 680), (1314, 802), (1369, 802)])
icon("network/endpoint", 1371, 782, 40)
text(1391, 837, "gateway endpoint", size=11, color=MUTED, anchor="middle", halo=True)
arrow([(1411, 802), (1438, 802)])
badge(9, 1314, 752, QUERY)
text(1300, 756, "reads", size=11, color=MUTED, anchor="end", halo=True)
badge(7, 1314, 780, REGISTRATION)
text(1300, 784, "writes", size=11, color=MUTED, anchor="end", halo=True)

icon("onprem/ci/github-actions", 1800, 400, 56)
text(1828, 474, "GitHub Actions", weight="bold", anchor="middle")
text(1828, 490, "CI/CD workflows", size=12, color=MUTED, anchor="middle")
add(f'<polyline points="1798,428 1760,428" fill="none" stroke="{LINE}" stroke-width="1.6"/>')
add(f'<polyline points="1760,240 1760,730" fill="none" stroke="{LINE}" stroke-width="1.6"/>')
arrow([(1760, 240), (1760, 20), (268, 20), (268, 60)])
text(1000, 15, "invalidates cached files", size=11, color=MUTED, anchor="middle", halo=True)
arrow([(1760, 240), (1484, 240), (1484, 263)])
text(1622, 234, "copies frontend files", size=11, color=MUTED, anchor="middle", halo=True)
arrow([(1760, 580), (1484, 580), (1484, 602)])
text(1622, 574, "publishes images", size=11, color=MUTED, anchor="middle", halo=True)
arrow([(1760, 730), (1338, 730)])
text(1560, 724, "starts ECS deployments", size=11, color=MUTED, anchor="middle", halo=True)

box(200, 1005, 1520, 90, "#c3c9d0", width=1.2, radius=6)
text(218, 1027, "Legend", size=12, weight="bold")
arrow([(300, 1023), (340, 1023)])
text(350, 1027, "request or data flow", size=12, color=MUTED)
arrow([(540, 1023), (580, 1023)], dash=True)
text(590, 1027, "file fetch or replication", size=12, color=MUTED)
box(790, 1013, 36, 20, SCALING, dash=True, width=1.4, radius=3)
text(836, 1027, "Auto Scaling group", size=12, color=MUTED)
x = 1010
for n, color, label in ((1, SHARED, "both paths"), (3, REGISTRATION, "registration"), (8, QUERY, "price query")):
    badge(n, x, 1023, color, r=9)
    text(x + 17, 1027, label, size=12, color=MUTED)
    x += 17 + round(6.2 * len(label)) + 40
swatches = (("AWS Cloud", CLOUD, False), ("Region", REGION, True), ("VPC", VPC, False), ("Availability zone", ZONE, True),
            ("Public subnet", PUBLIC, False), ("Private subnet", PRIVATE, False), ("ECS cluster", CLUSTER, False),
            ("EC2 instance", INSTANCE, False), ("ECS task", CLUSTER, False), ("Target group", CHIP, False))
chip_fills = {"ECS task": "#ffffff", "Target group": "#f3f4f6"}
x = 218
for label, color, dashed in swatches:
    box(x, 1063, 30, 16, color, dash=dashed, width=1.4, radius=3, fill=chip_fills.get(label, "none"))
    text(x + 38, 1075, label, size=12, color=MUTED)
    x += 38 + round(6.2 * len(label)) + 40

add("</g>")
save("architecture-production.svg")
