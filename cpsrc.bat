@echo off

set SRC_PATH=F:\qobj\droid\DIII4A
set DST_PATH=F:\data\DIII4A

if exist "%DST_PATH%" (
	echo Remove %DST_PATH%
   del /S/Q %DST_PATH%
   rmdir /S/Q %DST_PATH%
) else (
	echo Not found %DST_PATH%
)

echo Create %DST_PATH%
mkdir %DST_PATH%
mkdir %DST_PATH%\idTech4amm
mkdir %DST_PATH%\Q3E

echo Copying %DST_PATH% to %SRC_PATH%
xcopy /E/I/Y/Q %SRC_PATH%\idTech4amm\src %DST_PATH%\idTech4amm\src
xcopy /Y/Q %SRC_PATH%\idTech4amm\build.gradle %DST_PATH%\idTech4amm\
xcopy /Y/Q %SRC_PATH%\idTech4amm\lint.xml %DST_PATH%\idTech4amm\
del /S/Q %DST_PATH%\idTech4amm\src\main\assets

xcopy /E/I/Y/Q %SRC_PATH%\Q3E\src %DST_PATH%\Q3E\src
xcopy /Y/Q %SRC_PATH%\Q3E\build.gradle %DST_PATH%\Q3E\
del /S/Q %DST_PATH%\Q3E\src\main\assets
del /S/Q %DST_PATH%\Q3E\src\main\jni\duke4
rmdir /S/Q %DST_PATH%\Q3E\src\main\jni\duke4

xcopy /Y/Q %SRC_PATH%\.gitignore %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\build.gradle %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\CHANGES.md %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\CHANGES.zh.md %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\CHECK_FOR_UPDATE.json %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\CMakeLists.txt %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\gradle.properties %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\gradlew %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\gradlew.bat %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\idtech4amm.keystore %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\LICENSE %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\local.properties %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\README.md %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\README.zh.md %DST_PATH%\
xcopy /Y/Q %SRC_PATH%\settings.gradle %DST_PATH%\

echo done!
start "" %DST_PATH%

pause