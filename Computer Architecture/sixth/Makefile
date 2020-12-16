all: runsixth

runsixth: sixth.c
	gcc -g -Wall -Werror -fsanitize=address -std=c99 sixth.c -o sixth

clean:
	rm -rf sixth
