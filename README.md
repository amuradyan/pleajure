# pleajure 👠

<!-- BADGES -->

[![pleajure-ci](https://github.com/amuradyan/pleajure/actions/workflows/clojure.yml/badge.svg)](https://github.com/amuradyan/pleajure/actions/workflows/clojure.yml)
[![Clojars Project](https://img.shields.io/clojars/v/am.dekanat/pleajure.svg)](https://clojars.org/am.dekanat/pleajure)

<!-- BADGES -->

A tool to write your configs in lists and atoms.

Below is an example of a config

```lisp
( ;; Example config at resources/cats.plj
  genus Felis
  family
    (name Felidae
     size 41)
  kingdom Animalia
  species
    ("Jungle cat" (Spot Felix Whiskers)
     "Black-footed cat" (Chloe Lucy Bella Angel)
     "Sand cat" (Mittens Dune)
     "African wildcat" (Molly Daisy Lily)
     "Chinese mountain cat" (Misty Smokey)
     "European wildcat" (Eli Smudge)
     "Domestic cat" (Max "Mr. Mittens")))
```

As you can see, it contains only lists and atoms in the classic lisp style. We can also use strings and numbers, and that's pretty much it. Any other form will produce an invalid config.

We can load a pleajure config via `parse-from-file` by specifying the file or leaving it blank, in which case pleajure will try to find the *application.plj* at the root of the project. The loaded config is stored as a list but pleajure provides a map-like access to it via the `get-at` function.

Given the config above, it looks something like this:

```clojure
...
(def config (parse-from-file "resources/cats.plj"))

(get-at config [:genus])              ;; => Felis
(get-at config [:family :size])       ;; => 41
(get-at config [:species "Sand cat"]) ;; => (Mittens Dune)
...
```

Such an API implies that we can do the following:

```clojure
(get-at config [:species "European wildcat" :Eli])    ;; => Smudge
(get-at config [:species "European wildcat" :Smudge]) ;; => :invalid-path
```

This behavior might be a bit strange, but be aware that it is possible. It can be really handy for expressing taxonomies.

For more info, you can look [here](./Captains%20Log/).
