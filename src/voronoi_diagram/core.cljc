(ns voronoi-diagram.core
  (:require [clojure.set]
            [delaunay-triangulation.core :as delaunay]))

(defn center-of-triangle [[[x1 y1] [x2 y2] [x3 y3]]]
  [(/ (+ x1 x2 x3) 3)
   (/ (+ y1 y2 y3) 3)])

(defn sort-triangles
  ([triangles] (sort-triangles [] triangles))
  ([sorted triangles]
   (cond
    (empty? triangles)
    sorted
    (empty? sorted)
    (sort-triangles [(first triangles)] (rest triangles))
    :else
    (let [previous-corners (set (last sorted))
          adjacent? (fn [triangle] (= (count (clojure.set/intersection previous-corners (set triangle))) 2))
          next-triangle (first (filter adjacent? triangles))]
      (if next-triangle
        (sort-triangles (conj sorted next-triangle) (remove #{next-triangle} triangles))
        sorted)))))

(defn polygon-for-point [triangles point]
  (let [neighboring-triangles (filter (fn [[a b c]] (or (= point a) (= point b) (= point c))) triangles)]
    (map center-of-triangle (sort-triangles neighboring-triangles))))

(defn loopify [polygon]
  (let [polygon (mapcat (fn [x] [x x]) polygon)
        polygon (concat (rest polygon) [(first polygon)])]
    polygon))

(defn diagram [points]
  (let [points (map (fn [[x y]] [(float x) (float y)]) points)
        cells (map (partial polygon-for-point (:triangles (delaunay/triangulate points))) points)]
    {:points points
     :cells cells
     :edges (->> cells
                 (map loopify)
                 (mapcat (partial partition 2))
                 (map (partial sort-by first))
                 distinct)}))
