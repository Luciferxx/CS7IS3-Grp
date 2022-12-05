#!/bin/bash

java -Xmx4000m -jar ./out/artifacts/CS7IS3_jar/CS7IS3.jar

./trec_eval/trec_eval qrels.assignment2.part1 ./output/results.txt 2>&1 | tee ./demonstrate_trec_eval_score.txt