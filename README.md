# Racoon Filesystem Checker

Racoon Filesystem Checker is a tool to operate with your filesystem. It is an easy to use restful API. The first version
of the tool is able to create a file catalog, and it stores the previous searches in a history. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

The things you need before installing the software:

* make
* Podman
* min. Java 17

### Installation for development

A step by step guide that will tell you how to get the development environment up and running.

```
$ Checkout the main branch of the project.
$ Run the command "make build" to build the project.
$ Run the command "make run" to start the project.
$ You can clean the development environment by running the command "make clean".
```

### Installation for use in production

A step by step guide that will tell you how to get the production environment up and running.

```
$ Run the command "make release" to start the project.
```


## Usage

```
$ Open the browser and go to the address "YOUR_IP_ADRESS:8081/swagger-ui.html" to reach the swagger UI.
$ In production you can use the "YOUR_IP_ADRESS:8082/swagger-ui.html" too, to reach the swagger UI.
$ You can find the Java doc of the project on the "YOUR_INSTANCE/javadoc/index.html" address.
```
