# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.12

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files\JetBrains\CLion 2018.2.4\bin\cmake\win\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files\JetBrains\CLion 2018.2.4\bin\cmake\win\bin\cmake.exe" -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = C:\Users\J05h\CLionProjects\assignment1test

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = C:\Users\J05h\CLionProjects\assignment1test\cmake-build-debug-mingw

# Include any dependencies generated for this target.
include CMakeFiles/assignment1test.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/assignment1test.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/assignment1test.dir/flags.make

CMakeFiles/assignment1test.dir/main.c.obj: CMakeFiles/assignment1test.dir/flags.make
CMakeFiles/assignment1test.dir/main.c.obj: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=C:\Users\J05h\CLionProjects\assignment1test\cmake-build-debug-mingw\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/assignment1test.dir/main.c.obj"
	C:\PROGRA~2\MINGW-~1\I686-8~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles\assignment1test.dir\main.c.obj   -c C:\Users\J05h\CLionProjects\assignment1test\main.c

CMakeFiles/assignment1test.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/assignment1test.dir/main.c.i"
	C:\PROGRA~2\MINGW-~1\I686-8~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E C:\Users\J05h\CLionProjects\assignment1test\main.c > CMakeFiles\assignment1test.dir\main.c.i

CMakeFiles/assignment1test.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/assignment1test.dir/main.c.s"
	C:\PROGRA~2\MINGW-~1\I686-8~1.0-P\mingw32\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S C:\Users\J05h\CLionProjects\assignment1test\main.c -o CMakeFiles\assignment1test.dir\main.c.s

# Object files for target assignment1test
assignment1test_OBJECTS = \
"CMakeFiles/assignment1test.dir/main.c.obj"

# External object files for target assignment1test
assignment1test_EXTERNAL_OBJECTS =

assignment1test.exe: CMakeFiles/assignment1test.dir/main.c.obj
assignment1test.exe: CMakeFiles/assignment1test.dir/build.make
assignment1test.exe: CMakeFiles/assignment1test.dir/linklibs.rsp
assignment1test.exe: CMakeFiles/assignment1test.dir/objects1.rsp
assignment1test.exe: CMakeFiles/assignment1test.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=C:\Users\J05h\CLionProjects\assignment1test\cmake-build-debug-mingw\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable assignment1test.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\assignment1test.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/assignment1test.dir/build: assignment1test.exe

.PHONY : CMakeFiles/assignment1test.dir/build

CMakeFiles/assignment1test.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\assignment1test.dir\cmake_clean.cmake
.PHONY : CMakeFiles/assignment1test.dir/clean

CMakeFiles/assignment1test.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" C:\Users\J05h\CLionProjects\assignment1test C:\Users\J05h\CLionProjects\assignment1test C:\Users\J05h\CLionProjects\assignment1test\cmake-build-debug-mingw C:\Users\J05h\CLionProjects\assignment1test\cmake-build-debug-mingw C:\Users\J05h\CLionProjects\assignment1test\cmake-build-debug-mingw\CMakeFiles\assignment1test.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/assignment1test.dir/depend
