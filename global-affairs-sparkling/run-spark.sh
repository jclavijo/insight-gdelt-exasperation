#!/bin/bash

#enter your aws information here, required for program to run
export AWS_ACCESS_KEY=
export AWS_SECRET_KEY=
export AWS_ENDPOINT="us-west-2"
export AWS_GDELT_ENDPOINT="us-east-1"
export AWS_GDELT_BUCKET="gdelt-open-data"
export SPARK_LOCAL_IP="10.0.0.45"

#C.I.
lein uberjar

#for local testing
#spark-submit --master local[*]  ./target/global-affairs-sparkling-0.1.0-SNAPSHOT-standalone.jar

#--driver-class-path=/home/ubuntu/insight-gdelt-exasperation/global-affairs-sparkling/lib/aws-java-sdk-1.7.4.jar:/home/ubuntu/insight-gdelt-exasperation/global-affairs-sparkling/lib/hadoop-aws-2.7.3.jar
#--packages org.apache.hadoop:hadoop-aws:2.76
#--jars file:lib/aws-java-sdk-1.11.400.jar,file:lib/hadoop-aws-3.1.0.jar

spark-submit --properties-file s3.properties --master spark://10.0.0.45:7077  ./target/global-affairs-sparkling-0.1.0-SNAPSHOT-standalone.jar
#--jars file:lib/aws-java-sdk-1.7.4.jar,file:lib/hadoop-aws-2.7.6.jar
