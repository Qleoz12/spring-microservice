@echo off
SET DIRECTORY_HOME=D:\BACKUP\rest-banco

cd %DIRECTORY_HOME%\discovery\
call mvn clean package install -P docker

cd %DIRECTORY_HOME%\gateway\
call mvn clean package install -P docker

cd %DIRECTORY_HOME%\domain\
call mvn clean package install

cd %DIRECTORY_HOME%\card-service\
call mvn clean package install -P docker

cd %DIRECTORY_HOME%\transaction-service\
call mvn clean package install -P docker


@pause