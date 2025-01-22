## Docker

```shell
docker build . --platform linux/amd64 -t vsamarin/users:1.0.0
```

```shell
docker push vsamarin/users:1.0.0
```

```shell
docker run -p 8080:8000 --env-file .env vsamarin/users:1.0.0
```

## Kubernetes

https://adamtheautomator.com/postgres-to-kubernetes/

# Create ConfigMap

```shell
kubectl apply -f config.yaml
```

# Checking ConfigMap

```shell
kubectl get configmap
```

# Create PersistentVolume

```shell
kubectl apply -f pv.yaml
```

# Create PersistentVolumeClaim

```shell
kubectl apply -f pvc.yaml
```

# Checking PersistentVolume

```shell
kubectl get pv
```

# Checking PersistentVolumeClaim

```shell
kubectl get pvc
```

# Create Deployment

```shell
kubectl apply -f deployment.yaml
```

# Create Service

```shell
kubectl apply -f service.yaml
```

# Connect to Postgres

```shell
kubectl exec -it postgresql-5db9f48876-9v4n8 -- psql -h localhost -U test -p 5432 users
```

