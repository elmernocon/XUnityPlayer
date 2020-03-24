#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from json import loads
from pathlib import Path
from typing import Dict

from scriptgen import \
    StringBuilder, \
    diff_text, \
    interpolate_text, \
    write_text_file


def get_text(
    template: str,
    expressions: Dict[str, str],
    template_name: str = None
) -> str:
    sb = StringBuilder()

    # Add a timestamps.
    if template_name.casefold().endswith(".md"):
        from scriptgen.templates.markdown import markdown_autogen
        sb.wb(markdown_autogen())
        sb.nl()
    elif template_name.casefold().endswith(".py"):
        from scriptgen import timestamp
        sb.wls(["#!/usr/bin/env python3", "# -*- coding: utf-8 -*-"])
        sb.nl()
        sb.wl(f"# Auto-generated: {timestamp()}")
        sb.nl()
    
    # Replace placeholders.
    # i.e. replace placeholders found in the text with values found in the expressions dictionary.
    # ex: ${SOME_KEY} → ACTUAL_VALUE
    interpolated_text = interpolate_text(template, expressions)

    # Write the interpolated text into the builder.
    sb.wl(interpolated_text)

    return str(sb)


if __name__ == "__main__":
    fdp = Path(__file__).parent

    templates_dir_name = "templates"

    # tools/templates/VALUES.t.json
    json_path = fdp / templates_dir_name / "VALUES.t.json"
    json_text = json_path.read_text()
    json = loads(json_text)

    templates = {
        # tools/templates/README.t.md → README.md
        (fdp / templates_dir_name / "README.t.md"): (fdp.parent.parent / "README.md"),
        # tools/templates/CONTRIBUTING.t.md → CONTRIBUTING.md
        (fdp / templates_dir_name / "CONTRIBUTING.t.md"): (fdp.parent.parent / "CONTRIBUTING.md"),

        # tools/templates/export_unitypackage.t.py → tools/export_unitypackage.t.py
        (fdp / templates_dir_name / "export_unitypackage.t.py"): (fdp / "export_unitypackage.py"),
    }

    for template_path, target_path in templates.items():
        template_text = template_path.read_text()
        text = get_text(
            template_text,
            json,
            template_name=template_path.name)
        write_text_file(
            text,
            target_path,
            # checks for relevant changes between two texts to determine whether to skip writing into a file.
            diff_func=diff_text,
            # filters out lines when checking for differences.
            filter_func=lambda line, idx: idx < 5 and (line.startswith("[//]: # (Auto-generated") or line.startswith("# Auto-generated")),
            log_func=lambda message: print(message)
        )
