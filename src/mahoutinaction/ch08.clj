(ns mahoutinaction.ch08
  (:import [java.util ArrayList List]
           [org.apache.mahout.math Vector DenseVector NamedVector VectorWritable]
           [org.apache.hadoop.conf Configuration]
           [org.apache.hadoop.fs FileSystem Path]
           [org.apache.hadoop.io
            SequenceFile$Writer SequenceFile$Reader Text]))

(defn add-apple [applelist params desc]
  (let [apple (NamedVector. (DenseVector. (double-array params)) desc)]
    (.add applelist apple)))

(defn build-apple-data []
  (let [apples (ArrayList.)]
    (add-apple apples [0.11 510 1] "Small round green apple")
    (add-apple apples [23 650 3] "Large oval red apple")
    (add-apple apples [0.09 630 1] "Small elongated red apple")
    (add-apple apples [0.25 590 3] "Large round yellow apple")
    (add-apple apples [0.18 520 2] "Medium oval green apple")
    apples))

(defn write-apple-data [filepath]
  "Write apple data as vectors"
  (let [apples (build-apple-data)
        conf (Configuration.)
        fs  (FileSystem/get conf)
        path (Path. filepath)
        writer (SequenceFile$Writer. fs conf path 
                                     Text
                                     VectorWritable)
        vec (VectorWritable.)]
    (dotimes [idx (count apples)]
      (let [v (.get apples idx)]
        (.set vec v)
        (.append writer (Text. (.getName v)) vec)))
    (.close writer)))

(defn get-values [^Vector values]
  "Return a list of the values in a Mahout Vector"
  (for [i (range (.size values))]
    (.get values i)))

(defn read-apple-data [filepath]
  "Read in the apple vectors"
  (let [conf (Configuration.)
        fs  (FileSystem/get conf)
        path (Path. filepath)
        reader (SequenceFile$Reader. fs path conf)
        vec_key (Text.)
        vec_value (VectorWritable.)]
    (while (.next reader vec_key vec_value)
      (do
        (println (str vec_key " - " (seq (get-values (.get vec_value)))))))
    (.close reader)))

(defn apples-to-vectors [filepath]
  (do
    (write-apple-data filepath)
    (read-apple-data filepath)))

;; (apples-to-vectors "appledata/apples") ;; relative to project, not HadoopFS
