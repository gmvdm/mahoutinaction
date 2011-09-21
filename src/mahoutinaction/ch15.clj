(ns mahoutinaction.ch15
  (:import [java.io BufferedReader FileReader]
           [org.apache.mahout.math.stats GlobalOnlineAuc]
           [org.apache.mahout.classifier.evaluation Auc]))

(defn process-line [line x1 x2 line-counter]
  (let [nums (re-seq #"[\w\.]+" line)
        score (Double/parseDouble (first nums))
        target (Integer/parseInt (second nums))]
    (. x1 add target score)
    (. x2 addSample target score)
    (reset! line-counter (+ @line-counter 1))
    (if (= 0 (mod @line-counter 500))
      (printf "%10d\t%10.3f\t%10d\t%.3f\n" @line-counter score target (. x2 auc)))))

(defn auc-example-from-file [in-file]
  (with-open [rdr (java.io.BufferedReader. (java.io.FileReader. in-file))]
    (let [x1 (Auc. 1)
          x2 (GlobalOnlineAuc.)
          line-count (atom 0)]
      (dorun
       (map #(process-line % x1 x2 line-count) (line-seq rdr)))
      (printf "%d lines read\n" @line-count)
      (printf "%10.2f = AUC batch estimate\n" (. x1 auc))
      (printf "%10.2f = AUC on-line estimate\n" (. x2 auc)))))
