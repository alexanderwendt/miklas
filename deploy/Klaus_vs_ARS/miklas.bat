@Echo off
set curdir=%CD%

ECHO Starting Miklas 1.0 by Alexander Wendt 2014
cmd mode con: cols=150 lines=200 /c start "Miklas 1.0 by Alexander Wendt 2014" /D  %curdir% /min java -jar bin\miklas.jar

TIMEOUT 1