# Simple Prometheus/Grafana Demo

Project serves as a playground to show simple Prometheus/Grafana stack example together with 
typical backend service infrastructure - Spring Boot RESTful Service and MongoDB persistence.

## Pre-requisites

* Java 21 SDK
* Docker (compose)

## Building and starting

Spring Boot project uses jib plugin to containerize the app

### Step 1. Build microservice container
```
./gradlew clean build -x test jibDockerBuild
```
### Step 2. Start compose stack
```
docker-compose --file docker/compose.yml up
```
## Access Grafana and examine gathered metrics
* [Grafana](http://localhost:3000)
```
user: admin
password: admin
```
* [Prometheus](http://localhost:9090)
* Prometheus metrics exposed
```
http://localhost:8080/actuator/prometheus
http://localhost:9216/metrics
```

## Links

* [Official Gradle documentation](https://docs.gradle.org)
* [Prometheus Introduction](https://www.devopsschool.com/blog/what-is-prometheus-and-how-it-works/)
* [Prometheus Client Libraries](https://prometheus.io/docs/instrumenting/clientlibs/)
* [Getting started with Grafana and Prometheus](https://grafana.com/docs/grafana/latest/getting-started/get-started-grafana-prometheus/)
* [Histograms and Summaries explained](https://prometheus.io/docs/practices/histograms/)
* [Prometheus Exporters List](https://github.com/prometheus/docs/blob/main/content/docs/instrumenting/exporters.md)
* [Micrometer Docs](https://docs.micrometer.io/micrometer/reference/concepts.html)