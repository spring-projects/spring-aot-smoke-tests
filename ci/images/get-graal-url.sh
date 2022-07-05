#!/bin/bash
set -e

case "$1" in
	graal22_1)
		echo "https://download.bell-sw.com/vm/22.1.0/bellsoft-liberica-vm-openjdk17.0.3+7-22.1.0+1-linux-amd64.tar.gz"
	;;
  *)
		echo $"Unknown Graal version"
		exit 1
esac
