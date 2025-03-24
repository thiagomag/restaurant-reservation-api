.PHONY: test clean build

test: unitTest integrationTest systemTest

unitTest:
	./gradlew clean unitTest

integrationTest:
	./gradlew clean integrationTest

systemTest:
	./gradlew clean systemTest

performanceTest:
	./gradlew clean performanceTest

clean:
	./gradlew clean

build:
	./gradlew build

docker-build:
	docker buildx build \
		--platform linux/amd64,linux/arm64 \
		-t thiagomag/restaurant-reservation-api:latest \
		--push .

docker-start: docker-build
	docker compose -f docker-compose.yml up -d

docker-stop:
	docker compose -f docker-compose.yml down