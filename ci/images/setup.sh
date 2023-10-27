#!/bin/bash
set -ex

###########################################################
# UTILS
###########################################################

export DEBIAN_FRONTEND=noninteractive
apt-get update
apt-get install --no-install-recommends -y tzdata ca-certificates net-tools libxml2-utils git curl libudev1 libxml2-utils iptables iproute2 jq unzip build-essential libz-dev libfreetype-dev
ln -fs /usr/share/zoneinfo/UTC /etc/localtime
dpkg-reconfigure --frontend noninteractive tzdata
rm -rf /var/lib/apt/lists/*

mkdir -p /opt/ytt/bin
curl --location https://github.com/vmware-tanzu/carvel-ytt/releases/download/v0.41.1/ytt-linux-amd64 > /opt/ytt/bin/ytt
chmod +x /opt/ytt/bin/ytt

###########################################################
# GRAAL
###########################################################
GRAAL_URL=$( ./get-graal-url.sh $1 )

mkdir -p /opt/graalvm
cd /opt/graalvm
curl -L ${GRAAL_URL} | tar zx --strip-components=1
test -f /opt/graalvm/bin/java
test -f /opt/graalvm/bin/javac

###########################################################
# DOCKER
###########################################################
cd /
DOCKER_URL=$( ./get-docker-url.sh )
curl -L ${DOCKER_URL} | tar zx
mv /docker/* /bin/
chmod +x /bin/docker*

export ENTRYKIT_VERSION=0.4.0
curl -L https://github.com/progrium/entrykit/releases/download/v${ENTRYKIT_VERSION}/entrykit_${ENTRYKIT_VERSION}_Linux_x86_64.tgz | tar zx
chmod +x entrykit && \
mv entrykit /bin/entrykit && \
entrykit --symlink

###########################################################
# DOCKER COMPOSE
###########################################################
mkdir -p /opt/docker-compose/bin
DOCKER_COMPOSE_URL=$( ./get-docker-compose-url.sh )
curl --location "${DOCKER_COMPOSE_URL}" > /opt/docker-compose/bin/docker-compose
chmod +x /opt/docker-compose/bin/docker-compose
