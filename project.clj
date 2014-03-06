(defproject intronic/bubble-time "0.1.0-SNAPSHOT"
  :description "Dive Logging toy project to learn Compojure/Om/Datomic/Reloaded/CI stuff"
  :url "http://bubble-time.insilico.io"
  :license {:name "GPL - v 3.0"
            :url "http://www.gnu.org/licenses/gpl-3.0.en.html"
            :distribution :repo}

  :jvm-opts ^:replace ["-Xmx2g" "-server" "-d64" "-Djava.net.preferIPv4Stack=true"]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2156"]
                 [ring/ring "1.2.1"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.5.0"]
                 [om-sync "0.1.1"]
                 [compojure "1.1.6"]
                 [fogus/ring-edn "0.2.0"]
                 [com.datomic/datomic-free "0.9.4532"]
                 [ring-mock "0.1.5"]]

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.4"]
                                  [org.clojure/java.classpath "0.2.0"]]}}

  :plugins [[lein-cljsbuild "1.0.2"]]

  :source-paths ["src/clj" "src/cljs"]
  :resource-paths ["resources"])

  :ring {:handler bubble-time.web/handler}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/clj" "src/cljs"]
                        :compiler {:output-to "resources/public/js/main.js"
                                   :output-dir "resources/public/js/out"
                                   :optimizations :none
                                   :source-map true}}]}
