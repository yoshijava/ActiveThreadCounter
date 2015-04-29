#!/bin/sh
mvn exec:java -Dexec.mainClass=ActiveThreadCounterMonitor -Dexec.args=$1
