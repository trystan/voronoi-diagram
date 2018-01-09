# voronoi-diagram

Get a voronoi diagram for a given collection of points.

## Leiningen

[trystan/voronoi-diagram "1.0.0"]

## Usage

There's only one usefull function, `diagram`. It takes a collection of points and returns a map with :points, :edges, and :cells. Each point is a vector of two coordinates, each edges is a vector of two points, and each cell is a vector of at least three points.

    (ns example.core
      (:require [voronoi-diagram.core :as voronoi]))

    (let [points [[2 2] [1 4] [4 1] [-10 -10] [-10 10] [10 10] [10 -10]]
          {:keys [points edges cells]} (voronoi/diagram points)]
      (println "point" (first points))
      (println "edge" (first edges))
      (println "cell" (first cells)))

    #=> point [2.0 2.0]
    #=> edge [[-2.3333333333333335 -1.3333333333333333] [2.3333333333333335 2.3333333333333335]]
    #=> cell [[-2.3333333333333335 -1.3333333333333333] [2.3333333333333335 2.3333333333333335] [-1.3333333333333333 -2.3333333333333335]]


## License

Copyright © 2015 Trystan

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
