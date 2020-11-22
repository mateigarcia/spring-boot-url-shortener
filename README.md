# spring-boot-url-shortener Installation
To build please run the following command. It will build and run Unit tests

`mvn clean install`

To run the application, please navigate to the target folder and run

`java -jar shortLink-0.0.1-SNAPSHOT.jar`

The application can be run with a -D parameter which would change the time to live of the url in memory. The folowing command

`java -jar shortLink-0.0.1-SNAPSHOT.jar --shortLinkApplication.linkTimeToLiveSeconds=10` 

Will set the time to live of the link in memory to only 10 seconds (currently is set to 100). If the default value needs to be modified, it lies within the application.properties

# Deploy to the cloud
Since this is a spring boot application, the easiest way to deploy it to the cloud is using elasticBeanstalk.
once the application is built, please run 
`createBeanstalkEnvironment.sh` or `createBeanstalkEnvironment.bat` if on Windows
in the ${project.root}/scripts folder. The script should create an s3 bucket, upload the jar to it and deploy the application to ebs.

NOTE:This runs on a default VPC. Otherwise the deployment instructions would be more complex.

NOTE:This should be run by someone with admin IAM permissions. The scripts will create an S3 bucket plus 
Beanstalk will create automatically the instance, ALB, security groups and do the deployment of the appplication.

# Usage
There are only 2 services. I will include a postman collection to test the application
- A post to http://shortLink.eu-west-1.elasticbeanstalk.com/link with a body of 

`{
     "link":"https://www.google.com/search?q=bank+of+america&oq=bank+o&aqs=chrome.0.0i67i355i433j46i67i199i291i433j69i57j0i67j46i199i291i433j0i433j0l2.1852j0j15&sourceid=chrome&ie=UTF-8"
 }`
 
 Should return something similar to
 
 `{
      "shortUrl": "google.com/1"
  }`
- A Get to http://shortlink.eu-west-1.elasticbeanstalk.com/link?shortUrl=google.com/1 should redirect to the initial link
Unless more than 100 seconds have passed since the link creation


# Improvements and challenges
-   The main problem of my approach is that all links are hold in memory, so the application does not scale. Ideally we should be saving the links to a DB.
Currently, even if we increase the number of instances, the application won't scale because each instance will have it's own in memory table with different values,
so having a common place where both instances could access is a must. On the other side, no DB means almost no extra latency

-   Currently also the shortLinkApplication.linkTimeToLiveSeconds parameter is in the properties file. This approach is fine if it is to stay as a constant, but if we want
to change the value during execution time, AWS paramStore would be more adequate

-   There are not (and should be) integration tests (Cucumber is a suggestion)
-   Swagger spec should be added
-   The application is currently open to the world and vulnerable to DOS attacks. Mechanisms should be put in place to avoid this
-   Also is HTTP with no security (although we might want to keep this if is meant for public use) 