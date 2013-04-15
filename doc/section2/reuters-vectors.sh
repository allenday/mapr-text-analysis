#!/bin/sh

#run this from the directory in which this file is stored

#Mahout ships with a utility class for converting the Reuters 21578 dataset from SGML to plain text
java -cp ~/.m2/repository/org/apache/lucene/lucene-benchmark/3.6.0/lucene-benchmark-3.6.0.jar org.apache.lucene.benchmark.utils.ExtractReuters ../../data/reuters-21578 ../../data/reuters-21578-extract

#Convert the raw text into Hadoop SequenceFile format
mahout seqdirectory -c UTF-8 -i ../../data/reuters-21578-extract -o ../../data/reuters-21578-seq
mahout seq2sparse -i ../../data/reuters-21578-seq -o ../../data/reuters-21578-vector

mahout kmeans -i ../../data/reuters-21578-vector/tfidf-vectors -c ../../data/reuters-21578-initial -o ../../data/reuters-21578-cluster -dm org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure -cd 1.0 -k 20 -x 20 -cl
