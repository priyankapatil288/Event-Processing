# Event-Processing #Steps to run this Application
Download hsqldb.zip and EventProcessing.zip.  
Extract hsqldb.zip and go to "hsqldb-2.4.1/hsqldb/data" location from command prompt.  
Start DB Server instance by running below command.  
java -cp ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/eventdb --dbname.0 eventdb.  
Extract and import EventProcessing.zip to any IDE's (Eclipse/Intellij).  
Run EventDataApplication java file. It will start the Spring boot application

#API for file processing
Created below to API for file processing

*Please run below curl command to process events from file
curl --location --request POST 'http://localhost:8081/filepath' \
--form 'filePath="C:\\Users\\gs-2111\\Documents\\Priyanka\\CS\\EventData\\src\\main\\resources/logfiless.txt"'

#Below are details for API to fetch event from databse
GET - IP:Port/event/{eventId} - We can search specifc event id to check total time duartion it took for completion
