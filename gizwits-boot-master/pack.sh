#!/bin/bash
. build.properties
mvn versions:set -DnewVersion=${boot_version}
mvn -Dmaven.test.skip=true clean install

