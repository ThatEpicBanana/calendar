if not exist "build" mkdir build
javac -cp .;lib/* -sourcepath src -d build src/calendar/Main.java
