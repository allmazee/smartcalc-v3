#!/bin/bash

JAVA_VERSION=21
MAIN_JAR="SmartCalc-$PROJECT_VERSION.jar"
INSTALLER_TYPE=deb

echo "java home: $JAVA_HOME"
echo "project version: $PROJECT_VERSION"
echo "app version: $APP_VERSION"
echo "main JAR file: $MAIN_JAR"

rm -rfd ./target/java-runtime/
rm -rfd target/installer/

mkdir -p target/installer/input/libs/

cp target/libs/* target/installer/input/libs/
cp target/${MAIN_JAR} target/installer/input/libs/

echo "detecting required modules"
detected_modules=`$JAVA_HOME/bin/jdeps \
  -q \
  --multi-release ${JAVA_VERSION} \
  --ignore-missing-deps \
  --print-module-deps \
  --class-path "target/installer/input/libs/*" \
    target/classes/edu/school21/smartcalc/app/Main.class`
echo "detected modules: ${detected_modules}"

manual_modules=,jdk.crypto.ec,jdk.localedata,javafx.controls,javafx.fxml
echo "manual modules: ${manual_modules}"

echo "creating java runtime image"
$JAVA_HOME/bin/jlink \
  --module-path target/installer/input/libs \
  --add-modules "${detected_modules}${manual_modules}" \
  --include-locales=en,de \
  --output target/java-runtime

echo "Creating installer of type $INSTALLER_TYPE"

$JAVA_HOME/bin/jpackage \
--type $INSTALLER_TYPE \
--dest target/installer \
--input target/installer/input/libs \
--name smartcalc \
--main-class edu.school21.smartcalc.app.Main \
--main-jar ${MAIN_JAR} \
--java-options "-Xmx2048m -Djava.library.path=/usr/local/lib/smartcalc/app" \
--runtime-image target/java-runtime \
--app-version ${APP_VERSION} \
--install-dir /usr/local \
--linux-shortcut \
--linux-menu-group "SmartCalc" \
--vendor "Peabody Albertine" \
--copyright "Copyright © 2024 Peabody Albertine"
