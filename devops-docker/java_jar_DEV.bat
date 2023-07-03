@echo off
SET DIRECTORY_HOME=D:\BACKUP\rest-banco

cd %DIRECTORY_HOME%\discovery\target
start "discovery" java -jar discovery.jar

cd %DIRECTORY_HOME%\gateway\target
start "gateway" java -jar gateway.jar

cd %DIRECTORY_HOME%\card-service\target
start "card" java -jar card-service.jar

cd %DIRECTORY_HOME%\transaction-service\target
start "transaction" java -jar transaction-service.jar

@pause