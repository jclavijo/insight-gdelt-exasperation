#!/bin/bash

#Run script from this directory

#create project
#npx express-generator

# install with sudo, so make sure to run script with sudo
npm i odata-v4-server
npm install pg
npm i tableau-api
npm i nodemon -g

npm install

#for testing
nodemon app.js

#for production
#node app.js &
