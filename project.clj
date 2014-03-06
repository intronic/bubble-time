(defproject intronic/bubble-time "0.1.0-SNAPSHOT"
  :description "Dive Logging toy project to learn Compojure/Om/Datomic/Reloaded/CI stuff"
  :url "http://bubble-time.insilico.io"
  :license {:name "GPL - v 3.0"
            :url "http://www.gnu.org/licenses/gpl-3.0.en.html"
            :distribution :repo}
  :jvm-opts ^:replace ["-Xmx2g" "-server" "-d64" "-Djava.net.preferIPv4Stack=true"]
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]]
                   :source-paths ["dev"]}}
)
