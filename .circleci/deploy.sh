#!/usr/bin/env bash
set -euo pipefail

# Configure git and set author info.
git config --global user.email "$GH_EMAIL" > /dev/null 2>&1
git config --global user.name "$GH_NAME" > /dev/null 2>&1
git config --global push.default simple

# Copy dist directory to tmp location.
cp -r presentation/dist /tmp/dist

# Switch back to root dir.
cd ..

# Checkout target branch.
git checkout -b gh-pages

# Delete all files except the .git folder.
find -mindepth 1 -maxdepth 1 -not -name .git -exec rm -rf {} \;

# Copy the contents of the dist director.
cp -r /tmp/dist/. ./

# Add all files.
git add --all

# Commit and push if changed.
if ! git diff-index --quiet HEAD ; then
    git commit -m "Automated deploy [ci skip]"
    git push --force --quiet
else
    echo "Nothing changed, skipping commit and push."
fi

# Switch back to master and clean.
git checkout master
git clean -f -d