PROJECT_NAME := racoon-filesystem-checker
IMAGE_NAME := $(PROJECT_NAME)-image
TEST_COMPOSE_FILE := compose.test.yml
PROD_COMPOSE_FILE := compose.prod.yml

.PHONY: build
build:
	./gradlew build

.PHONY: run
run: build
	podman-compose -f $(TEST_COMPOSE_FILE) up -d --build

.PHONY: release
release:
	podman-compose -f $(PROD_COMPOSE_FILE) up -d --build

.PHONY: clean
clean:
	podman-compose -f $(TEST_COMPOSE_FILE) down
	podman rmi -f $(IMAGE_NAME)

.PHONY: help
help:
	@echo "Racoon Filesystem Checker"
	@echo "----------------------------------------"
	@echo "Felhasználható célok:"
	@echo "  - build:        Gradle build futtatása"
	@echo "  - run:          Docker image futtatása Podman segítségével"
	@echo "  - release:      Éles környezetben való indítás podman-compose segítségével"
	@echo "  - clean:        Törli az előző futtatás során létrejött konténereket és image-eket"
	@echo "  - help:         Ez a súgó üzenet"

# Alapértelmezett célpont
.DEFAULT_GOAL := help