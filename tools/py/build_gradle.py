#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from argparse import ArgumentParser
from os import chdir, getcwd
from pathlib import Path
from platform import system

from funity.util import run_process

logs = []


def main() -> None:
    # Get arguments.
    arguments = parse_arguments()
    project = arguments.project
    tasks = arguments.tasks

    # Get paths.
    fdp = Path(__file__).parent
    rdp = fdp.parent.parent
    pdp = rdp / arguments.project

    # Get gradle file.
    gradle_file = pdp.resolve() / get_gradle_file()

    # Throw if gradle file does not exist.
    if not gradle_file.exists():
        raise FileNotFoundError()

    # Change current working directory to the project path.
    owd = getcwd()
    chdir(str(pdp))

    # Run tasks.
    for task in tasks:
        print()
        run_gradle_task(
            gradle_file,
            project,
            task
        )

    # Revert to the original working directory.
    chdir(owd)


def parse_arguments() -> any:
    argument_parser = ArgumentParser(
        description="Builds a gradle project."
    )
    argument_parser.add_argument(
        "-p",
        "--project",
        type=str,
        required=True,
        help="<Required> Gradle project path relative to the root directory."
    )
    argument_parser.add_argument(
        "-t",
        "--tasks",
        nargs="+",
        required=True,
        help="<Required> Gradle tasks to run."
    )
    return argument_parser.parse_args()


def get_gradle_file() -> str:
    platforms = {
        "Darwin": "gradlew",
        "Linux": "gradlew",
        "Windows": "gradlew.bat"
    }
    return platforms[system()]


def run_gradle_task(
    gradle_file: Path,
    project: str,
    task: str
) -> None:
    global logs
    logs = []
    print(f"Running gradle task ./{project} [{task}]")
    return_code = run_process([
            str(gradle_file),
            task
        ],
        log_func=filter_gradle_task)
    if return_code != 0:
        for log in logs:
            print(log, end="")
    print(f"→ {'Success' if return_code == 0 else 'Failed'}")


def filter_gradle_task(line: str) -> None:
    global logs
    logs.append(line)
    start = ": > Task "
    line = line.rstrip()
    if  line.startswith(start) and \
        not line.endswith("UP-TO-DATE") and \
            not line.endswith("NO-SOURCE"):
        line = f"↳ {line.replace(start, '')}"
        print(line)


if __name__ == "__main__":
    main()
