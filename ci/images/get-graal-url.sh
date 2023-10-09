#!/bin/bash
set -e

case "$1" in
  graal22_3)
    echo "https://download.bell-sw.com/vm/22.3.3/bellsoft-liberica-vm-core-openjdk17.0.8.1+1-22.3.3+2-linux-amd64.tar.gz"
  ;;
  *)
		echo $"Unknown Graal version"
		exit 1
esac
