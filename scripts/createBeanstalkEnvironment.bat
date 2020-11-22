aws s3 mb s3://shortlinkbucket-boa
aws s3 cp ../target/shortLink-0.0.1-SNAPSHOT.jar s3://shortlinkbucket-boa/shortLink-0.0.1-SNAPSHOT.jar
aws elasticbeanstalk create-application --application-name shortLink
aws elasticbeanstalk create-environment --application-name shortLink --cname-prefix shortLink --environment-name shortLink-dev --solution-stack-name "64bit Amazon Linux 2018.03 v2.11.1 running Java 8" --option-settings file://options.txt
aws elasticbeanstalk create-application-version --application-name shortLink --version-label "0.0.1" --source-bundle S3Bucket="shortlinkbucket-boa",S3Key="shortLink-0.0.1-SNAPSHOT.jar"
aws elasticbeanstalk wait environment-exists --application-name shortLink --environment-name shortLink-dev --no-include-deleted
aws elasticbeanstalk update-environment --environment-name shortLink-dev --version-label "0.0.1"