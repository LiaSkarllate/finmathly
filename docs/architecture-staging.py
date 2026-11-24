from diagram_kit import (CLOUD, INSTANCE, LINE, MUTED, POD, PUBLIC, REGION, VPC, ZONE, add, arrow, box, icon, text)
from diagram_kit import save, start

WIDTH, HEIGHT = 1670, 836
start(WIDTH, HEIGHT)


def labeled_icon(name, x, y, size, title, detail):
    icon(name, x, y, size)
    text(x + size / 2, y + size + 18, title, weight="bold", anchor="middle")
    text(x + size / 2, y + size + 34, detail, size=12, color=MUTED, anchor="middle")


def pod(x, y, w, title, detail, icon_name="k8s/compute/pod"):
    box(x, y, w, 44, POD, fill="#ffffff", width=1.2, radius=6)
    icon(icon_name, x + 8, y + 8, 28)
    text(x + 44, y + 19, title, size=12, weight="bold")
    text(x + 44, y + 35, detail, size=11, color=MUTED)


labeled_icon("general/users", 60, 58, 64, "Maintainers", "browser or API client")
box(200, 30, 1300, 700, CLOUD, width=2.2, radius=6)

box(215, 60, 1270, 650, REGION, dash=True)
text(232, 83, "Region us-east-1 (N. Virginia)", weight="bold", color=REGION)

arrow([(124, 92), (652, 92), (652, 144)])
text(540, 86, "fetches frontend files", size=12, color=MUTED, anchor="middle", halo=True)
arrow([(124, 106), (628, 106), (628, 144)])
text(520, 122, "calls the API", size=12, color=MUTED, anchor="middle", halo=True)

add('<g transform="translate(0, 30)">')
labeled_icon("onprem/ci/github-actions", 1562, 285, 56, "GitHub Actions", "CI/CD workflows")
labeled_icon("compute/ec2-container-registry", 1230, 170, 56, "Amazon ECR", "images, tag staging")
labeled_icon("management/systems-manager-run-command", 1230, 400, 56, "AWS Systems Manager", "Run Command")

box(245, 140, 900, 515, VPC)
text(262, 162, "VPC 10.1.0.0/16 · staging", weight="bold", color=VPC)
box(268, 190, 850, 445, ZONE, dash=True)
text(284, 211, "Availability zone a", weight="bold", color=ZONE)
box(288, 225, 810, 390, PUBLIC, fill="#f4f8ea")
text(302, 246, "Public subnet · 10.1.1.0/24", weight="bold", color=PUBLIC)
box(310, 265, 765, 330, INSTANCE, fill="#ffffff")
icon("compute/ec2-instance", 320, 270, 20)
text(346, 285, "EC2 instance · t4g.medium · public IP", size=12, weight="bold", color=INSTANCE)
icon("onprem/container/k3s", 320, 296, 20)
text(346, 311, "k3s, single node", size=12, weight="bold", color=POD)

icon("network/internet-gateway", 616, 116, 48)
text(606, 136, "Internet Gateway", weight="bold", anchor="end")

arrow([(640, 164), (640, 298)])
icon("onprem/network/traefik", 618, 300, 44)
text(672, 318, "Traefik", weight="bold")
text(672, 336, "ingress controller", size=12, color=MUTED)
text(800, 366, "routes by URL path", size=11, color=MUTED, anchor="middle", halo=True)
add(f'<polyline points="640,344 640,372" fill="none" stroke="{LINE}" stroke-width="1.6"/>')
add(f'<polyline points="440,372 940,372" fill="none" stroke="{LINE}" stroke-width="1.6"/>')
arrow([(440, 372), (440, 398)])
arrow([(690, 372), (690, 398)])
arrow([(940, 372), (940, 398)])

pod(330, 400, 220, "frontend", "web server, React files")
pod(580, 400, 220, "registry service", "1 replica")
pod(830, 400, 220, "calculation service", "1 replica")

pod(330, 530, 220, "PostgreSQL", "in place of RDS", "onprem/database/postgresql")
pod(580, 530, 220, "ElasticMQ", "in place of SQS")
pod(830, 530, 220, "DynamoDB local", "in place of DynamoDB", "database/dynamodb")
arrow([(620, 444), (620, 500), (440, 500), (440, 528)])
text(530, 493, "writes", size=11, color=MUTED, anchor="middle", halo=True)
arrow([(720, 444), (720, 528)])
text(712, 490, "publishes", size=11, color=MUTED, anchor="end", halo=True)
arrow([(870, 444), (870, 486), (760, 486), (760, 528)])
text(815, 479, "consumes", size=11, color=MUTED, anchor="middle", halo=True)
arrow([(960, 444), (960, 528)])
text(968, 490, "reads and writes", size=11, color=MUTED, halo=True)

arrow([(646, 265), (646, 166)], dash=True)
text(638, 208, "pulls images", size=11, color=MUTED, anchor="end", halo=True)
arrow([(658, 265), (658, 166)])
text(666, 228, "fetches commands", size=11, color=MUTED, halo=True)
arrow([(664, 124), (1170, 124), (1170, 198), (1228, 198)], dash=True)
arrow([(664, 146), (1200, 146), (1200, 428), (1228, 428)])

add(f'<polyline points="1560,313 1430,313" fill="none" stroke="{LINE}" stroke-width="1.6"/>')
arrow([(1430, 313), (1430, 198), (1288, 198)])
text(1354, 190, "publishes images", size=11, color=MUTED, anchor="middle", halo=True)
arrow([(1430, 313), (1430, 428), (1288, 428)])
text(1364, 420, "starts rollouts", size=11, color=MUTED, anchor="middle", halo=True)

box(200, 715, 1300, 70, "#c3c9d0", width=1.2, radius=6)
text(218, 737, "Legend", size=12, weight="bold")
arrow([(300, 733), (340, 733)])
text(350, 737, "request or data flow", size=12, color=MUTED)
arrow([(540, 733), (580, 733)], dash=True)
text(590, 737, "file fetch", size=12, color=MUTED)
swatches = (("AWS Cloud", CLOUD, False, "none"), ("Region", REGION, True, "none"), ("VPC", VPC, False, "none"),
            ("Availability zone", ZONE, True, "none"), ("Public subnet", PUBLIC, False, "none"),
            ("EC2 instance", INSTANCE, False, "none"), ("Kubernetes pod", POD, False, "#ffffff"))
x = 218
for label, color, dashed, fill in swatches:
    box(x, 755, 30, 16, color, dash=dashed, width=1.4, radius=3, fill=fill)
    text(x + 38, 767, label, size=12, color=MUTED)
    x += 38 + round(6.2 * len(label)) + 40
add("</g>")

save("architecture-staging.svg")
