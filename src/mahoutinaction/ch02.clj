(ns mahoutinaction.ch02
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity]
           [org.apache.mahout.cf.taste.impl.recommender GenericUserBasedRecommender]
           [org.apache.mahout.cf.taste.impl.neighborhood NearestNUserNeighborhood]
           [org.apache.mahout.cf.taste.eval RecommenderBuilder ]
           [org.apache.mahout.cf.taste.impl.eval AverageAbsoluteDifferenceRecommenderEvaluator]
           [java.io File]))

;; Chapter 2.2 Running a first recommender engine
;; (recommend "resources/intro.csv")
(defn recommend [file]
  (let [model (FileDataModel. (File. file)) 
       similarity (PearsonCorrelationSimilarity. model) 
       neighborhood (NearestNUserNeighborhood. 2 similarity model) 
       recommender  (GenericUserBasedRecommender. model neighborhood 
                                                  similarity) 
       builder (reify RecommenderBuilder (buildRecommender [_ _] 
                                           recommender)) 
       evaluator (AverageAbsoluteDifferenceRecommenderEvaluator.)] 
   ;; (.evaluate evaluator nil model 0.7 1.0)
    (.recommend recommender 1 1)))


