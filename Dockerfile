FROM maven:3.5.4-jdk-8-alpine
ADD . /tmp
RUN cd /tmp && mvn clean package && mkdir /app && mv target/weatherlist*.jar /app && rm -rf /tmp/*
WORKDIR /app
EXPOSE 8080
CMD java -jar weatherlist*.jar