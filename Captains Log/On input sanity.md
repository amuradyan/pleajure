# On input sanity

`get-at` and `list-lookup` are equipped with sanity checks, a-la contract. In terms of a type system the whole thing is two methods with tests and some check noise at the top of the function. I've separated such tests into a file of their own, so far it is convenient. Input checks should further be added to the other functions, and I'll see how can I abstract it.

There's a [native contract solution](https://github.com/clojure/core.contracts), that should be checked.

On the other hand, how do you check that you fail predictably, when the input is not a `list`? We could check exhaustively, given that is possible, but with this approach, the more types you have, the more expensive it gets.
