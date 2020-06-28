# Spark: unit, integration and end2end tests with Scala

## Building

```
sbt clean compile scalastyle coverage test test:scalastyle coverageReport
```

## Assembly, generate jar file

```
sbt assembly
```
