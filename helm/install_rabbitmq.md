## Install RabbitMQ

```shell
helm install rabbitmq oci://registry-1.docker.io/bitnamicharts/rabbitmq --namespace homework --set auth.username=my-user --set auth.password=my-password --set auth.erlangCookie=my-erlang-cookie
```

```shell
kubectl expose service rabbitmq --type=NodePort --target-port=15672 --name=rabbitmq-np -n homework
````

```shell
minikube service rabbitmq-np -n homework
````

Open in Browser:
http://172.28.22.91:30987/

___

## Uninstall RabbitMQ

```shell
helm delete rabbitmq --namespace homework
```


