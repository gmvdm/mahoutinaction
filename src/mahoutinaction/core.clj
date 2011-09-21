(ns mahoutinaction.core
  (:use [mahoutinaction.ch02 :as ch02]
        [mahoutinaction.ch03 :as ch03]
        [mahoutinaction.ch15 :as ch15]))

;; Chapter 3
(defn run-ch03 []
  (do
    (let [stats (ch03/precision-boolean "resources/ua.base.gz")]
      (println (str "Precision: " (.getPrecision stats) " Recall: "
                    (.getRecall stats))))))

(defn -main []
  (do
    ;; Chapter 2
    (println "Recommend")
    (doall (map #(println %) (ch02/recommend "resources/intro.csv")))
    (println (str "Evaluation: " (ch02/evaluator "resources/intro.csv")))
    (let [stats (precision "resources/intro.csv")]
      (println (str "Precision: " (.getPrecision stats) " Recall: " (.getRecall stats))))
    ;; Chapter 15
    (println "AUC metric")
    (ch15/auc-example-from-file "resources/ch15.sample.txt")))
