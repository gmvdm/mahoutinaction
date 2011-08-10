(ns mahoutinaction.core
  (:use [mahoutinaction.ch02 :as ch02]))

(defn -main []
  (do
   (println "Recommend")
   (doall (map #(println %) (ch02/recommend "resources/intro.csv")))
   (println "Evaluate")
   (println (str "Evaluation: " (ch02/evaluator "resources/intro.csv")))))
