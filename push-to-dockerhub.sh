#!/bin/bash

# Variables for the first image
OLD_IMAGE_1="cppenloglou/atm-frontend:latest"
NEW_IMAGE_1="cppenloglou/atm-service-frontend:1.0"

# Variables for the second image
OLD_IMAGE_2="cppenloglou/atm-service:latest"
NEW_IMAGE_2="cppenloglou/atm-service-api:1.0"

# Docker Hub credentials (optional if you're already logged in)
DOCKER_USERNAME="my_username"
DOCKER_PASSWORD="my_password"

# Log in to Docker Hub (optional if already logged in)
echo "Logging in to Docker Hub..."
echo "$DOCKER_PASSWORD" | docker login --username "$DOCKER_USERNAME" --password-stdin
if [ $? -ne 0 ]; then
    echo "Docker login failed. Exiting..."
    exit 1
fi

# Tag the first image with the new tag
echo "Tagging image $OLD_IMAGE_1 as $NEW_IMAGE_1..."
docker tag "$OLD_IMAGE_1" "$NEW_IMAGE_1"
if [ $? -ne 0 ]; then
    echo "Docker tag failed for $OLD_IMAGE_1. Exiting..."
    exit 1
fi

# Push the first image to Docker Hub
echo "Pushing image $NEW_IMAGE_1 to Docker Hub..."
docker push "$NEW_IMAGE_1"
if [ $? -ne 0 ]; then
    echo "Docker push failed for $NEW_IMAGE_1. Exiting..."
    exit 1
fi

# Tag the second image with the new tag
echo "Tagging image $OLD_IMAGE_2 as $NEW_IMAGE_2..."
docker tag "$OLD_IMAGE_2" "$NEW_IMAGE_2"
if [ $? -ne 0 ]; then
    echo "Docker tag failed for $OLD_IMAGE_2. Exiting..."
    exit 1
fi

# Push the second image to Docker Hub
echo "Pushing image $NEW_IMAGE_2 to Docker Hub..."
docker push "$NEW_IMAGE_2"
if [ $? -ne 0 ]; then
    echo "Docker push failed for $NEW_IMAGE_2. Exiting..."
    exit 1
fi

echo "Push completed for both images!"

