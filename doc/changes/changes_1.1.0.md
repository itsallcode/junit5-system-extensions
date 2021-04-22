# JUnit5 System Extensions 1.1.0, released 2019-12-28

Code name: Mute output

## Summary

To mute the output (i.e. don't forward output to System.out / System.err) call stream.captureMuted() instead of stream.capture(). This can be useful to speed up unit tests.

## Features

* #16: Mute output of captured streams