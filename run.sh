#!/bin/sh
# Usage: ./run.sh [RR|SJF|IdealSJF|FeedbackRR] [timeQuantum]
set -e
cd "$(dirname "$0")"
javac -d target/classes src/main/java/*.java
java -cp target/classes Main "$@"
