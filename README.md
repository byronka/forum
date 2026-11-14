Forum
=====

An internet forum


Quick start:
------------

Test the program:

```shell
make test
```

To build:

```shell
make package
```

To run the jar:

```shell
make run
```

then visit http://localhost:8080

Stop the application (at the commend line) py pressing crtl+c


Development environment
-----------------------

* Requires Java 25
* Built primarily in two environments: Windows Cygwin, and Mac.  Both
  are unix-like environments, providing tools like Make, Awk, and Grep.

Approach
--------

Uses minimal dependencies.  The framework in use, [Minum](https://github.com/byronka/minum), is one of the
smallest and least-powerful available, chosen because the Java
development kit already provides incredible capability.  The desire is
to keep lean.  We will only include code which provides a net
positive.  Anything which adds weight without significant benefits is
a net cost to the system, causing uncertainty and unnecessary edge
conditions.

The system is built using pragmatic TDD (test-driven development).
This means that most new capabilities will be built with
experimentation by tests.  Being pragmatic, not necessarily all lines
of code will be tested, and [tests are not always required](https://renomad.com/blogposts/why_write_this_test.html).
