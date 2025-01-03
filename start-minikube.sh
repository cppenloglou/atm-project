#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo "🚀 Starting Minikube..."

# Check if Minikube is already running
if minikube status | grep -q "Running"; then
    echo -e "${RED}Minikube is already running. Stopping current instance...${NC}"
    minikube stop
    sleep 5
fi

# Start Minikube with specified configuration
if minikube start --driver=docker --static-ip="192.168.49.2"; then
    echo -e "${GREEN}✅ Minikube started successfully!${NC}"
    
    # Display Minikube status
    echo -e "\n📊 Minikube Status:"
    minikube status
    
    # Display the IP
    echo -e "\n🌐 Minikube IP:"
    minikube ip
else
    echo -e "${RED}❌ Failed to start Minikube${NC}"
    exit 1
fi