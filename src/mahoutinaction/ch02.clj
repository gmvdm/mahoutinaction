(ns mahoutinaction.ch02
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity]
           [org.apache.mahout.cf.taste.impl.recommender GenericUserBasedRecommender]
           [org.apache.mahout.cf.taste.impl.neighborhood NearestNUserNeighborhood]
           [org.apache.mahout.cf.taste.eval RecommenderBuilder ]
           [org.apache.mahout.cf.taste.impl.eval AverageAbsoluteDifferenceRecommenderEvaluator]
           [org.apache.mahout.common RandomUtils]
           [java.io File]))

;; Chapter 2.2 Running a first recommender engine
;; (recommend "resources/intro.csv")
;; via http://groups.google.com/group/clojure/browse_thread/thread/6e01fd108c32d701?pli=1
(defn recommend [file]
  (let [model (FileDataModel. (File. file)) 
       similarity (PearsonCorrelationSimilarity. model) 
       neighborhood (NearestNUserNeighborhood. 2 similarity model) 
       recommender  (GenericUserBasedRecommender. model neighborhood 
                                                  similarity)]
    (.recommend recommender 1 1)))

;; Chapter 2.3 Evaluating a Recommender
(defn evaluator [file]
  (let [_ (RandomUtils/useTestSeed)
        file-model (FileDataModel. (File. file))
        evaluator (AverageAbsoluteDifferenceRecommenderEvaluator.)
        builder (reify RecommenderBuilder
                  (buildRecommender [_this model]
                    (let [similarity (PearsonCorrelationSimilarity. model) 
                          neighborhood (NearestNUserNeighborhood. 2 similarity model)
                          recommender  (GenericUserBasedRecommender. model
                                                                     neighborhood
                                                                     similarity)]
                                              recommender)))] 
    (.evaluate evaluator builder nil file-model 0.7 1.0)))
