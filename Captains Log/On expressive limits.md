# On expressive limits

pleajure understands symbols and lists of symbols. Configs, on the other hand, make a great use of maps. Maps are awesome, they're fast with convenient apis and powerful semantics. To *mean* a map, one should use a list of pairs of [certain properties](./Initial%20toughts.md). This leads to inability of expressing just a list of such pairs. `((a b) (c d))` is always `{:a :b :c :d}` and never `[[a b][c d]]`.

One way of solving this is to add more syntax and just use the Clojure map.

```clojure
((a b) (c d)) => {:a :b :c :d}
((a b) (c d) ,,,) => [[:a :b ] [:c :d]]
```

Another way of solving this is to always interpret lists as lists and provide a map-like api for them, whenever possible. Assuming we keep the current notion of a map, but parsing as-is, we should be able to do this:

```clojure
...
  (:require [pleajure.core :as p :refer [interpret]]))

(let [config (interpret '((a ((b ((c 1)))))))] ;; Note the paren noise
  (get-in config [:a :b :c])) ;; => 1
```

If we assume that as long as the element in a list is has a *next*, it is a valid key in a map, we should be able to do this:

```clojure
...
  (:require [pleajure.core :as p :refer [interpret]]))

(let [config (interpret '(a (b (c 1)))
  (p/get-in config [:a :b :c])) ;; => 1
```

All odd lists, except the singleton, may still be treated as maps /win?/:

```clojure
...
  (:require [pleajure.core :as p :refer [interpret]]))

(let [config (interpret '(a (b c) d))
  (p/get-in config [:a :b])) ;; => c
```

I like this approach, since it simplifies the notation, but then the question is: how much of the map API do we move over? What if someone wants to `map` over a *map* in pleajure, because he just accessed it as a map?

```clojure
...
  (:require [pleajure.core :as p :refer [interpret]]))

(let [config (interpret '(a 0 b 1))
  (p/get-in config [:a])  ;; => 0
  (into {} (map (fn [[k v]] [k (inc v)]) m))) ;; => will break
)
```

This can probably be solved by providing an explicit converter. Anyways, the `get-in` is a must.
