#! /usr/bin/python3
# -*- coding:utf-8 -*-

import sys
import base64
import pyasn1.codec.ber.decoder
import pyasn1.codec.der.decoder
import pyasn1.codec.der.encoder
from pyasn1_modules.rfc2459 import *

pem = open(sys.argv[1], 'rt').readlines()
pem.pop()
pem.pop(0)
lines = [line.strip() for line in pem]
b64 = ''.join(lines)

der = base64.b64decode(b64)

#asn1,restofasn1 = pyasn1.codec.der.decoder.decode(der, asn1Spec=Certificate())
asn1,restofasn1 = pyasn1.codec.ber.decoder.decode(der, asn1Spec=Certificate())

der = pyasn1.codec.der.encoder.encode(asn1)

b64 = base64.b64encode(der)

print("-----BEGIN CERTIFICATE-----")
length = 78
v = [b64[i: i+length] for i in list(range(0, len(b64), length))]
[print(str(l, encoding='utf-8')) for l in v]
print("-----END CERTIFICATE-----")
