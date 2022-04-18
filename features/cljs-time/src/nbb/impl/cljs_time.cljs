(ns nbb.impl.cljs-time
  {:no-doc true}
  (:require [cljs-time.core :as t]
            [cljs-time.coerce :as tc]
            [cljs-time.format :as tf]
            [nbb.core :as nbb]
            [sci.core :as sci :refer [copy-var]]))

(def core-ns (sci/create-ns 'cljs-time.core nil))
(def coerce-ns (sci/create-ns 'cljs-time.coerce nil))
(def format-ns (sci/create-ns 'cljs-time.format nil))

(def core-namespace
  {'now (copy-var t/now core-ns)})

(def coerce-namespace
  {'to-long (copy-var tc/to-long coerce-ns)})

(def format-namespace
  {'unparse (copy-var tf/unparse format-ns)
   'parse (copy-var tf/parse format-ns)
   'formatter (copy-var tf/formatter format-ns)})

(defn init []
  (nbb/register-plugin!
   ::cljs-time
   {:namespaces {'cljs-time.core   core-namespace
                 'cljs-time.coerce coerce-namespace
                 'cljs-time.format format-namespace}}))
