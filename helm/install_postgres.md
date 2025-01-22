## Helm Install Postgresql

https://adamtheautomator.com/postgres-to-kubernetes/

### Create PersistentVolume

```shell
kubectl apply -f ./postgresql/pv.yaml
```

### Create PersistentVolumeClaim

```shell
kubectl apply -f ./postgresql/pvc.yaml
```

### Install Postgres

```shell
helm install postgresql -f ./postgresql/values.yaml oci://registry-1.docker.io/bitnamicharts/postgresql
```

### Connect to Postgresql

```shell
kubectl run postgresql-dev-client --rm --tty -i --restart="Never" --namespace default --image docker.io/bitnami/postgresql:14.1.0-debian-10-r80 --env="PGPASSWORD=test" --command -- psql --host postgresql -U test -d users -p 5432
```

___

## Uninstall Postgres

```shell
helm delete postgresql
```

### Delete PersistentVolumeClaim

```shell
kubectl delete -f ./postgresql/pvc.yaml
```

### Delete PersistentVolume

```shell
kubectl delete -f ./postgresql/pv.yaml
```
