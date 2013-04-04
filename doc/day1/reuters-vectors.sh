#!/bin/sh

java -cp ~/.m2/repository/org/apache/lucene/lucene-benchmark/3.6.0/lucene-benchmark-3.6.0.jar org.apache.lucene.benchmark.utils.ExtractReuters ../../data/reuters-21578 ../../data/reuters-21578-extract
mahout seqdirectory -c UTF-8 -i reuters-21578-extract -o reuters-21578-seq
mahout seq2sparse -i reuters-21578-seq -o reuters-21578-vector
