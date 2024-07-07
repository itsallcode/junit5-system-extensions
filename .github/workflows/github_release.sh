#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

base_dir="$( cd "$(dirname "$0")/../.." >/dev/null 2>&1 ; pwd -P )"
readonly base_dir
readonly pom_file="$base_dir/pom.xml"

# Read project version from pom file
project_version=$(grep "<version>" "$pom_file" | sed --regexp-extended 's/\s*<version>(.*)<\/version>\s*/\1/g' | head --lines=1)
readonly project_version
echo "Read project version '$project_version' from $pom_file"

readonly changes_file="$base_dir/doc/changes/changes_${project_version}.md"
notes=$(cat "$changes_file")
readonly notes

readonly title="Release $project_version"
readonly tag="$project_version"
echo "Creating release:"
echo "Git tag      : $tag"
echo "Title        : $title"
echo "Changes file : $changes_file"

release_url=$(gh release create --latest --title "$title" --notes "$notes" --target main "$tag")
readonly release_url
echo "Release URL: $release_url"
