FROM openjdk:11.0.13-jdk-slim

RUN apt-get update \
    && apt-get install -y wget \
    && wget https://apache.mirror.digitalpacific.com.au/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz \
    && tar -xf apache-maven-3.8.4-bin.tar.gz \
    && mv apache-maven-3.8.4 /usr/share/maven \
    && rm -rf apache-maven-3.8.4-bin.tar.gz \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn \
    && apt-get clean

RUN useradd -m appuser
USER appuser

WORKDIR /app
COPY . .

EXPOSE 3000

RUN mvn clean install

WORKDIR /app/target

CMD ["java", "-jar", "RaceHub-3.0.0.jar"]
