(ns global-affairs-sparkling.core-test
  (:require [clojure.test :refer :all]
            [global-affairs-sparkling.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
git update-index --skip-worktree run-spark.sh
git update-index --skip-worktree run-spark.sh
(let [gsc (make-spark-context)
          link "s3a://gdelt-open-data"
          temp-bucket "v2/events/20150328170000.export.csv"
          gdelt-bucket "v2/events/"
          gdelt-link (str link "/" gdelt-bucket)
          file-link-dir "s3a://gdeltdata/"
          file-link "s3a://gdeltdata/20190613211500.export.csv"
          file-local "20150821.export.csv"
          ;num-of-files 2
         ; file-list s3c/get-gdelt-list
         ; files (take-last num-of-files file-list)
          ]
      (println gsc)
      (try
        (->> file-link-dir

             ;; (spark/text-file gsc)
             ;; (spark/map count)
             ;; (spark/reduce +)
            ; (spark/parallelize gsc) ;(take-last num-of-files file-list  );(dorun (s3c/get-gdelt-list))
                                        ;(spark/map #(s3c/get-cvs-from-s3 %))

             (spark/whole-text-files  gsc)
             (spark/flat-map-values (fn [line] (string/split line #"\n")))
             (spark/map-to-pair (dest/key-value-fn (fn [k v] (sp/mk-datecountryevent-tuple v))))
             spark/count-by-key
             ;(spark/cache)
             ;(spark/take 20)
             (spark/count)
             ;(println "-------------FILES-------------")
             ;(spark/values)
             ;spark/collect
             ;(spark/take 10)
             (clojure.pprint/pprint "----------------------------")                           ;
             )
        (catch Exception e
          (spark/stop gsc)
          (str "caught exception: " (.getMessage e))))
      gsc))
