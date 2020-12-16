module Project1 where

--import Data.List so we can find the genericLength
import Data.List


--check for the two modulus's
div7or9 :: Integer -> Bool
div7or9 num = mod num 7 == 0 || mod num 9 == 0


--recursive concat with a 2x replicated char list
echo :: [Char] -> [Char]
echo [] = ""
echo (char:charList) = replicate 2 char ++ echo charList


--recursive concat with a 2x replicated char list
--and exculde the doubleing of spaces
echons :: [Char] -> [Char]
echons [] = ""
echons (char:charList) =
  if char == ' '
    then [char] ++ echons charList
  else
    replicate 2 char ++ echons charList


--recursive count if num is an even number
countEvens :: [Integer] -> Integer
countEvens [] = 0
countEvens (num:numList) =
  if mod num 2 == 0
    then 1 + countEvens numList
  else
    0 + countEvens numList


--fold the x's and y's into sums and then divide by length
centroid :: [(Double,Double)] -> (Double,Double)
centroid [] = (0,0)
centroid tupleList =
  ( ((foldl (+) 0 (map fst tupleList))/(genericLength tupleList))
  , ((foldl (+) 0 (map snd tupleList))/(genericLength tupleList)) )


--follow the pattern and do a recursive count until you hit base case 1
hailstone :: Integer -> Integer
hailstone num =
  if num == 1
    then 1
  else if num > 1
    then
      if mod num 2 == 0
        then 1 + hailstone(div num 2)
      else
        1 + hailstone(3*num+1)
  else 0
