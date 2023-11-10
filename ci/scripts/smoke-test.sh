#!/bin/bash
set -e

source $(dirname $0)/common.sh

pushd git-repo > /dev/null
./gradlew --no-daemon ${SMOKE_TEST}:${TASK} -PforceSnapshots
popd > /dev/null
