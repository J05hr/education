% To use this code with SWI-Prolog, either invoke swipl with Lec22.pl directly
%
%     swipl lec22.pl
%
% Or start swipl normally and load the file by entering
%
%     consult(lec22).
%
% or
%
%     [lec22].
%
% When you are finished, exit swipl with the command:
%
%     halt.
%
% To watch Prolog solve your goals, use "trace." to turn on tracing, and
% "nodebug." to turn it off.

member1(X, [X|_]).
member1(X, [_|T]) :- member1(X, T).

% parent(X, Y): Y is a parent of X
parent(joe, jim).
parent(jane, joe).
parent(jill, joe).
parent(joan, jim).
parent(john, joan).

% ancestor(X, Z): Y is an ancestor of Z
ancestor1(X, Z) :- parent(X, Z).
ancestor1(X, Z) :- parent(X, Y), ancestor1(Y, Z).


ancestor2(X, Z) :- parent(X, Z); parent(X, Y), ancestor2(Y, Z).

ancestor3(X, Z) :- parent(X, Y), (Y = Z; ancestor3(Y, Z)).


sibling(X, Y) :- dif(X, Y), parent(X, Z), parent(Y, Z).

cousin(X, Y) :- parent(X, XP), parent(Y, YP), sibling(XP, YP).


