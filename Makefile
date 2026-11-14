# This file is used to configure GNU Make for our project's use.  It provides us a few nice
# benefits:
#
# 1) When just running "make", it shows the possible recipes (e.g. clean, lint, etc.), and does so quickly
# 2) Some commands are just faster and easier - for example, the "clean" recipe just
#    removes some directories.  No need for the whole Maven startup for such a task
# 3) Using Make allows easy aliases.  Easier to type "make mutation_test" rather than remembering
#    to type "mvn test-compile org.pitest:pitest-maven:mutationCoverage"
# 4) Using Make lets us handle complex scenarios easily with complete control - for example, see the "jar" recipe.
# 5) Lets us add little bits of better experience.  Like, how starting the mutation tests it provides a
#    hint about expecting timeouts.  Adding that as a Maven customization would be such overkill it would
#    never happen, but adding it in the Makefile is a piece of cake.
#
# As a quick note: the word "PHONY" means that the recipe (e.g. clean, lint, etc) is not the name of a file. Without it,
#                  there could be a conflict if there was a file at the top level with that name.  Using PHONY
#                  means that GNU Make won't look for a file.

##
# Project name - used to set the jar's file name
##
PROJ_NAME := forum
VERSION=1.0.0
MAVEN := ./mvnw

##
# overall output directory
##
OUT_DIR := target

##
# output directory for main source files
##
OUT_DIR_MAIN := $(OUT_DIR)/classes

.PHONY: all
##
# default target(s)
##
all: help

.PHONY: clean
#: clean up any output files
clean:
	 @rm -fr $(OUT_DIR)

.PHONY: package
#: build the classes and bundle into a jar
package:
	 @${MAVEN} package
	 @echo "jar is located at target/forum-1.0.0-jar-with-dependencies.jar"

.PHONY: run
#: run the application (depends on package)
run: target/forum-1.0.0-jar-with-dependencies.jar
	 java -jar target/forum-1.0.0-jar-with-dependencies.jar

target/forum-1.0.0-jar-with-dependencies.jar:
	@echo "Error: cannot run application!" >&2
	@echo "" >&2
	@echo "  target/forum-1.0.0-jar-with-dependencies.jar must first exist." >&2
	@echo "  run 'make package'" >&2
	@echo "" >&2
	@echo "" >&2
	@exit 1

.PHONY: test
#: run tests with code coverage
test:
	 @${MAVEN} jacoco:prepare-agent test jacoco:report
	 @echo "test code coverage report is located in target/site/jacoco"

.PHONY: test_quiet
#: run tests with minimal output
test_quiet:
	 @${MAVEN} jacoco:prepare-agent test jacoco:report -DLOG_LEVELS=NONE 2>&1 | grep -E "Running com\.|Tests run:|BUILD SUCCESS|BUILD FAILURE" || true
	 @echo "test code coverage report is located in target/site/jacoco"

.PHONY: mutation_test
#: run mutation testing using pitest
mutation_test:
	 @echo "be patient - this can take 10 minutes, for real"
	 @echo "It will complain about TIMED_OUT a lot - but don't worry.  Just wait."
	 @${MAVEN} test-compile org.pitest:pitest-maven:mutationCoverage
	 @echo "mutation test report is located in target/pit-reports"

.PHONY: javadoc
#: build the javadoc documentation
javadoc:
	 @${MAVEN} javadoc:javadoc
	 @echo "Javadocs are located in target/reports/apidocs"

# a handy debugging tool.  If you want to see the value of any
# variable in this file, run something like this from the
# command line:
#
#     make print-CLS
#
# and you'll get something like: CLS = out/inmra.logging/ILogger.class out/inmra.logging/Logger.class out/inmra.testing/Main.class out/inmra.utils/ActionQueue.class
print-%:
	    @echo $* = $($*)

# This is a handy helper.  This prints a menu of items
# from this file - just put hash+colon over a target and type
# the description of that target.  Run this from the command
# line with "make help"
help:
	 @echo
	 @echo Help
	 @echo ----
	 @echo
	 @grep -B1 -E "^[a-zA-Z0-9_-]+:([^\=]|$$)" Makefile \
     | grep -v -- -- \
     | sed 'N;s/\n/###/' \
     | sed -n 's/^#: \(.*\)###\(.*\):.*/\2###\1/p' \
     | column -t  -s '###'

# This configures Make to show a more helpful message when the
# user enters an invalid target.
%:
	@echo ""
	@echo "Error: Invalid target '$@'."
	@echo ""
	@$(MAKE) --silent --no-print-directory help
	@echo ""
	@echo ""
	@exit 0