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

curl https://raw.githubusercontent.com/spring-io/concourse-java-scripts/v0.0.4/concourse-java.sh > /opt/concourse-java.sh

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
# GRADLE ENTERPRISE
###########################################################
mkdir ~/.gradle
echo 'systemProp.user.name=concourse' > ~/.gradle/gradle.properties