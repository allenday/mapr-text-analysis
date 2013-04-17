#!/bin/sh

#make the matrix from the Reuters text
mahout seq2sparse -i ../../data/reuters-21578-seq/ -o vectors -ow
#get the matrix dimensions
mahout rowid -i vectors/tfidf-vectors/part-r-00000 -o matrix
#reduce to rank=3 matrix
mahout svd -i vectors/tfidf-vectors/ -o svd-values -nr 21578 -nc 41807 -r 3
mahout ssvd -i vectors/tfidf-vectors/ -o ssvd-values -k 3 --reduceTasks 2

#https://cwiki.apache.org/MAHOUT/quick-tour-of-text-analysis-using-the-mahout-command-line.html
#XXX WARNING 13/04/17 11:38:33 INFO driver.MahoutDriver: Program took 7037745 ms (Minutes: 117.29575)
mahout rowsimilarity -i matrix/matrix -o similarity -r 21578 --similarityClassname SIMILARITY_COSINE -m 10 -ess

#check out the output
mahout seqdumper -i similarity/part-r-00000 > top10.txt
cat top10.txt | perl -ne 'm/Key: (\d+)/;$x=$1;@t=m/[\{,](\d+):/g;print $x,"\t",$_,"\n" foreach @t' > top10.pair
cat top10.pair | head -1000 | perl ./gv.pl > top10.png
