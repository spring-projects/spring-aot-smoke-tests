FROM ubuntu:focal-20220531

ADD setup.sh /setup.sh
ADD get-graal-url.sh /get-graal-url.sh
ADD get-docker-url.sh /get-docker-url.sh
RUN ./setup.sh graal22_1

ENV JAVA_HOME /opt/graalvm
ENV PATH $JAVA_HOME/bin:/opt/ytt/bin:/opt/docker-compose/bin:$PATH
ADD docker-lib.sh /docker-lib.sh

ENTRYPOINT [ "switch", "shell=/bin/bash", "--", "codep", "/bin/docker daemon" ]
