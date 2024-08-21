build:
	mvn clean package -DskipTests

docker: build
	docker build . -t api-impl:1.0

run: docker
	docker-compose -f docker-compose.yml up  --remove-orphans