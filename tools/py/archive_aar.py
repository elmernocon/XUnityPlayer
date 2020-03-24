#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from pathlib import Path
from shutil import copy
from subprocess import call


def main() -> None:
    # Get paths.
    fdp = Path(__file__).parent
    rdp = fdp.parent.parent

    # Get build_gradle.py script.
    script = str(fdp / "build_gradle.py")

    # Build gradle projects.
    build_gradle(script)

    # Copy the com.unity3d.xplayer.aar file to the refs/xplayer folder.
    aar = rdp / "bin/Release/xplayer" / "com.unity3d.xplayer.aar"
    if aar.exists():
        rrxupdp = rdp / "refs/xplayer"
        rrxupdp.mkdir(parents=True, exist_ok=True)
        suffix = aar.suffix
        name = f"{aar.name.replace(suffix, '')}_latest{suffix}"
        src = aar
        dst = rrxupdp / name
        if dst.exists():
            dst.unlink()
        copy(str(src), str(dst))
        print()
        print(f"Copied {str(src.relative_to(rdp))}")
        print(f"     → {str(dst.relative_to(rdp))}")


def build_gradle(script: str) -> None:
    projects = {
        "src/xplayer": [
            ":xplayer:archiveDebug",
            ":xplayer:archiveRelease",
            ":xample:archiveDebug",
            ":xample:archiveRelease",
        ],
        "src/deeplinking": [
            ":deeplinking:archiveDebug",
            ":deeplinking:archiveRelease"
        ]
    }
    for project, tasks in projects.items():
        call([
            "python3", script,
            "-p", project,
            "-t", *tasks])


if __name__ == "__main__":
    main()
