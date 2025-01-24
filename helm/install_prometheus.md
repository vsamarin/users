# Install Prometheus

```shell
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
```

```shell
helm install prometheus prometheus-community/prometheus
## helm install prometheus prometheus-community/kube-prometheus-stack
```

```shell
kubectl expose service prometheus-server --type=NodePort --target-port=9090 --name=prometheus-server-np
```

```shell
kubectl create configmap prometheus-config --from-file=prometheus.yml
```

```shell
kubectl get pods -l app.kubernetes.io/instance=prometheus
```

```shell
minikube service prometheus-server-np
```

# Uninstall Prometheus

```shell
helm delete prometheus
```

```shell
kubectl delete configmap prometheus-config
```
