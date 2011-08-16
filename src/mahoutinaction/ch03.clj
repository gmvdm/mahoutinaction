(ns mahoutinaction.ch03
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.model GenericBooleanPrefDataModel]
           [org.apache.mahout.cf.taste.eval RecommenderBuilder DataModelBuilder]
           [org.apache.mahout.cf.taste.impl.recommender GenericBooleanPrefUserBasedRecommender]
           [org.apache.mahout.cf.taste.impl.similarity LogLikelihoodSimilarity]
           [org.apache.mahout.cf.taste.impl.neighborhood NearestNUserNeighborhood]
           [org.apache.mahout.cf.taste.impl.eval GenericRecommenderIRStatsEvaluator]
           [java.io File]))

;; (precision-boolean "resources/ua.base.gz")
(defn precision-boolean [file]
  (let [file-model
        (FileDataModel. (File. file))

        evaluator
        (GenericRecommenderIRStatsEvaluator.)
        
        recommenderBuilder
        (reify RecommenderBuilder
          (buildRecommender [_this model]
            (let [similarity (LogLikelihoodSimilarity. model) 
                  neighborhood (NearestNUserNeighborhood. 10 similarity model)
                  recommender  (GenericBooleanPrefUserBasedRecommender. model
                                                                        neighborhood
                                                                        similarity)]
              recommender)))
        
        modelBuilder
        (reify DataModelBuilder
          (buildDataModel [_this trainingData]
            (GenericBooleanPrefDataModel. (GenericBooleanPrefDataModel/toDataMap trainingData))))]
    (.evaluate evaluator recommenderBuilder modelBuilder file-model nil 10
               GenericRecommenderIRStatsEvaluator/CHOOSE_THRESHOLD
               1.0)))
