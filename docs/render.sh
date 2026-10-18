#!/usr/bin/env bash
# Renders docs/data-model.puml into docs/data-model.svg.
# Needs Java and Graphviz (the `dot` command, for example `sudo apt install graphviz`).
# Set PLANTUML_JAR to use a jar you already have; otherwise a pinned version is cached in ~/.cache/finmathly.
set -euo pipefail

version=1.2024.7
cd "$(dirname "$0")"

jar="${PLANTUML_JAR:-$HOME/.cache/finmathly/plantuml-$version.jar}"
if [ ! -f "$jar" ]; then
  mkdir -p "$(dirname "$jar")"
  curl -fsSL -o "$jar" "https://github.com/plantuml/plantuml/releases/download/v$version/plantuml-$version.jar"
fi

java -jar "$jar" -tsvg data-model.puml
