# insight-gdelt-exasperation

Kafka configuration file



Installation Process

Create VPC, IAM groups and Users, Create EC2 security groups,

EC2-security-groups: close most ports, open access to port that will be used by services and connected by security groups

Create AWS EC2 instance, done manually once for configuration but then continued creating them with the automation tool Pegasus. 

I forked an instance and made some changes.

Use pegasus to "quickly" install and update the required files on ec2, configuring the example/xx.sh files and master.yml, worker.yml files with their respective configuration

There were errors and checked : zookeeper/conf/zoo.cfg and updated configuration file including change permissions of kafka folders "sudo chown -R ubuntu /usr/local/kafka/"

follow https://kafka.apache.org/quickstart to check connections and creations of topics

Install kafka locally "brew install kafka" this also install zookeeper dependencies, install spark locally.



