(ns nbb.impl.cljs-bean
  (:require
   [cljs-bean.core :as bean]
   [cljs-bean.transit]
   [clojure.core :as c]
   [nbb.core :as nbb]
   [sci.core :as sci]))

(def bns (sci/create-ns 'cljs-bean.core nil))

(def btns (sci/create-ns 'cljs-bean.transit nil))
;; TODO: Update writer-handlers to return usable handlers
(def cljs-bean-transit-namespace
  (sci/copy-ns cljs-bean.transit btns))

(def cljs-bean-namespace
  {'bean (sci/copy-var bean/bean bns)
   'bean? (sci/copy-var bean/bean? bns)
   'object (sci/copy-var bean/object bns)
   '->js (sci/copy-var bean/->js bns)
   '->clj (sci/copy-var bean/->clj bns)})

(c/defn init []
  (nbb/register-plugin!
   ::cljs-bean
   {:namespaces {'cljs-bean.core cljs-bean-namespace
                 'cljs-bean.transit cljs-bean-transit-namespace}}))

