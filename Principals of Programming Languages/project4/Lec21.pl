food(apple).

beats(rock, scissors).
beats(bomb, Anything).

eats(johnny, Something) :- food(Something).


cat(garfield).
cat(tom).

dog(odie).
dog(scooby).

mouse(jerry).

chases(jerry, tom).
chases(garfield, odie).
chases(A, B) :- cat(A), mouse(B).
chases(A, B) :- dog(A), cat(B).
