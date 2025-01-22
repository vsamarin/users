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
