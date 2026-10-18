#!/usr/bin/env bash
# Renders the diagrams of this folder:
#   data-model.puml -> data-model.svg   (PlantUML)
#   architecture.py -> architecture.svg (Python, with the official AWS icons of the package "diagrams")
# Needs Java, Python 3 and Graphviz (the `dot` command, for example `sudo apt install graphviz`).
# Pinned versions of PlantUML and of "diagrams" are downloaded into ~/.cache/finmathly.
# Set PLANTUML_JAR to use a PlantUML jar you already have.
set -euo pipefail

plantuml_version=1.2024.7
diagrams_version=0.24.4
cache="$HOME/.cache/finmathly"
cd "$(dirname "$0")"

jar="${PLANTUML_JAR:-$cache/plantuml-$plantuml_version.jar}"
if [ ! -f "$jar" ]; then
  mkdir -p "$(dirname "$jar")"
  curl -fsSL -o "$jar" "https://github.com/plantuml/plantuml/releases/download/v$plantuml_version/plantuml-$plantuml_version.jar"
fi
java -jar "$jar" -tsvg data-model.puml

venv="$cache/venv-diagrams-$diagrams_version"
if [ ! -x "$venv/bin/python" ]; then
  python3 -m venv "$venv"
  "$venv/bin/pip" install --quiet "diagrams==$diagrams_version"
fi
"$venv/bin/python" architecture.py
