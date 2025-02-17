cd delphi
./gradlew clean
./gradlew build
cd ..
cp delphi/app/build/libs/app.jar delphi.jar
echo "Done!"