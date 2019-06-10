FROM centos
MAINTAINER yingzuidou 1498389698@qq.com
RUN mkdir -p /usr/web /usr/web/project /usr/web/env

ADD jdk-8u211-linux-x64.tar.gz /usr/web/env
ENV JAVA_HOME /usr/web/env/jdk1.8.0_211
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV PATH $PATH:$JAVA_HOME/bin
RUN java -version
ADD platform-eureka-config-1.0-SNAPSHOT.jar /usr/web/project
WORKDIR /usr/web/project
ENTRYPOINT java -jar platform-eureka-config-1.0-SNAPSHOT.jar && tail -f > /etc/null

 

