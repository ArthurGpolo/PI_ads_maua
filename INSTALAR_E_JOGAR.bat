@echo off
setlocal enabledelayedexpansion

echo ============================================================
echo      INSTALADOR E EXECUTOR - JORNADA DO CONHECIMENTO
echo ============================================================
echo.

:: 1. Garantir Driver
if not exist "lib" mkdir lib
if not exist "lib\mysql-connector-j.jar" (
    echo [1/3] Baixando Driver MySQL...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar' -OutFile 'lib\mysql-connector-j.jar'"
)

:: 2. Compilar
echo [2/3] Compilando o projeto...
if exist "bin" rd /s /q bin
mkdir bin
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d bin -cp "lib\mysql-connector-j.jar" @sources.txt
if %errorlevel% neq 0 (
    echo [ERRO] Falha na compilacao. Verifique se o JDK esta instalado.
    pause
    exit /b
)
del sources.txt

:: 3. Rodar
echo [3/3] Iniciando o Jogo...
java -cp "bin;lib\mysql-connector-j.jar" com.maua.jogo.Main
pause
