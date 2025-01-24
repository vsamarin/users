# Install Grafana

```shell
helm repo add grafana https://grafana.github.io/helm-charts
```

```shell
helm install grafana grafana/grafana
```

```shell
kubectl expose service grafana --type=NodePort --target-port=3000 --name=grafana-np
```

```shell
kubectl get secret --namespace default grafana -o jsonpath="{.data.admin-password}"
```

```shell
minikube service grafana-np
```

# Uninstall Grafana

```shell
helm delete grafana
```
