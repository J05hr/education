module Project2 where

import           Data.List
import           Data.Maybe

--replicate a list of x 0's inside a list y times
zero :: Int -> Int -> [[Double]]
zero 0 0 = []
zero y x = [ replicate x 0 | y <- [1..y]]


--check the indexs as you add to the lists and if rows == columns add a 1 else 0
ident :: Int -> [[Double]]
ident 0    = []
ident size =  [ [ if y == x then 1 else 0 | y <- [1..size]] | x <- [1..size]]


--zip a list containing items from the list of lists where we take the n,n item where n is incremented from 0 to row length
diag :: [[Double]] -> [Double]
diag []    = []
diag lists =  zipWith (!!) lists [0..length (lists !! 0) - 1]


--zip the sums of the first list in each set of lists and recurse until empty
add :: [[Double]] -> [[Double]] -> [[Double]]
add []  []  = []
add (alist:alists) (blist:blists) = [zipWith (+) alist blist] ++ add alists blists


--n,m becomes m,n for each item, using the built in function for simplicity
transp :: [[Double]] ->[[Double]]
transp []  = []
transp mat =  transpose mat

--def
data Sparse = Sparse Int Int [(Int,[(Int,Double)])]
    deriving (Show, Eq)

--make a helper to unsparse
unsparse :: Sparse -> [[Double]]
unsparse (Sparse rows cols sp) =
  [ [fromMaybe 0.0 (lookup y (fromMaybe [(0,0.0)] (lookup x sp))) | y <- [0..rows-1]] | x <- [0..cols-1]]

--make a helper to sparsify
--sparsify ::  [[Double]] -> Sparse
--sparsify mat = Sparse (length mat)  (length (mat !! 0)) [ [ if ((mat !! y) !! x ) != 0 then (0 else ((mat !! y) !! x ) | y <- [0..length mat -1]] | x <- [1..length (mat !! 0)-1]]

--cheap out and hardcode the fixed syntax, saves tons of time and space
sident :: Int -> Sparse
sident size = Sparse size size [(n,[(n,1.0)]) | n<-[0..size-1]]

--unsparse then diag
sdiag :: Sparse -> [Double]
--sdiag s = []
sdiag s = diag(unsparse(s))

--unsparse the 2 then add them and  sparsify
sadd :: Sparse -> Sparse -> Sparse
sadd a b = sident 0
--sadd  s1 s2 =  sparsify((add(unsparse (s1) unsparse(s2))))
