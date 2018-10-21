Thank you for your interest in helping us to make the Junit5-System-Extensions (J5SE) better!

# Goal of This Document
This document aims at answering all the questions potential contributors to J5SE might have before feeling ready to get started.

# What This Document is not
If you are looking for general information about what OpenFastTrace is, please make sure to check out the [ReadMe](./README.md) that comes with the project.

# What Kind of Contributions can I Make?

## Contributing Code
If you are a programmer and want to help us improve the implementation, test cases or design of J5SE please create a branch of the current `develop` branch of J5SE using [git](https://git-scm.com/) and make your changes on that branch.

Then please create a pull request and ask for a review by one of the core team members (e.g. `redcatbear` or `kaklakariada`).
The reviewers will either ask you to work in review findings or if there are none, merge your branch.

## Testing
We are happy if you test J5SE! While we do a great deal of testing ourselves, we want J5SE to be as portable as possible. So it is especially helpful for us if you test on a platform that we don't have.

If you find a bug, please let us know by writing an [issue ticket](https://github.com/itsallcode/openfasttrace/issues/new?template=Bug_report.md). There is a template for bug tickets that helps you provide all the information that we need to reproduce and tackle the bug you found.

If you are a programmer, a code contribution in form of an automatic unit test case would be most appreciated, since this will make reproduction of the issue easier and prevent future regressions.

## Ideas
Last but not least if you have ideas for ways to improve or extend J5SE, feel free to write a [feature request](https://github.com/itsallcode/openfasttrace/issues/new?template=Feature_request.md).

# Style Guides
We want J5SE to have a professional and uniform coding style. Formatter rules for Eclipse are part of the project. If you use a different editor, please make sure to match the current code formatting.

We develop the code following the principles described in Robert C. Martin's book "Clean Code" as our guide line for designing and organizing the code.