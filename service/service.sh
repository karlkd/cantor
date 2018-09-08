#!/bin/bash
(nohup java -Dhost.name=$HOSTNAME -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=localhost -Dcom.sun.management.jmxremote.port=9002 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -classpath ./lib/*:./conf/ io.cantor.service.InitialService &) && java -jar /usr/share/jmxtrans/lib/jmxtrans-all.jar -e -j /var/lib/jmxtrans -s 5 -c false