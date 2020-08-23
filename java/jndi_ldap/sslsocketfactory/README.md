
$ /home/opt/jboss-eap-7.3/bin/jboss-cli.sh -c 

[standalone@localhost:9990 /] module add --name=sslsocketfactory --resources=./target/sslsocketfactory-1.0-SNAPSHOT.jar

[standalone@localhost:9990 /] /subsystem=ee:write-attribute(name=global-modules,value=[{name=sslsocketfactory}]

