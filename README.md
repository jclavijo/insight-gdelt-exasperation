# insight-gdelt-exasperation

This project has several components individually documented with configuration and deployment instructions.



- [proposal](https://github.com/jclavijo/insight-gdelt-exasperation/blob/master/proposal.md)

- [slides](https://docs.google.com/presentation/d/1IHmYTAQvi0bRAg2PhGVig7T-V1Aas-7TF0TT77BiH78/edit?usp=sharing)

Installation Process

Create VPC, IAM groups and Users, Create EC2 security groups,

EC2-security-groups: close most ports, open access to port that will be used by services and connected by security groups

Create AWS EC2 instance, done manually once for configuration but then continued creating them with the automation tool Pegasus. 

I forked an instance and made some changes.

Use pegasus to "quickly" install and update the required files on ec2, configuring the example/xx.sh files and master.yml, worker.yml files with their respective configuration

follow https://spark.apache.org/quickstart to check connections and creations of topics

Install spark locally "brew install spark" this also install dependencies.