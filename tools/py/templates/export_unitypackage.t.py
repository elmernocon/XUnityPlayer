from argparse import ArgumentParser
from pathlib import Path
from shutil import move, rmtree

from funity import \
    UnityEditor, \
    UnityProject, \
    UnityVersion

d_output = "${project_name}"
d_version = "${version}"
d_project_path: str = "${test_project_path}"
d_asset_paths = [
    "Assets/Plugins/Android/AndroidManifest.xml",
    "Assets/Plugins/Android/com.unity3d.xplayer.aar",
    "Assets/Plugins/Android/com.unity3d.xample.aar",
    "Assets/Plugins/Android/com.unity3d.deeplinking.aar",
    "Assets/Scenes/Main.unity",
    "Assets/Scripts/Listener.cs",
    "Assets/Scripts/Logger.cs"
]


def main():
    # Get arguments.
    arguments = parse_arguments()
    output = arguments.output
    version = arguments.version
    project_path = arguments.project
    asset_paths = arguments.assets

    # Get paths.
    fdp = Path(__file__).parent
    rdp = fdp.parent.parent
    cache_path = fdp / "editor.cache"

    # Get Unity editor.
    editor = get_editor(str(cache_path))

    # Get Unity project.
    project = UnityProject(str(rdp / project_path))

    # Setup output path.
    output_dir_name = "UnityPackages"
    output_dir = project.path / output_dir_name
    output_dir.mkdir(parents=True, exist_ok=True)

    # Setup file name.
    file_extension = ".unitypackage"
    file_name = f"{output}_v{version}{file_extension}"
    file_path = output_dir / file_name

    try:
        # Run 'exportPackage' on the Unity project using the Unity editor CLI.
        return_code = editor.run(
            "-projectPath", str(project),
            "-exportPackage", *asset_paths, str(file_path.relative_to(project.path)),
            # log_func=lambda l: print(l, end=""),
            cli=True  # Shorthand for '-batchmode', '-nographics', '-quit', '-silent-crashes'.
        )

        if return_code != 0 or not file_path.exists():
            raise Exception("Failed to export package.")

        # Setup bin path.
        bupdp = rdp / "bin/UnityPackages"
        bupdp.mkdir(parents=True, exist_ok=True)

        # Move file into bin/UnityPackages folder.
        src = file_path
        dst = bupdp / file_path.name
        move(str(src), str(dst))
        print(f"{str(src.relative_to(rdp))} → {str(dst.relative_to(rdp))}")
    finally:
        # Delete output directory.
        rmtree(str(output_dir), ignore_errors=True)


def parse_arguments() -> any:
    argument_parser = ArgumentParser(
        description="Exports a .unitypackage from a Unity project."
    )
    argument_parser.add_argument(
        "-o",
        "--output",
        type=str,
        required=False,
        help="The name of the *.unitypackage file.",
        default=d_output
    )
    argument_parser.add_argument(
        "-v",
        "--version",
        type=str,
        required=False,
        help="The version of the *.unitypackage file.",
        default=d_version
    )
    argument_parser.add_argument(
        "-p",
        "--project",
        type=str,
        required=False,
        help="Unity project path relative to the root directory.",
        default=d_project_path
    )
    argument_parser.add_argument(
        "-a",
        "--assets",
        nargs="+",
        required=False,
        help="Asset paths relative to the Unity project directory.",
        default=d_asset_paths
    )
    return argument_parser.parse_args()


def get_editor(cache: str) -> UnityEditor:
    # Find all Unity editor installations and cache the results.
    editors = UnityEditor.find_in(cache=cache)

    # Filter results to only include 2019.2 versions.
    editor_version = UnityVersion(2019, 2)
    editors = [e for e in editors if e.version.is_equal_to(editor_version, fuzzy=True)]

    # Throw an exception if no compatible editor version is found.
    if not editors:
        raise Exception(f"No Unity {editor_version} found.")

    # Return the first compatible editor.
    return editors[0]


if __name__ == "__main__":
    main()
