#!/bin/sh
cd `dirname $0`
if [ -n "$JAVA_HOME" ]; then
  $JAVA_HOME/bin/java -Djava.library.path="native" -jar stones-of-history.jar
else
   java -Djava.library.path="native" -jar stones-of-history.jar
fi

