#!/bin/bash -v

URL=$1
echo "URL=$URL"
TRUSTSTORE=~/openssl/RootCA-2023/trust.jks

java -cp . -Djavax.net.debug=all -Djavax.net.ssl.trustStore=$TRUSTSTORE -Djavax.net.ssl.trustStorePassword=password test.Main $URL
