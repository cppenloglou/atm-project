#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo "🛑 Stopping Minikube..."

# Check if Minikube is running
if minikube status | grep -q "Running"; then
    # Stop Minikube
    if minikube stop; then
        echo -e "${GREEN}✅ Minikube stopped successfully!${NC}"
    else
        echo -e "${RED}❌ Failed to stop Minikube${NC}"
        exit 1
    fi
else
    echo -e "${RED}Minikube is not running.${NC}"
fi

# Cleanup Minikube cluster (optional)
echo "🧹 Cleaning up Minikube cluster..."
if minikube delete; then
    echo -e "${GREEN}✅ Minikube cluster deleted successfully!${NC}"
else
    echo -e "${RED}❌ Failed to delete Minikube cluster${NC}"
    exit 1
fi

echo -e "\n🏁 Minikube stop script completed."
