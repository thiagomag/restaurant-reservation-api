.PHONY: test clean build

test:
	./gradlew clean test

unitTest:
	./gradlew clean unitTest

integrationTest:
	./gradlew clean integrationTest

systemTest:
	./gradlew clean systemTest

clean:
	./gradlew clean

build:
	./gradlew build

docker-build:
	docker buildx build \
		--platform linux/amd64,linux/arm64 \
		-t $(DOCKER_USERNAME)/$(DOCKER_IMAGE_NAME):latest \
		--push .