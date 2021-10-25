echo "[PROCESS]  Compiling gui for TIG-marketing"
E:
cd C:\Users\mmc\Pahappa\TIG-marketing\TIG-marketing
call mvn eclipse:clean
call mvn eclipse:eclipse -Dwtpversion=2.0
call mvn package
echo "[SUCCESSFUL]  Successfully Compiled All Guis
PAUSE