import base64
import os
from xml.sax.saxutils import escape

import diagrams

RESOURCES = os.path.join(os.path.dirname(os.path.dirname(diagrams.__file__)), "resources")
FONT = "Helvetica, Arial, sans-serif"
INK = "#232f3e"
MUTED = "#5f6b7a"
LINE = "#4b5563"

CLOUD = "#232f3e"
REGION = "#00a4a6"
VPC = "#8c4fff"
ZONE = "#147eba"
PUBLIC = "#7aa116"
PRIVATE = "#00a4a6"
SCALING = "#ed7100"
CLUSTER = "#ed7100"
INSTANCE = "#7d8998"
CHIP = "#9aa3ad"
POD = "#326ce5"

SHARED = INK
QUERY = "#1a6fd1"
REGISTRATION = "#c2185b"

out = []


def add(svg):
    out.append(svg)


def text(x, y, content, size=13, weight="normal", color=INK, anchor="start", halo=False):
    attrs = f'x="{x}" y="{y}" font-family="{FONT}" font-size="{size}" font-weight="{weight}" text-anchor="{anchor}"'
    if halo:
        add(f'<text {attrs} fill="#ffffff" stroke="#ffffff" stroke-width="5">{escape(content)}</text>')
    add(f'<text {attrs} fill="{color}">{escape(content)}</text>')


def box(x, y, w, h, stroke, dash=False, fill="none", width=1.6, radius=8):
    dashes = ' stroke-dasharray="7 5"' if dash else ""
    add(f'<rect x="{x}" y="{y}" width="{w}" height="{h}" rx="{radius}" fill="{fill}" '
        f'stroke="{stroke}" stroke-width="{width}"{dashes}/>')


def icon(name, x, y, size):
    provider = name.split("/")[0]
    path = name if provider in ("aws", "k8s", "onprem") else "aws/" + name
    with open(os.path.join(RESOURCES, path + ".png"), "rb") as f:
        data = base64.b64encode(f.read()).decode()
    add(f'<image x="{x}" y="{y}" width="{size}" height="{size}" href="data:image/png;base64,{data}"/>')


def arrow(points, dash=False):
    pts = " ".join(f"{px},{py}" for px, py in points)
    dashes = ' stroke-dasharray="6 5"' if dash else ""
    add(f'<polyline points="{pts}" fill="none" stroke="{LINE}" stroke-width="1.6"{dashes} marker-end="url(#head)"/>')


def badge(n, x, y, color, r=11):
    add(f'<circle cx="{x}" cy="{y}" r="{r}" fill="{color}"/>')
    text(x, y + 4.5, str(n), size=13 if r == 11 else 11, weight="bold", color="#ffffff", anchor="middle")


def node(icon_name, x, y, title, lines, size=48, label_dx=58, anchor="start"):
    icon(icon_name, x, y, size)
    ty = y + size / 2 - 4 * len(lines) + 4
    tx = x + label_dx if anchor == "start" else x - 10
    text(tx, ty, title, weight="bold", anchor=anchor)
    for i, content in enumerate(lines):
        text(tx, ty + 18 * (i + 1), content, size=12, color=MUTED, anchor=anchor)


def start(width, height):
    add(f'<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {width} {height}" width="{width}" height="{height}">')
    add('<defs><marker id="head" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" '
        f'orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10 z" fill="{LINE}"/></marker></defs>')
    add(f'<rect width="{width}" height="{height}" fill="#ffffff"/>')


def save(name):
    add("</svg>")
    with open(os.path.join(os.path.dirname(os.path.abspath(__file__)), name), "w", encoding="utf-8") as f:
        f.write("\n".join(out))
