
## Generate Javascript resources
Set-Location .\task-generator-web
mvn compile

## Copy static files
Set-Location ..
Remove-Item .\task-generator-ws\src\main\webapp\* -Exclude WEB-INF -Recurse

Get-ChildItem .\task-generator-ws\src\main\webapp\ -Exclude WEB-INF -Recurse | Where-Object {$_.FullName -notlike "*\WEB-INF\*"} | Remove-Item -Recurse
Copy-Item .\task-generator-web\src\main\dist\main\* .\task-generator-ws\src\main\webapp\ -Recurse

Set-Location .\task-generator-ws
mvn clean package

Set-Location ..

docker-compose build