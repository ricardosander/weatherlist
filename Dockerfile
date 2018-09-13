FROM maven:3.5.4-jdk-8-alpine
LABEL maintainer="ricardo.sander.lopes@gmail.com"
ADD . /tmp
RUN cd /tmp && mvn clean package && mkdir /app && mv target/weatherlist*.jar /app && rm -rf /tmp/*
WORKDIR /app
EXPOSE 8080
CMD java -jar weatherlist*.jar | tee /var/log/weatherlist.log