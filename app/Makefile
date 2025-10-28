.DEFAULT_GOAL := build-run

setup:
	./gradlew wrapper --gradle-version 9.0

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean install

run-dist:
	./build/install/app/bin/app

run:
	./gradlew run

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain

check-deps:
	./gradlew dependencyUpdates -Drevision=release

sonar-run:
	./gradlew build sonar --info

build-run: build run

.PHONY: build