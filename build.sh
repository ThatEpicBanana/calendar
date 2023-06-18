#!/bin/zsh

mkdir -p build
javac -cp '.:lib/*' -d build -Xlint:deprecation "$@" $(fd -e .java)
