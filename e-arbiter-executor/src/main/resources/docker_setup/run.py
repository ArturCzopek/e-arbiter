#! /usr/bin/env python

import os
import sys
import commands
import shlex

# change directory
os.chdir("solution")

# variables & utilities

prog = "program"
test_data = open("test_data", "r");

lang_commands = [
    # language, compile command, execute command
    (".c", "gcc -o {prog} {prog}.c".format(prog=prog), "./{}".format(prog))
]

program = commands.getstatusoutput("find . -name '{}*'".format(prog))[1]


def prepare_command(execute_command, args):
    cmd = execute_command
    for arg in args:
        cmd += ' "{}"'.format(arg)
    return cmd


# find commands tuple for this extension
for tuple in lang_commands:
    if program.endswith(tuple[0]):
        ext, compile_command, execute_command = tuple

# compile
status, output = commands.getstatusoutput(compile_command)
if status != 0:
    print output
    sys.exit()

iterations = 0
successes = 0

# execute
for line in test_data.readlines():
    args = shlex.split(line)
    expected = args.pop()

    status, output = commands.getstatusoutput(prepare_command(execute_command, args))

    result = "EXPECTED: {} | ACTUAL: {} | PASSED: {}".format(expected, output, output == expected)

    if output == expected:
        successes += 1
    iterations += 1

    print result

if successes == iterations:
    print "ALL PASSED"
else:
    print "{} FAILED".format(iterations - successes)

print "FINISHED"
