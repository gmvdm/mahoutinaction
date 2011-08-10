(ns mahoutinaction.core
  (:use [mahoutinaction.ch02 :as ch02]))


(defn -main []
  (do
    ;; Chapter 2
    (println "Recommend")
    (doall (map #(println %) (ch02/recommend "resources/intro.csv")))
    (println "Evaluate")
    (println (str "Evaluation: " (ch02/evaluator "resources/intro.csv")))
    (println "Precision & Recall")
    (let [stats (precision "resources/intro.csv")]
      (println (str "Precision: " (.getPrecision stats) " Recall: " (.getRecall stats))))))
