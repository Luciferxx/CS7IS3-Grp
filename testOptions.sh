#!/bin/bash

for a in standard whitespace simple english custom
do
  for s in classic bm25 boolean lmdirichlet
  do
    java -Xmx4000m -jar ./out/artifacts/CS7IS3_jar/CS7IS3.jar -a $a -s $s -i

    ./trec_eval/trec_eval ./qrels.assignment2.part1 ./output/results.txt 2>&1 | tee ./output/$a:$s:trec_eval_score.txt
  done
done