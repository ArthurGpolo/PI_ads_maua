@echo off
setlocal
echo ============================================================
echo      TESTES UNITARIOS - JORNADA DO CONHECIMENTO
echo ============================================================
echo.

set CP=lib\mysql-connector-j.jar;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar

echo [1/2] Compilando codigo + testes...
if exist "bin-test" rd /s /q bin-test
mkdir bin-test
dir /s /b src\*.java test\*.java > sources-test.txt
javac -encoding UTF-8 -d bin-test -cp "%CP%" @sources-test.txt
if %errorlevel% neq 0 (
    echo [ERRO] Falha na compilacao dos testes. Verifique o JDK.
    pause
    exit /b
)
del sources-test.txt

echo.
echo [2/2] Executando JUnit...
java -cp "bin-test;%CP%" org.junit.runner.JUnitCore com.maua.jogo.AllTests
pause
