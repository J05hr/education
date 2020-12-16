:- use_module(library(clpfd)).

:- op(920, fy, *).

*_.

list_length([], 0).
list_length([_|L], N) :-
    N #> 0,
    N #= M + 1,
    list_length(L, M).



friend(alice, bob).
friend(bob, carol).
friend(bob, eve).
friend(carol, daniel).
friend(daniel, eve).
%friend(X, Y) :- friend(Y, X).

friends(X, Y) :- friend(X, Y).
friends(X, Y) :- friend(Y, X).



pairup(X,Y,X-Y).






parent(john, joe).
parent(jim, john).
parent(jill, john).
parent(joan, joe).
parent(jack, joan).
parent(jane, joan).


sibling(X, Y) :-
    dif(X, Y),
    parent(X, P),
    parent(Y, P).

ancestor(D,A) :- 
    parent(D, P),
    (P = A; ancestor(P, A)).



transitive(Rel, X, Z) :-
    call(Rel, X, Y),
    (Y = Z; transitive(Rel, Y, Z)).



path(_, T, T, [T]).
path(Rel, S, T, [S|Rest]) :-
    call(Rel, S, B),
    nonmember(S, Rest),
    path(Rel, B, T, Rest).

nonmember(_, []).
nonmember(X, [E|Es]) :-
    dif(X,E),
    freeze(Es, nonmember(X, Es)).



% Interpeters and Meta-interpreters
% ---------------------------------

% rule(+Head, +Body) - defines a clause in our interpreted language
%
% Head is a term that we are defining in the language.
% Body gives antecedents for Head.
%
% To simplify the interpreter, we will make a distinction between terms that
% are part of the language and terms that are defined within the language. That
% is, we will distinguish terms that are defined using rules (such as parent/2)
% and those which are provided by the interpreter (such as dif/2).
%
% We arbitrarily choose to indicate defined terms using {}.

rule(parent(john, joe),  true).
rule(parent(jim,  john), true).
rule(parent(jill, john), true).
rule(parent(joan, joe),  true).
rule(parent(jack, joan), true).
rule(parent(jane, joan), true).

rule(sibling(X, Y), (dif(X,Y), {parent(X, P)}, {parent(Y, P)})).

rule(cousin(A, B), ({parent(A, C)}, {parent(B,D)}, {sibling(C, D)})).

rule(ancestor(A,B), {parent(A, B)}).
rule(ancestor(A,B), ({parent(A, C)}, {ancestor(C,B)})).
    % note that having multiple rules with the same head is fine


% interp(+Goal)  - interprets Goal in our interpreted language

interp(true).
interp(X = X).
interp(dif(X,Y)) :- dif(X,Y).
interp((P,Q))    :- interp(P), interp(Q).
interp((P;Q))    :- interp(P); interp(Q).
interp({G})      :- rule(G, Body), interp(Body).


% justify(+Goal) - solves Goal in the interpreted language and pretty-prints a
%   justification if it is true
%
% ?- justify({ancestor(jane, joe)}).
%
% ancestor(jane,joe) because 
%     parent(jane,joan) is a fact
%     ancestor(joan,joe) because 
%         parent(joan,joe) is a fact
%
% true .
%
% ?- justify({cousin(jack, jill)}).
%
% cousin(jack,jill) because 
%     parent(jack,joan) is a fact
%     parent(jill,john) is a fact
%     sibling(joan,john) because 
%         dif(joan,john)
%         parent(joan,joe) is a fact
%         parent(john,joe) is a fact
%
% true .

justify(Goal) :- justify(Goal, J), nl, ppj(J), nl.



:- op(750, xfy, <=).

% justify(+Goal, -Justification)  - interprets Goal in the interpreted language
%   returns its reasoning in Justification

justify(Goal, J) :- justify(Goal, J, []).

justify(true,  J, J).
justify(X = X, J, J).
justify(dif(X,Y), [dif(X,Y)|J], J) :- dif(X,Y).
justify((P,Q), J, J0) :-
    justify(P, J, J1),
    justify(Q, J1, J0).
justify((P;Q), J0, J) :-
    justify(P, J0, J);
    justify(Q, J0, J).
justify({G}, [G <= JG | J], J) :-
    rule(G, Body),
    justify(Body, JG, []).




% pr_clause(+Head, +Body, +Sources) - defines a clause with provenance in an
%     interpreted language
%
% Head is a term
% Body gives the antecedents
% Sources is a list of sources (empty for obvious truths)
%
% Using this, we can determine what sources must be trusted in order to rely
% on a conclusion.

pr_clause(parent(jim, joe),   true, []).
pr_clause(parent(jane, jim),  true, []).
pr_clause(parent(john, jane), true, [rumor]).
pr_clause(parent(jack, john), true, []).

pr_clause(ancestor(X,Y), ({parent(X,P)}, (P = Y; {ancestor(P, Y)})), []).

pr_clause(friend(alice, bob),    true, [alice]).
pr_clause(friend(bob, carol),    true, [bob]).
pr_clause(friend(bob, eve),      true, [bob]).
pr_clause(friend(bob, eve),      true, [eve]).
    % note that friend(bob, eve) is asserted by two sources
pr_clause(friend(carol, daniel), true, [carol]).
pr_clause(friend(daniel, eve),   true, [rumor]).

pr_clause(friends(X, Y), {friend(X, Y)}, []).
pr_clause(friends(X, Y), {friend(Y, X)}, []).

pr_clause(contact(X, Y), path(friends, X, Y, _), []).
    % our language isn't quite powerful enough to define path/4, so we will have
    % the interpreter handle it
    %
    % contrast with ancestor/2, which can be defined in our language


% pr_call(+Goal, -Sources) - interprets Goal in the interpreted language;
%
% Any sources that must be trusted will be included in Sources
%
% ?- pr_call({ancestor(jack, jane)}, S).
% S = [rumor] .
%
% ?- pr_call({friends(bob, eve)}, S).
% S = [bob] ;
% S = [eve] .
%
% ?- pr_call({contact(bob,daniel)}, S), nonmember(bob, S).
% S = [eve, rumor] .
% 
% ?- pr_call(path(friends, bob, daniel, P), S).
% P = [bob, carol, daniel],
% S = [bob, carol] ;
% P = [bob, eve, daniel],
% S = [bob, rumor] ;
% P = [bob, eve, daniel],
% S = [eve, rumor] ;
% false.

pr_call(Goal, Sources) :- pr_call(Goal, [], Sources).


% pr_call(+Goal, +Sources1, -Sources2) - interprets Goal
%
% Sources1 must be an ordered list of sources;
% Sources2 will be the union of Sources1 and the sources needed to prove Goal.

pr_call(true,     S, S).
pr_call(X = X,    S, S).
pr_call(dif(X,Y), S, S) :- dif(X, Y).

pr_call((P,Q), S0, S) :-
    pr_call(P, S0, S1),
    pr_call(Q, S1, S).

pr_call((P;Q), S0, S) :-
    pr_call(P, S0, S);
    pr_call(Q, S0, S).

pr_call({G}, S0, S) :-
    pr_clause(G, Body, Src),
    sort(Src, S1),
    ord_union(S0, S1, S2),
    pr_call(Body, S2, S).

pr_call(path(Rel, A, Z, [A|P]), S0, S) :-
    pr_path(Rel, A, Z, P, S0, S).

% pr_path(:Rel, ?Start, ?Finish, ?Path, +Sources1, -Sources2)

pr_path(_, A, A, [], S, S).
pr_path(Rel, A, Z, [B|Rest], S0, S) :-
    nonmember(A, [B|Rest]),
    append_args(Rel, A, B, Goal),
    pr_call({Goal}, S0, S1),
    pr_path(Rel, B, Z, Rest, S1, S).


append_args(T0, X, Y, T) :-
    T0 =.. L0,
    append(L0, [X,Y], L),
    T =.. L.



% Instead of an interpreter, we can make a meta-interpreter: a Prolog
% interpreter written in Prolog.

% mi_clause(+Head, -Body) - gives the antecedents for Goal
%
% Head a term
% Body will be a term in the format the meta-interpreter expects. That is, one
% of
%    true
%    false
%    X = Y
%    X \= Y
%    dif(X,Y)
%    (P,Q)
%    (P;Q)
%    mi_clause(G, B)
%    {P}

mi_clause(Goal, Body) :-
    clause(Goal, Subgoals),
    clean(Subgoals, Body).

% clean(+Goal, -CleanGoal) - converts an arbitrary Prolog goal into one that
% a meta-interpreter can handle.
%
% This handles all the overlapping cases into a centralized place, simplifying
% the design of the meta-interpreters.

clean(Goal, {Goal}) :-
    Goal \= true,
    Goal \= false,
    Goal \= (_ = _),
    Goal \= (_ \= _),
    Goal \= dif(_,_),
    Goal \= (_,_),
    Goal \= (_;_),
    Goal \= mi_clause(_,_).
clean(true, true).
clean(false, false).
clean(X = Y, X = Y).
clean(X \= Y, X \= Y).
clean(dif(X,Y), dif(X,Y)).
clean((P,Q), (CP,CQ)) :- clean(P, CP), clean(Q, CQ).
clean((P;Q), (CP;CQ)) :- clean(P, CP), clean(Q, CQ).
clean(mi_clause(G, B), mi_clause(G, B)).



% mi_call(+Goal) - evaluates a Goal. This is an interpreter for a subset of
%    Prolog defined in Prolog.
%
% ?- mi_call(parent(jim, X)).
% X = john.
%
% This is, in fact, a meta-circular interpreter, which means it is capable of
% interpreting itself.
%
% ?- mi_call(mi_call(parent(jim, X))).
% X = john.

mi_call(Goal) :- clean(Goal, C), mi_call_(C).

mi_call_(true).
mi_call_(X = X).
mi_call_(X \= Y)          :- X \= Y.
mi_call_(dif(X,Y))        :- dif(X,Y).
mi_call_((A,B))           :- mi_call_(A), mi_call_(B).
mi_call_((A;B))           :- mi_call_(A); mi_call_(B).
mi_call_(mi_clause(G, B)) :- mi_clause(G, B).
mi_call_({G})             :- mi_clause(G, B), mi_call_(B).



% mi_justify(+Goal) - evaluate Goal and pretty-print a justification
%
% ?- mi_justify(sibling(john, joan)).
%
% sibling(john,joan) because 
%     dif(john,joan)
%     parent(john,joe) is a fact
%     parent(joan,joe) is a fact
%
% true .

mi_justify(G) :- mi_justify(G, J), nl, ppj(J), nl.



% mi_justify(+Goal, -Justification) - evaluate Goal and provide justification
%
% Similar to justify/2, except that it works with Prolog-defined rules.

mi_justify(Goal, J) :- 
    clean(Goal, C),
    mi_justify(C, J, []).



mi_justify(true, J, J).
mi_justify(X = X, J, J).
mi_justify(X \= Y, [X \= Y|J], J) :- X \= Y.
mi_justify(dif(X,Y), [dif(X,Y)|J], J) :- dif(X,Y).

mi_justify((A,B), J, J0) :-
    mi_justify(A, J, J1),
    mi_justify(B, J1, J0).

mi_justify((A;B), J, J0) :-
    mi_justify(A, J, J0);
    mi_justify(B, J, J0).

mi_justify(mi_clause(G, B), [mi_clause(G, B) | J], J) :- mi_clause(G, B).

mi_justify({G}, [G <= JG | J], J) :-
    mi_clause(G, B),
    mi_justify(B, JG, []).


% Note that mi_call and mi_justify can interpret each other
%
% ?- mi_call(mi_justify(friends(bob, alice), J)).
% J = [friends(bob, alice)<=[friend(alice, bob)<=[]]].
%
% ?- mi_justify(mi_call(friends(bob, alice))).
%
% mi_call(friends(bob,alice)) because 
%     clean(friends(bob,alice),{friends(bob,alice)}) because 
%         friends(bob,alice)\=true
%         friends(bob,alice)\=false
%         friends(bob,alice)\=(_14360=_14362)
%         friends(bob,alice)\=(_14372\=_14374)
%         friends(bob,alice)\=dif(_14384,_14386)
%         friends(bob,alice)\=(_14396,_14398)
%         friends(bob,alice)\=(_14408;_14410)
%         friends(bob,alice)\=mi_clause(_14420,_14422)
%     mi_call_({friends(bob,alice)}) because 
%         mi_clause(friends(bob,alice),{friend(alice,bob)})
%         mi_call_({friend(alice,bob)}) because 
%             mi_clause(friend(alice,bob),true)
%             mi_call_(true) is a fact
%
% true .



% We define limited, a meta-interpreter that limits the number of subgoals used
% to resolve a goal, which we can use to do iterative deepening instead of
% depth-first search.
%
% If you used the simplest implementation for tree/1, you will notice that it
% generates infinitely many trees, but does not generate every tree.
% 
% ?- T = bin(bin(tip,_,tip), _, tip), tree(T).
% T = bin(bin(tip, _1476, tip), _1484, tip).
%
% ?- tree(T), T = bin(bin(tip,_,tip), _, tip).
% (nontermination)
%
% Using limited, we can guarantee universal termination.
%
% ?- limited((tree(T), T = bin(bin(tip,_,tip), _, tip)), 5).
% T = bin(bin(tip, _3628, tip), _3636, tip) ;
% false.
%
% If we want to check infinitely many solutions, we can use limited/2 or
% limited/3 with an increasingly large limit. This is iterative deepening,
% which is guaranteed to find every solution.
%
% ?- length(_, N), limited(tree(T), N, 0).
% N = 1,
% T = tip ;
% N = 3,
% T = bin(tip, _3510, tip) ;
% N = 5,
% T = bin(tip, _3522, bin(tip, _3656, tip)) ;
% N = 5,
% T = bin(bin(tip, _3646, tip), _3522, tip) ;
% N = 7,
% T = bin(tip, _3534, bin(tip, _3668, bin(tip, _3802, tip))) ;
% N = 7,
% T = bin(tip, _3534, bin(bin(tip, _3792, tip), _3668, tip)) ;
% N = 7,
% T = bin(bin(tip, _3658, tip), _3534, bin(tip, _3802, tip)) ;
% N = 7,
% T = bin(bin(tip, _3658, bin(tip, _3792, tip)), _3534, tip) ;
% N = 7,
% T = bin(bin(bin(tip, _3782, tip), _3658, tip), _3534, tip) ;
% N = 9,
% T = bin(tip, _3546, bin(tip, _3680, bin(tip, _3814, bin(tip, _3948, tip)))) .


% limited(+Goal, +Max) - solve goal with at most Max subgoals

limited(Goal, Max) :- limited(Goal, Max, _).

limited(Goal, Max, End) :- clean(Goal, G), limited_(G, Max, End).

limited_(true, N, N).
limited_(X = X, N, N).
limited_(dif(X,Y), N, N) :- dif(X,Y).
limited_(X \= Y, N, N) :- X \= Y.
limited_((P,Q), N0, N) :-
    limited_(P, N0, N1),
    limited_(Q, N1, N).
limited_((P;Q), N0, N) :-
    limited_(P, N0, N);
    limited_(Q, N0, N).
limited_(mi_clause(G, B), N0, N) :-
    N0 #= N + 1,
    mi_clause(G, B).
limited_({G}, N0, N) :-
    N0 #> 0,
    N1 #= N0 - 1,
    mi_clause(G, B),
    limited_(B, N1, N).




% ppj(+Justification) - pretty-print a justification
ppj(J) :- ppjs(0, J).

ppjs(I, Js) :- maplist(ppj(I), Js).

ppj(I, J) :-
    J \= (_ <= _),
    tab(I),
    writeln(J).
ppj(I, G <= []) :-
    tab(I),
    write(G),
    writeln(' is a fact').
ppj(I, G <= [J|Js]) :-
    tab(I),
    write(G),
    writeln(' because '),
    I2 #= I + 4,
    ppjs(I2, [J|Js]).