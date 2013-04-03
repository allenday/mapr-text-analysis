mapr-text-analysis
==================

1. download and install Hadoop 1.0.4:

http://hadoop.apache.org/#Getting+Started
http://mirror.symnds.com/software/Apache/hadoop/common/stable/hadoop-1.0.4.tar.gz

follow the instructions for "Standalone Operation"
http://hadoop.apache.org/docs/r1.0.4/single_node_setup.html#Local

2. download and install Pig 0.10.0:

http://pig.apache.org/docs/r0.10.0/start.html#Pig+Setup
http://www.eng.lsu.edu/mirrors/apache/pig/stable/pig-0.11.1.tar.gz

follow the instructions for "Interactive Mode" to ensure it works
http://pig.apache.org/docs/r0.11.1/start.html#interactive-mode

3. mvn build to pull in all dependency jars.

mvn clean install
