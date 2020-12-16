%jr922 Joshua Rozenberg
%PA5 - Prolog
:- use_module(library(clpfd)).


% tree(+Tree) - succeeds when Tree is a binary tree
tree(tip) :- true.
tree(bin(Left,_,Right)) :- tree(Left), tree(Right).
tree(_) :- false.


% symmetric(+Tree) - succeeds when Tree is a symmetric binary tree
symmetric(tip) :- true.
symmetric(bin(Left,_,Right)) :- tree(Left) = tree(Right).
symmetric(_) :- false.


% height(+Tree, ?Height) - succeeds when Tree is a binary tree with height
% Height
height(tip, 0) :- true.
height(bin(Left, _, Right), Height) :- M #= Height-1, (height(Left, M); height(Right, M)).
height(_, _) :- false.


% perfect(?Tree, ?Height) - succeeds when Tree is a perfect binary tree with
% height Height
perfect(tip, 0) :- true.
perfect(bin(Left, _, Right), Height) :- (M #= Height-1, M #>= 0, perfect(Left, M), perfect(Right, M)).
perfect(_, _) :- false.




% sorted(+List) - succeeds when List is a list of integers in non-decreasing
% order

sorted(List) :- false.


% sselect(?Item, ?List, ?ListWithItem) - succeeds when List and ListWithItem
% are sorted lists of intgers, and ListWithItem is the result of inserting
% Item into List
sselect(Item, List1, List2) :- false.
