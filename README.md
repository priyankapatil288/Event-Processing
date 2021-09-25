# Event-Processing 
# Steps to run this Application
Download hsqldb.zip and EventProcessing.zip.  
Extract hsqldb.zip and go to "hsqldb-2.4.1/hsqldb/data" location from command prompt.  
Start DB Server instance by running below command.  
java -cp ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/eventdb --dbname.0 eventdb.  
Extract and import EventProcessing.zip to any IDE's (Eclipse/Intellij).  
Run EventDataApplication.java file. It will start the Spring boot application

# API for file processing
Created below 2 API's for file processing

# API to process the json file
POST - IP:Port/filePath --form 'filePath="location of file which needs to be parsed"'

Example: curl --location --request POST 'http://localhost:8080/filepath' --form 'filePath="src/main/resources/logfile.txt"'

This command will parse dummy json file attached with this project in resources folder.  
API Response - Processing has started  
You can verify the output of parsed file by opening DB session for hsql (Steps to open DB UI are shared below) and by running below query  
* Select * from event

# API to fetch event from database using eventId
GET - IP:Port/event/{eventId} - We can search specifc event id to check total time duartion it took for completion  
Example: curl --location --request GET 'http://localhost:8080/event/scsmb5tgra'


# Steps to open DB Session to verify all the events parsed
1. Go to "hsqldb-2.4.1\hsqldb\bin" path and run runManagerSwing.bat file. This will open UI popup where you have to provide below details
2. Type: HSQL Database Engine In-Memory
3. Driver: org.hsqldb.jdbcDriver
4. URL:jdbc:hsqldb:hsql://localhost/eventdb
5. User:SA
