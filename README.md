# Yar RPC Client for Java

[![Build Status](https://travis-ci.org/starlight36/yar-client-java.png?branch=master)](https://travis-ci.org/starlight36/yar-client-java)
[![Coverage Status](https://coveralls.io/repos/starlight36/yar-client-java/badge.svg)](https://coveralls.io/r/starlight36/yar-client-java)
[![JitPack](https://img.shields.io/github/tag/starlight36/yar-client-java.svg?label=JitPack)](https://jitpack.io/#starlight36/yar-client-java/v0.1.2)

A simple Java client for [Yar RPC framework](https://github.com/laruence/yar).

## Introduction

Yar is a light RPC framework for PHP written by Laruence. 
If you are looking for a Java client for Yar, this project may
solve your problems.

## Usage

Use this client is very simple, just few codes:

```java
YarClient client = new YarClient("http://localhost/yar.php");
Integer timestamp = client.call("doSomething", Integer, "Hello");
System.out.println(timestamp);
client.close();
```

## Downloads

### Jar

Coming soon..

### Maven

Add the JitPack repository to your build file.

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Add the dependency in the form.

```xml
<dependency>
    <groupId>com.github.starlight36</groupId>
    <artifactId>yar-client-java</artifactId>
    <version>v0.1.1</version>
</dependency>
```

## Limitation

* Native PHP packager is not support correctly.
* Concurrent or asynchronous call is not support temporarily.

## Feedback

If you find some bugs, use [Issues](https://github.com/starlight36/yar-client-java/issues).
