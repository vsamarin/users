## Build Docker Image

```shell
docker build . --platform linux/amd64 -t vsamarin/users:1.0.1
```

## Push Docker Image

```shell
docker push vsamarin/users:1.0.1
```

## Run Docker Image

```shell
docker run -p 8080:8000 --env-file .env vsamarin/users:1.0.1
```

## Errors rate

https://stackoverflow.com/questions/78806290/how-to-create-error-rate-table-in-grafana-using-http-server-requests-seconds-cou/78882599

## Nginx ingress Controller Prometheus

https://docs.nginx.com/nginx-ingress-controller/logging-and-monitoring/prometheus/

## Spring Boot Default Metrics

https://www.baeldung.com/spring-boot-prometheus
https://tomgregory.com/aws/spring-boot-default-metrics/
https://stackoverflow.com/questions/55311460/calculate-the-number-of-requests-per-second-using-spring-boot-actuator
https://habr.com/ru/articles/548700/

## Grafana

https://grafana.com/docs/grafana/latest/setup-grafana/installation/docker/
https://grafana.com/docs/grafana-cloud/monitor-applications/asserts/enable-prom-metrics-collection/application-frameworks/springboot/
https://grafana.com/grafana/dashboards/5373-micrometer-spring-throughput/

## Prometheus

https://prometheus.io/docs/prometheus/latest/installation/
https://prometheus.io/docs/concepts/metric_types/#summary
https://prometheus.io/docs/practices/histograms/
https://prometheus.io/docs/prometheus/latest/querying/functions/#irate
https://habr.com/ru/companies/tochka/articles/693834/

