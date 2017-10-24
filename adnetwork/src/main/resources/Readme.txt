How to build and use AdNetwork application:
    - application is made using Spring boot and maven, so just:
        - download code from GitHub
        - in folder containing downloaded code run command: mvnw spring-boot:run

After wrapper downloads necessary dependencies and starts the application,
go to http://localhost:8461/ where a simple web-application will allow you to select network and date for which reports are to be downloaded.

Web app is used so that it demonstrates, that we cannot import ad networks from sites that are not configured in our application (url should be defined, date format,...).
Configuration for each network is found in table ad_network_source. Ad_network_source is pre-filled at startup with 2 providers from data.sql file.
I also added a command line app AdNetworkApplicationConsole, but its annotations are commented out and should be uncommented.
This would be used in a scenario where we would like to import daily reports for a range of dates.

Application uses an in-memory H2 database.
To verify that data is saved go to http://localhost:8461/h2-console (using JDBC url:= jdbc:h2:mem:ad-network).
Link to this h2 database console can be found on http://localhost:8461/ .