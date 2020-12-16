module Project3 where

import           Data.Maybe

data RE a
    = Symbol a
    | Empty
    | RE a :+: RE a
    | RE a :|: RE a
    | Repeat (RE a)
    | Plus (RE a)
    deriving (Show, Read, Eq, Ord)

data ABC = A | B | C deriving (Show, Read, Eq, Ord)

-- The language for atMost2As includes exactly those strings in which A occurs
-- no more than twice
atMost2As :: RE ABC
atMost2As = Repeat (Symbol B:|:Symbol C):+:(Symbol A:|:Empty):+:Repeat (Symbol B:|:Symbol C):+:(Symbol A:|:Empty):+:Repeat (Symbol B:|:Symbol C)


-- anyOf alts returns a regular expression that matches any of the symbols
-- given in alts (assuming alts is non-empty)
anyOf :: [a] -> RE a
anyOf []          = Empty
anyOf (item:list) = Repeat(Symbol item) :|: anyOf list


--helps for the repeats
repeatsHelp :: Int -> RE a -> RE a
repeatsHelp 0 r     = Empty
repeatsHelp n Empty = Empty
repeatsHelp n r     = r :+: repeatsHelp (n-1) r


-- repeats m n r returns a regular expression which matches r at least m but no
-- more than n times (assuming m <= n)
repeats :: Int -> Int -> RE a -> RE a
repeats n m Empty = Empty
repeats 0 0 r     = Empty
repeats n m r     = if n == m then repeatsHelp n r else repeatsHelp n r :|: repeats (n+1) m r


-- matchEmpty r indicates whether r will accept the empty string
matchEmpty :: RE a -> Bool
matchEmpty Empty      = True
matchEmpty (Repeat a) = True
matchEmpty (Plus a)   = matchEmpty a
matchEmpty (a :+: b)  = matchEmpty a && matchEmpty b
matchEmpty (a :|: b)  = matchEmpty a || matchEmpty b
matchEmpty (Symbol a) = False


-- minLength r returns the minimum length of any string that matches r
minLength :: RE a -> Integer
minLength Empty      = 0
minLength (Repeat a) = 0
minLength (Plus a)   = minLength a
minLength (a :+: b)  = minLength a + minLength b
minLength (a :|: b)  = minimum[minLength a, minLength b]
minLength (Symbol a) = 1


--helper for maxLength
maxLengthInt :: RE a -> Integer
maxLengthInt Empty          = 0
maxLengthInt (Symbol a)     = 1
maxLengthInt (Repeat Empty) = 0
maxLengthInt (Plus Empty)   = 0
maxLengthInt (Repeat a)     = -1
maxLengthInt (Plus a)       = -1
maxLengthInt (a :+: b)      = maxLengthInt a  + maxLengthInt b
maxLengthInt (a :|: b)      = maximum[maxLengthInt a, maxLengthInt b]


-- maxLength r returns Just n if the maximum length of any string matched by
-- r is n, or Nothing if there is no upper limit
maxLength :: RE a -> Maybe Integer
maxLength a = if maxLengthInt(a) == -1 then Nothing else Just (maxLengthInt(a))


-- firsts r returns a list containing every symbol that occurs first in some
-- string that matches r
firsts :: RE a -> [a]
firsts Empty = []
firsts (Symbol a) = [a]
firsts (Repeat a) = firsts a
firsts (Plus a)   = firsts a
firsts (a :|: b)  = firsts a ++ firsts b
firsts (a :+: b)  = if minLength a == 0 then firsts a ++ firsts b else firsts a
