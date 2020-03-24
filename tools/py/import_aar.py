#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from pathlib import Path
from shutil import copy


def main():
    # Get paths.
    fdp = Path(__file__).parent
    rdp = fdp.parent.parent

    # Check if the bin/Release folder exists.
    brdp = rdp / "bin/Release"
    if not brdp.exists():
        raise IOError("Folder not found.")

    # Check if the tests/ExtendedPlayer.Unity folder exists.
    txrdp = rdp / "tests/XPlayer.Test"
    if not txrdp.exists():
        raise IOError("Cannot find the test Unity project.")

    txrapadp = txrdp / "Assets/Plugins/Android"

    packages = [
        "xplayer",
        "xample",
        "deeplinking"
    ]

    # Copy .aar files found in each package into the Assets/Plugins/Android folder.
    for package in packages:
        pp = brdp / package
        if not pp.exists():
            continue
        for aar_file in list(pp.glob("**/*.aar")):
            src = aar_file
            dst = txrapadp / aar_file.name
            dst.parent.mkdir(parents=True, exist_ok=True)
            copy(str(src), str(dst))
            print()
            print(f"Copied {str(src)}")
            print(f"     → {str(dst)}")


if __name__ == "__main__":
    main()
