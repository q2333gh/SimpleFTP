#!/bin/bash
nohup java -jar "$1" &
sleep 1
./ifRunning.sh


