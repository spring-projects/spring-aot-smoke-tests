#!/bin/bash
set -e

case "$1" in
  graal22_3)
    echo "https://download.bell-sw.com/vm/22.3.2/bellsoft-liberica-vm-core-openjdk17.0.7+7-22.3.2+1-linux-amd64.tar.gz"
  ;;
  *)
		echo $"Unknown Graal version"
		exit 1
esac
