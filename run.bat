call build.bat

chcp 932

cd build
java -classpath .;../lib/* calendar.Main
cd ..
