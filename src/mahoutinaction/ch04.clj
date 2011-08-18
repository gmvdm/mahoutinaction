(ns mahoutinaction.ch04
  (:import [org.apache.mahout.cf.taste.example.grouplens GroupLensDataModel]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity]
           [org.apache.mahout.cf.taste.impl.recommender GenericUserBasedRecommender]
           [org.apache.mahout.cf.taste.impl.neighborhood NearestNUserNeighborhood]
           [org.apache.mahout.cf.taste.eval RecommenderBuilder RecommenderIRStatsEvaluator]
           [org.apache.mahout.cf.taste.impl.eval
            AverageAbsoluteDifferenceRecommenderEvaluator RMSRecommenderEvaluator
            GenericRecommenderIRStatsEvaluator LoadEvaluator]
           [org.apache.mahout.common RandomUtils]
           [java.io File]))


(defn run-load [file]
  (let [model (GroupLensDataModel. (File. file)) 
        similarity (PearsonCorrelationSimilarity. model) 
        neighborhood (NearestNUserNeighborhood. 100 similarity model) 
        recommender  (GenericUserBasedRecommender. model neighborhood 
                                                   similarity)]
    (LoadEvaluator/runLoad recommender)))
