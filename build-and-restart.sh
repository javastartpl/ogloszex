docker build . -t ogloszex
docker stop ogloszex
docker rm ogloszex
docker run -d -p 8080:8080 --name=ogloszex ogloszex