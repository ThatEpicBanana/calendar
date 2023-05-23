#!/bin/zsh

mkdir -p build
javac -d build $(fd -e .java)
