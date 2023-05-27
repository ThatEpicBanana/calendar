#!/bin/zsh

mkdir -p build
javac -cp '.:lib/*' -d build $(fd -e .java)
