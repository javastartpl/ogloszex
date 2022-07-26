docker build . -t ogloszex
docker stop ogloszex || true
docker rm ogloszex || true
docker run -d -p 8080:8080 --name=ogloszex ogloszex