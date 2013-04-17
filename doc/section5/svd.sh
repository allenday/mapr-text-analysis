#!/bin/sh

#make the matrix from the Reuters text
mahout seq2sparse -i ../../data/reuters-21578-seq/ -o vectors -ow
#get the matrix dimensions
mahout rowid -i vectors/tfidf-vectors/part-r-00000 -o matrix
#reduce to rank=3 matrix
mahout svd -i vectors/tfidf-vectors/ -o svd-values -nr 21578 -nc 41807 -r 3
mahout ssvd -i vectors/tfidf-vectors/ -o ssvd-values -k 3 --reduceTasks 2


mahout rowsimilarity -i matrix -o similarity -r 21578 --similarityClassname SIMILARITY_COSINE -m 10 -ess
