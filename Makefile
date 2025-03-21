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