#!/bin/bash
set -e

pushd git-repo > /dev/null
ytt -f ci/pipeline-template/schema.yml -f ci/pipeline-template/pipeline.yml --data-values-file ci/smoke-tests.yml > ci/pipeline.yml
popd > /dev/null
