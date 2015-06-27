# Yar RPC Client for Java

A simple Java client for [Yar RPC framework](https://github.com/laruence/yar).

## Introduction

Yar is a light RPC framework for PHP written by Laruence. 
If you are looking for a Java client for Yar, this project may
solve your problems.

## Usage

Use this client is very simple, just few codes:

```
YarClient client = new YarClient("http://localhost/yar.php");
Integer timestamp = client.call("doSomething", Integer, "Hello");
System.out.println(timestamp);
client.close();
```

## Downloads

### Jar

Coming soon..

### Maven

Coming soon..

## Limitation

* Native PHP packager is not support correctly.
* Concurrent or asynchronous call is not support temporarily.

## Feedback

If you find some bugs, use [Issues](https://github.com/starlight36/yar-client-java/issues).
