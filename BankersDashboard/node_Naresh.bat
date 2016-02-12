cd %~dp0lib
SET PATH=%PATH%;%~dp0drivers

java -jar selenium-server-standalone-2.48.2.jar -role node -maxSession 10 -hub http://172.17.68.33:4444/grid/register -Dwebdriver.ie.driver=%~dp0drivers\IEDriverServer.exe -browser browserName="internet explorer",version=Node3,maxInstances=1