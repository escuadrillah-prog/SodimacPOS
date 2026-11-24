@echo off
title Autoservicio POS Homecenter Sodimac - Windows
cd /d "%~dp0"
if not exist "build\classes" mkdir "build\classes"
echo Compilando sistema POS en Java para Windows...
javac -encoding UTF-8 -d build\classes src\main\*.java src\util\*.java src\modelo\*.java src\vista\*.java src\controlador\*.java
xcopy /E /I /Y /Q src\img build\classes\img >nul
echo Iniciando terminal POS Sodimac...
start "" javaw -cp build\classes main.Main
exit
