#!/bin/zsh

./build.sh

cd build 
jar cfm ../calendar.jar ../manifest.txt $(fd -e .class --no-ignore) ../lib/* 
cd ..
