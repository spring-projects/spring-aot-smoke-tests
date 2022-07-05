FROM ubuntu:focal-20220531

ADD setup.sh /setup.sh
ADD get-graal-url.sh /get-graal-url.sh
RUN ./setup.sh graal22_1

ENV JAVA_HOME /opt/graalvm
ENV PATH $JAVA_HOME/bin:$PATH
