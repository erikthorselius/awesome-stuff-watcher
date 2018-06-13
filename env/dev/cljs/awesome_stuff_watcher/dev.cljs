(ns ^:figwheel-no-load awesome-stuff-watcher.dev
  (:require
    [awesome-stuff-watcher.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
