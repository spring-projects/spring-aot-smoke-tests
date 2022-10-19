#!/bin/bash
set -e

case "$1" in
	graal22_1)
		echo "https://download.bell-sw.com/vm/22.1.0/bellsoft-liberica-vm-openjdk17.0.3.1+2-22.1.0+2-linux-amd64.tar.gz"
	;;
  graal22_2)
    echo "https://download.bell-sw.com/vm/22.2.0/bellsoft-liberica-vm-openjdk17.0.4+8-22.2.0+2-linux-amd64.tar.gz"
  ;;
  graal22_3)
    echo "https://download.bell-sw.com/vm/22.3.0/bellsoft-liberica-vm-core-openjdk17.0.5+8-22.3.0+1-ea-linux-amd64.tar.gz"
  ;;
  *)
		echo $"Unknown Graal version"
		exit 1
esac
