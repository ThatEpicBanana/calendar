#!/bin/zsh

./build.sh

cd build
java -classpath ".:../lib/*" calendar.Main "$@"
cd ..
