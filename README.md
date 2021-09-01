# EMSApp


<!-- TECHNOLOGIES USED -->
## 🔨  Technologies used 
* [Java](https://www.java.com/)
* [Gradle](https://gradle.org/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Tibco EMS](https://www.tibco.com/products/tibco-enterprise-message-service)

<!-- PREREQUISITES -->
## ⚙️Prerequisites
* Java 16
* Gradle
* EMS

<!-- INSTALLATION AND USAGE -->
## 🧭 Installation 
Application can be set up in two ways. First:

* clone repository

  `git clone https://github.com/hantczak/EMSApp.git`

* cd into project directory

* run project using 
  
  `gradle bootRun` 

 ... or simply start the app in Your favourite IDE!

<!-- INSTALLATION AND USAGE -->
## 🎯 Functionalities 
In current state of the app, all endpoints are accessible via HTTP requests on localhost:8090. App can allow access to topics and queues.
* POST message to the queue - if a message shall contain body, replace '{messageBody}' with desired text.

   NOTE: there is an "IS_REDUNDANCY_ENABLED" header, that is chosen to randomly contain true or false value.

  `http://localhost:8090/{messageBody}`

* GET message from queue

  `http://localhost:8090/?is_redundancy_enabled=false`
  
  NOTE: Parameter decides, whether messages with "IS_REDUNDANCY_ENABLED" set to true or false shall be taken.

* POST a message to the topic

  `http://localhost:8090/topic/{messageBody}`
  
  NOTE: Same header as in case of queue is used. Its content is also randomized. Messages are automatically fetched by 2 listeners, listening for both messages with "IS_REDUNDANCY_ENABLED" set to true and false. Message contents are then printed out to the app console.
  
  <!-- CONFIGURATION -->
## 🔧 Configuration
One additional file has to be added to ensure proper working of the application. Name of the file is 'application.yml', and it has to be located under src/main/resources.
It has to look the following way:

![image](https://user-images.githubusercontent.com/74920949/131670428-bf89de38-4c60-49df-a4f4-1cf88b2fe1ef.png)

<!-- STATUS -->
## 🏗️ Status
In progress, app functionality could be further developped.

