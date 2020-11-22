# spring-boot-url-shortener Installation
To build please run the following command 

`mvn clean install`

To run the application, please navigate to the target folder and run

`java -jar shortLink-0.0.1-SNAPSHOT.jar`

The application can be run with a -D parameter which would change the time to live of the url in memory. The folowing command

`java -jar shortLink-0.0.1-SNAPSHOT.jar --shortLinkApplication.linkTimeToLiveSeconds=10` 

Will set the time to live of the link in memory to only 10 seconds (currently is set to 100). If the default value needs to be modified, it lies within the application.properties

# Deploy to the cloud
Since this is a spring boot application, the easiest way to deploy it to the cloud is using elasticBeanstalk.
once the application is built, please run createBeanstalkEnvironment.sh in the ${project.root}/scripts folder. The script should create an s3 bucket, upload the jar to it and deploy the application to ebs.

