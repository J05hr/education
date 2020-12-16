all: runseventh

runseventh: seventh.c
	gcc -g -Wall -Werror -fsanitize=address -std=c99 seventh.c -o seventh

clean:
	rm -rf seventh
