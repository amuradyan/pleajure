# Initial thoughts

I want to be able to specify configs in LISP and parse them into Java objects, and here's how I see things.

An entry is a list of two elements.

The first element of an entry, henceforth *the name*,  can only be an atom.
The second element of an entry, henceforth *the value*, can be an atom or a list, e.g.


```scheme
(An entry)

(e (2 4))

(Antoine "Marie Jean-Baptiste Roger, Vicomte de Saint-Exupéry")
```

Note that the *conventional* syntax can be used for strings.

The *value* is interpreted as an object if it contains only entries.

```scheme
(balance
  ((debit 100)
    (credit 200)))
```

It is interpreted as a list otherwise.

```scheme
(պոպոկ (պնդուկ մանդարին))
```

**NOTE:** Objects are not allowed to contain entries with duplicate names.

The config can either be an entry, or contain a list of entries only. It is always interpreted as an object.

The two configs below are equivalent:

```json
{ // Fighter 1 in JSON
  "first-name": "Sharzhoom",
  "last-name": "Suzumov",
  "age": 26,
  "gender": "unrevealed",
  "favorite-color": "the best color",
  "likes": [
    "cheese",
    "tea",
    "smalltalk"
  ], // (cheese tea smalltalk))
  "dislikes": [
    {
      "first-name": "Otar",
      "last-name": "Aperov"
    },
    {
      "first-name": "Lori",
      "last-name": "Dzu"
    }
  ]
}
```

```scheme
( ;; Fighter 1 in Clocon
  (first-name Sharzhoom)
  (last-name Suzumov)
  (age 26)
  (gender unrevealed)
  (favorite-color "the best color")
  (likes
    (cheese tea smalltalk))
  (dislikes
    (((first-name Otar)
        (last-name Aperov))
      ((first-name Lori)
        (last-name Dzu)))))
```
