#Global Affairs: exploring bilateral relationships

Project Proposal


- High level overview:  

- - Global Affairs is a platform that collects data and analyses GDELT data.
  - It analyzes cross national relationships based on events and trends and presents it on a map over time. 
  - It gathers the data from S3 and process the data on Spark, saves it on Postgres and presents the results on Tableau.

- **Who is the user?**  

- - Any organization that wants to see

  - - The current or past historical trends on external relationships with other nations. 
    - The current or historical internal state of a nation's events, aggregated by news and online texts.
    - An analysis on  collected trends events and language used on the internet.

- **What problem does it solve?**  It allows an organizations to see the bilateral relationships of nation based on global or domestic events.

- **How do you solve it?** By creating a pipeline that transforms the GDELT Data and combining the relationships between different nations and events, and producing a graphical analysis of the data and maintaining a real time trend analysis.