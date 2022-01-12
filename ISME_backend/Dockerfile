#You can choose to build your api with java 8 or 11
#I've tested both using these images


#java 11
FROM adoptopenjdk/openjdk11:ubi

#here you set the location of your jar file when you build it.
COPY ./ISME_Backend.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java","-jar", "-Dserver.port=8081","ISME_Backend.jar"]

