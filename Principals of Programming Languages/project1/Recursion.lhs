Writing Recursive Functions
===========================

This is a "literate" Haskell file. Lines beginning with '>' will be treated as
Haskell source code by GHC, so you may load this file in GHCi and try out the
definitions given here.

In this document, we will examine some methods for writing recursive functions
in Haskell. The first section will look at a way for turning a loop into a
recursive function. The second section will discuss how to write a recursive
algorithm by following the recursive structure of its input.

Some preliminaries:

> module Recursion where
>
> import Data.Array


1. Turning a loop into a recursive function
-------------------------------------------

Most loops implicitly rely on mutation, as the loop condition will need to be
repeatedly tested and at some point switch from evaluating to False to
evaluating to True. The pure functional style does not use mutation, so we
cannot write loops in this way. Instead of using assignment to change the value
of a variable, we will use recursion to re-bind the variables and evaluate the
loop condition with these new bindings.

To start, let's look at loops that just perform a calculation (that is, they do
not interact with the outside world). This loop computes the sum of numbers from
1 to n:

    procedure sum_up_to(n):
        var i = 1
        var sum = 0
        while i <= n:
            sum := sum + i
            i   := i + 1
        end while
        return sum
    end procedure

This loop has three parts:

1. Before the loop, we set up the initial conditions, setting `i` and `sum`.
2. Within the loop body, we compute new values for `i` and `sum`.
3. After the loop, we return the final value of `sum`.

In each iteration of the loop body, we first check the loop condition. If the
loop condition holds, we calculate the new values for `i` and `sum` and then
return to the top of the loop (that is, we execute the loop body again).

If the loop condition does not hold, we proceed to the post-loop section with
the current values for `sum` and `i`.

In Haskell, we would write the loop along these lines:

    loop i sum =
        if i <= n
            then loop_body i sum
            else post_loop sum

Note that `n` is free here. We will assume that `loop` is defined in some scope
where `n` is bound.

The body of the loop will compute the new values for `i` and `sum` and then
perform the loop again (that is, it will call loop with the new values):

    loop_body i sum =
        let
            sum' = sum + i
            i'   = i + 1
        in loop i' sum'

Putting these together, we can substitute the definition of `loop_body` into
`loop`:

    loop i sum =
        if i <= n
            then
                let
                    sum' = sum + i
                    i'   = i + 1
                in loop i' sum'
            else post_loop sum

Let's observe how this will evaluate. Assuming `n` is 3, we will start with

    loop 1 0

Here, i = 1 and sum = 0. Since 1 <= 3, we will choose the `then` branch and
compute i' and sum', which will be 1 + 1 = 2 and 0 + 1 = 1, respectively. We
then return the result of the recursive call:

    loop 2 1

Here, i = 2 and sum = 1. Since 2 <= 3, we will chose the `then` branch, compute
sum' = 1 + 2 = 3 and 'i = 2 + 1 = 3, and return the result of the
recursive call:

    loop 3 3

Here, i = 3 and sum = 3. Since 3 <= 3, we will choose the `then` branch,
compute sum' = 3 + 3 = 6 and i' = 3 + 1 = 4, and return the result of the
recursive call:

    loop 4 6

Here, i = 4 and sum = 6. Since 4 > 3, we will instead pass 6 to `post_loop`
and return whatever that does.

To finish writing `sum_up_to`, we need to set the initial values of `i` and
`sum` by calling `loop` with 1 and 0, and we need to define `post_loop`, which
in this case will just return `sum`.

    sum_up_to n = loop 1 0
        where
        loop i sum =
            if i <= n
                then
                    let
                        sum' = sum + i
                        i'   = i + 1
                    in loop i' sum'
                else sum

Note that `n` is bound by the parameter list for `sum_up_to` and is used by
`loop`, which is a nested definition inside `sum_up_to`. An alternative strategy
here would be to make `n` an additional parameter to `loop` and define `loop`
outside `sum_up_to`.

We can rewrite `sum_up_to` to be less verbose by using guards in the definition
of `loop`. Since `i'` and `sum'` are only used once, we can substitute their
definitions where they are used. Thus,

> sum_up_to n = loop 1 0
>     where
>     loop i sum
>         | i <= n    = loop (i + 1) (sum + i)
>         | otherwise = sum

If you load this module in GHCi, you can confirm that it computes the expected
results:

    *Recursion> sum_up_to 3
    6

Exercise: This version of `sum_up_to` begins with i = 0 and increases i until
it equals n. You could equivalently start with i = n, and decrease it until it
reaches 0. Write this version.



Fibonacci numbers
-----------------

One of the standard examples of a recursive function is computing the nth
Fibonacci number. In Haskell, we could define it like this:

> fibr n
>     | n < 2     = 1
>     | otherwise = fibr (n - 1) + fibr (n - 2)

This defininition is correct, but it has poor performance for large `n`: each
time `n` increases by 1, the number of recursive calls made approximately
doubles, meaning that `fibr` is O(2^n) time.

In contrast, a loop-based version of `fib` can run in O(n) time.

    procedure fib(n):
        var this = 1
        var prev = 1
        var i = n

        while i > 0:
            var tmp = this
            this := this + prev
            prev := tmp
            i    := i - 1
        end while

        return this
    end procedure

In this implementation, we keep track of the previous two Fibonacci numbers 
and move forward `n` places in the sequence until we find the result. We do a
fixed amount of work per iteration, and we iterate `n` times, hence O(n) time.

To turn this into a recursive definition, we need to identify the variables that
change during iteration. They are `i`, `this`, and `prev`. While `tmp` is
recalcuated in each iteration, we do not refer to its previous value, so it
can be considered local to a particular iteration.

The initial values if `i`, `this`, and `prev` are `n`, 1, and 1, respectively.

The loop condition is `i > 0`. If iteration proceeds, then the new values will
be

    this' = this + prev
    prev' = this
    i'    = i - 1

Because we are not using assignment, we still have access to the old value of
`this` and do not need the temporary variable `tmp`. The loop itself will be:

    loop i this prev =
        if i > 0
            then
                let
                    i'    = i - 1
                    this' = this + prev
                    prev' = prev
                in loop i' this' prev'
            else this

When called with initial values, this function will repeatedly call itself and
eventually return the computed value of `this`. We can simplify the definition
of `loop` and create a wrapper function that provides the desired interface:

> fibl n = loop n 1 1
>     where
>     loop i this prev
>         | i > 0     = loop (i - 1) (this + prev) this
>         | otherwise = this

By setting +s in GHCi, we can roughly compare the execution times of `fibr` and
`fibl`. For example:

    *Recursion> :set +s
    *Recursion> fibr 28
    514229
    (0.72 secs, 385,154,864 bytes)
    *Recursion> fibr 29
    832040
    (1.15 secs, 621,588,120 bytes)
    *Recursion> fibl 29
    1346269
    (0.01 secs, 2,588,040 bytes)
    *Recursion> fibl 200
    734544867157818093234908902110449296423351
    (0.01 secs, 2,587,488 bytes)

(The exact times will vary by machine.)



Adding up a list
----------------

The previous two examples used integer arguments, but we can apply these ideas
to any input data. For example, this procedure will add up the elements of a
linked list:

    procedure sum(list):
        var total = 0
        while list is not null:
            total := total + list.data
            list  := list.next
        end while
        return total
    end procedure

There is already a function `sum` provided by the Prelude that does what we
want, but we will define our own anyway.

The Prelude provides three relevant functions here: `null` returns true for
empty lists, `head` returns the first element of a (non-empty) list, and `tail`
returns the portion of a (non-empty) list following the head. Following the
pattern from above, we could define

    loop total list =
        if null list
            then total
            else
                let
                    total' = total + head list
                    list'  = tail list
                in loop total' list'

However, we can effectively call all three functions at once by pattern matching
on `list`.

    loop total list =
        case list of
            []          -> total
            item : rest -> 
                let
                    total' = total + item
                    list'  = rest
                in loop total' list'

We can simplify this further by using multiple equations and substituting the
definitions of the local variables:

    loop total []            = total
    loop total (item : rest) = loop (total + item) rest

This will move through the list, item by item, accumulating the sum in `total`
until it reaches the end of the list, at which point it returns the sum. To make
it complete, we write a wrapper function to provide the initial values:

> sum1 list = loop 0 list
>     where
>     loop total []            = total
>     loop total (item : rest) = loop (total + item) rest

We can confirm in GHCi that this behaves as expected:

    *Recursion> sum1 [1,2,3]
    6
    *Recursion> sum1 []
    0
    *Recursion> sum1 [10,100,1000,-1]
    1109



Binary search of an array
-------------------------

Here is a slightly more complex example: using binary search to determine
whether an item exists in an array. This is fairly simple to write using a loop:

    procedure search(target, array):
        var lo = 0
        var hi = length(array)
        
        while lo <= hi:
            var mid = (lo + hi) / 2

            if array[mid] = target:
                return true
            
            else if array[mid] < target:
                lo := mid + 1
            
            else:
                hi := mid - 1
            end if
        end while
    
        return false
    end procedure

We maintain two indicies into the array, representing the start and end of the
region we are considering. Depending on the value in the middle of that region,
we remove either the top or bottom half, continuing until we find the target or
the region is empty.

We have two loop variables, `lo` and `hi`, and two loop exit conditions, success
or failure. When the loop continues to the next iteration, there are two
different ways for `hi` and `low` to change. These four cases are:

1. lo > hi : return false
2. array[mid] = target : return true
3. array[mid] < target : continue; lo' = mid + 1, hi' = hi
4. array[mid] > target : continue; lo' = lo, hi' = mid - 1

While we could use Haskell's `==` and `<` operators to choose between cases 2 -
4, we will instead use a related function `compare`, which returns an
`Ordering`. These are both provided by the Prelude, with `Ordering` defined as:

    data Ordering = LT | EQ | GT                

An Array type is defined in Data.Array. Arrays in Haskell are more general than
in Java or C, allowing for different index types and arbitrary ranges. For this
example, we will assume that the indices are integers. To obtain the element at
an index, we use the `!` operator (e.g., `array ! index`).

Putting these together, our loop body will look like this:

    loop lo hi
        | lo > hi   = False
        | otherwise = 
            case compare (array ! mid) target of
                EQ -> True
                LT -> loop (mid + 1) hi
                GT -> loop lo (mid - 1)
        where mid = div (lo + hi) 2

To obtain the initial values of `lo` and `hi`, we will use `bounds` to get the
first and last indices of the array.

> search array target = loop first last
>     where
>     (first, last) = bounds array
>     
>     loop lo hi
>         | lo > hi = False
>         | otherwise =
>             case compare target (array ! mid) of
>                 EQ -> True
>                 LT -> loop lo (mid - 1)
>                 GT -> loop (mid + 1) hi
>             where mid = div (lo + hi) 2

For the purposes of testing, we will also define a simple array. The simplest
way to do this is with `listArray`, which takes a pair containing the first and
last indices, and a list containing the values that should be at each index.

> a :: Array Integer Integer
> a = listArray (0,6) [1,3,4,5,8,9,10]

    *Recursion> search a 0
    False
    *Recursion> search a 1
    True
    *Recursion> search a 2
    False
    *Recursion> search a 3
    True
    *Recursion> search a 4
    True



2. Structural induction
-----------------------

In the previous section, we wrote recursive algorithms by first writing an
iterative algorithm and using it as the basis for a recursive function. Another
strategy is to base the algorithm on the recursive structure of the input.

As an example, a list is either empty or consists of a single element and
another, smaller list. For functions that extract data from a list, or summarize
its contents in some way, we can design algorithms by working out how to extend
this summary to include one more element.

For example, a recursive function for finding the sum of the elements in a list
will have two cases. For the empty list, the sum is zero:

> sum2 [] = 0

For a non-empty list, we can get the sum by adding the first element to the sum
of the remaining elements:

> sum2 (first : rest) = first + sum2 rest

Exercise: Modify `sum2` to calculate the product of the elements of a list.

Exercise: Modify `sum2` to calculate the length of a list.


Eliminating missing values from a list
--------------------------------------

We don't need to restrict ourselves to generating simple values. Using this
method, it is simple to transform a list by building up a new one.

For example, `[Maybe a]` is a list whose elements may contain values of type `a`
or be `Nothing`. We can turn this into a list of type `[a]` fairly simply. The
base case is an empty list, which will turn into another empty list

> catMaybes [] = []

For a non-empty list, we will call `catMaybes` recursively to eliminate the
`Nothing` values in the tail of the list. The head of the input list will either
be `Nothing` or `Just x`, for some value `x` of type `a`. In the first case, we
drop the head and return the transformed tail:

> catMaybes (Nothing : tail) = catMaybes tail

For example, the list `[Just 3]` should become `[3]`, so the list `[Nothing,
Just 3]` should also become `[3]`.

For the case where the head of the list is `Just x`, we simply add `x` to the
front of the transformed tail:

> catMaybes (Just x : tail) = x : catMaybes tail

Exercise: Write a function `catLeft` that collects the `Left` values in a list
of type `[Either a b]`. Its signature will be:

    catLeft :: [Either a b] -> [a]

Exercise: Write a function that collects those numbers in a list that are
divisible by 3. Its signature will be:

    div3s :: [Integer] -> [Integer]



Recursion on a tree
-------------------

This technique works for any recursive structure. For example, we can write a
function to find the minimum element of a non-empty binary tree.

First, we will define a leaf-labeled tree type:

> data Tree a = Leaf a | Fork (Tree a) (Tree a) deriving (Show, Eq, Ord)

Here we have two cases. In the base case, a tree with a single element, we
simply return that element as the minimum:

> treeMin (Leaf a) = a

In the recursive step, we have two subtrees. We call our function recursively on
both, to get the minimum values in both, and then choose whichever of those is
smaller.

> treeMin (Fork l r) = min (treeMin l) (treeMin r)


Recursion on a natural number
-----------------------------

Perhaps unsurprisingly, the natural numbers also have a recursive structure.
Specifically, each number is either 0 or one more than another natural number. 

Let's say we want to write a function to generate a list of the first n square
numbers. Our base case, 0, will simply return an empty list:

> squares 0 = []

In the recursive case, we will use `squares (n - 1)` to get the first n - 1
squares, and then add n^2 to the beginning:

> squares n = n^2 : squares (n - 1)

Exercise: rewrite `sum_up_to` in this style.


We can even use this structure to find Fibonacci numbers. Here, we need to be a
bit clever. To calculate a Fibonacci number, we need to know two previous
numbers, but the recursive structure only lets us make one recursive call (on n
- 1). The solution is to put the recursion in a helper function that returns two
Fibonacci numbers: the requested one and the next one.

In the base case, we return the first two Fibonacci numbers:

> fibHelp 0 = (1, 1)

In the recursive case, obtain Fib(n-1) and Fib(n) from the recursive call and
use them to return Fib(n) and Fib(n+1).

> fibHelp n = (fib_n, fib_n + fib_n_1) where (fib_n_1, fib_n) = fibHelp (n - 1)

Using `fibHelp`, we can easily obtain Fib(n), by returning its first answer.

> fibi n = fst (fibHelp n)


Conclusion
----------

While the pure functional programming style does not allow iteration, as in
procedural languages, we have seen that many iterative programs can be rewritten
to use recursion. Each recursive call corresponds to jumping back to the top of
the loop.

We have also seen that many types naturally have a recursive structure that can
be used to guide the development of recursive functions.