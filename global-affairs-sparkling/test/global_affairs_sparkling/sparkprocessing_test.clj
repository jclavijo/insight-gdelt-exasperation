(ns global-affairs-sparkling.sparkprocessing-test
  (:require [global-affairs-sparkling.sparkprocessing :as sut]
            [clojure.test :as t]))

;; (let [row-str '("201806" "US" "" "061" "6.4" "" "MX" "CA")
;;       row (zipmap [:date :ac1 :ac2 :ec :gsc :gc1 :gc2 :egc] row-str)
;;       vals-of-selected-keys #(vals (select-keys row %))
;;       vals-of-selected-keys-in-vector #(vals (select-keys %2 %1));first row[] then [keys]
;;       no-blanks-in-vector? #(not-any? string/blank? %)
;;       do-these-tags-exist? #(no-blanks-in-vector? (vals-of-selected-keys %))
;;       do-these-tags-exist-in-row? #(no-blanks-in-vector? (vals-of-selected-keys-in-vector %2 %1));order because of condp
;;       ]
;;   (println row
;;            (vals-of-selected-keys [:date :ac1])
;;            (no-blanks-in-vector? '("1" ""))
;;            (do-these-tags-exist? [:date :ac1])
;;            (do-these-tags-exist? [:ac1 :ac2])
;;            (do-these-tags-exist-in-row? row [:ac1 :gc2])
;;            )
;;   ;; (if (do-these-tags-exist? [:ac1 :gc2])
;;   ;;   (vals-from-selected-keys [:ac1 :gc2])
;;   ;;   false)
;;   (cond
;;     (do-these-tags-exist-in-row? row [:ac1 :ac2]) (vals-of-selected-keys [:ac1 :egc])
;;     (do-these-tags-exist-in-row? row [:ac1 :gc2]) (vals-of-selected-keys [:ac1 :gc2])

;;     (do-these-tags-exist-in-row? row [:gc1 :ac2]) (vals-of-selected-keys [:gc1 :ac2])
;;     (do-these-tags-exist-in-row? row [:gc1 :gc2]) (vals-of-selected-keys  [:gc1 :gc2])

;;     (do-these-tags-exist-in-row? row [:ac1 :egc]) (vals-of-selected-keys  [:ac1 :egc])
;;     (do-these-tags-exist-in-row? row [:gc1 :egc]) (vals-of-selected-keys  [:gc1 :egc])
;;     :else nil)
;;   )

