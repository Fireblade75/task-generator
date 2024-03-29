
## Build the Angular project
#Set-Location .\task-generator-web
#mvn compile

## Copy static files incl WEB-INF
#Set-Location ..
#Get-ChildItem .\task-generator-ws\src\main\webapp\ -Recurse | Remove-Item -Recurse
#Copy-Item .\task-generator-web\src\main\dist\main\* .\task-generator-ws\src\main\webapp\ -Recurse
#Copy-Item .\task-generator-web\src\main\webapp\WEB-INF .\task-generator-ws\src\main\webapp\ -Recurse

## Build the Java EE project
#Set-Location .\task-generator-ws
#mvn clean package -DskipTests
#Set-Location ..



## Build the Docker image

docker-compose build