All Pleajure methods except the Booleans right now return a pair with the value on the right and something about the *type* of the value on the left.

Error reporting follows this convention but putting the `:error` keyword on the left.

---

Having lists of two is a bit tricky since we interpret them as pairs. So we should probably wrap them in a list to avoid confusion. This will add some noise but only in singleton maps. Still, this will not solve the technical problem of having a list of two instead of a singleton map, since Clojure repl will interpret it as a pair, like so

    (a (b c)) => [:a {:b :c}]

We need it to be like this

    (a (b c)) => [:a [:b :c]]
    (a ((b c))) => [:a {:b :c}]
