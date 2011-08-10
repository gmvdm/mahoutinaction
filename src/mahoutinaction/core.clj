(ns mahoutinaction.core
  (:use mahoutinaction.ch02))

(defn -main []
  (recommend "resources/intro.csv"))
