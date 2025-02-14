@echo off
cd delphi
call gradlew.bat build
cd ..
copy delphi\app\build\libs\app.jar delphi.jar
echo Done!
pause