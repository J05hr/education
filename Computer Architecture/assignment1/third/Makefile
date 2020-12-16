all: runthird

runthird: third.c
	gcc -g -Wall -Werror -fsanitize=address -std=c99 third.c -o third

clean:
	rm -rf third
