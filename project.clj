(defproject mahoutinaction "1.0.0-SNAPSHOT"
  :description "Mahout in Action book examples"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.apache.mahout/mahout-core "0.5"]
                 [org.apache.mahout/mahout-math "0.5"]
                 [org.apache.mahout/mahout-utils "0.5"]]
  :dev-dependencies [[lein-javac "1.2.1-SNAPSHOT"]]
  ;; :source-path "src/clj"
  :java-source-path [["src/java"]]
  :java-options {:debug "true"}
  :main mahoutinaction.core)
