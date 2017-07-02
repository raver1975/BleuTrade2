FROM ubuntu
MAINTAINER Paul Klemstine

RUN apt-get update
RUN apt-get install -y software-properties-common unzip
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer


RUN wget https://services.gradle.org/distributions/gradle-3.4.1-bin.zip
RUN unzip -d gradle gradle-3.4.1-bin.zip
RUN export PATH=$PATH:./gradle/gradle-3.4.1/bin
#RUN apt-get install -y software-properties-common python
#RUN echo "deb http://us.archive.ubuntu.com/ubuntu/ precise universe" >> /etc/apt/sources.list
RUN mkdir /bleutrade
ADD . /bleutrade
WORKDIR /bleutrade
CMD ["gradle","jar"]
CMD ["java", "-jar","/bleutrade/build/libs/DataCollector.jar"]