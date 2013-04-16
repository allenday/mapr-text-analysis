import sys
import string

for line in sys.stdin:
  values = string.split(line[0].replace('\"',''), ",")
  ipStartIndex = values[0]
  ipEndIndex = values[1]
  locationId = values[2]
  geoBlockIndex = str(long(ipEndIndex) - (long(ipEndIndex) % long(65536)))
  print "\t".join([locationId, ipStartIndex, ipEndIndex, geoBlockIndex])
