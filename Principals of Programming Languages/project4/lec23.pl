:- use_module(library(clpfd)).

len([], 0).
len([_|T], N) :- M #= N - 1, len(T, M).

len2([], 0).
len2([_|T], N) :- N #> 0, M #= N - 1, len2(T, M).

len3(L, N) :- zcompare(Cmp, N, 0), len3_help(Cmp, L, N).

len3_help(=, [], 0).
len3_help(>, [_|T], N) :- M #= N - 1, len3(T, M).


app([], L, L).
app([X|L1], L2, [X|L3]) :- app(L1, L2, L3).



% list_sum(?List, ?Total)
list_sum([], 0).
list_sum([X|Xs], N) :- N #= X + M, list_sum(Xs, M).


% contrast
% ?- list_sum(L, 3).
% ?- old_list_sum(L, 3).

% old_len(+List, ?Length)
old_len([], 0).
old_len([_|T], N) :- old_len(T, M), N is M + 1.

% old_list_sum(+List, ?Total)
old_list_sum([], 0).
old_list_sum([X|Xs], N) :- old_list_sum(Xs, M), N is X + M.
