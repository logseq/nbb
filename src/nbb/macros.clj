(ns nbb.macros
  (:require
   [clojure.data.json :as json]
   [clojure.edn :as edn]
   [clojure.string :as str]))

(defmacro with-async-bindings [m & body]
  `(do (sci.impl.vars/push-thread-bindings ~m)
       (.finally (do ~@body)
                 (fn []
                   (sci.impl.vars/pop-thread-bindings)))))

(defmacro get-in-package-json [k]
  (get (json/read-str (slurp "package.json") :key-fn keyword) k))

(def features (some-> (System/getenv "NBB_FEATURES")
                      (str/split (re-pattern ","))))

(defmacro feature-requires []
  (when features
    (let [;; all nbb_features.edn files on the classpath:
          configs (enumeration-seq
                   (.getResources (.getContextClassLoader (Thread/currentThread))
                                  "nbb_features.edn"))
          m (->> configs
                       (mapcat (comp edn/read-string slurp str))
                       (mapcat (fn [{:keys [namespaces js]}]
                                 (mapv (fn [n] [n js]) namespaces)))
                       (into {}))]
      (list 'quote m))))
