# ATM Service Application Suite

This repository, hosted at [ATM Project GitHub](https://github.com/cppenloglou/ATM-project.git), contains three interconnected applications designed for an ATM service system:

1. **ATM Microservice Backend Server (API)**
2. **ATM Frontend in Next.js (Under Construction)**

Each application serves a distinct role, providing a modular, scalable solution for the ATM service project.

### Setup
#### 1. Clone the repository:
```bash
git clone https://github.com/cppenloglou/ATM-project.git
cd ATM-project
```

#### 2. Set Up and Start Minikube
Before proceeding, ensure you have installed Minikube and kubectl correctly.

1. Grant execution permissions to the start-minikube.sh script:
```bash
chmod +x start-minikube.sh
```

2. Start Minikube by running:
``` bash
./start-minikube.sh
```

This script will:

<li>Check if Minikube is already running and stop the instance if necessary.
<li>Start a new Minikube instance with the required configuration.

#### 3. Deploy Kubernetes Resources
Once Minikube is running, deploy the necessary Kubernetes resources by executing:

``` bash
kubectl apply -f kubernetes/.
```

#### 4. Stop Minikube (When Needed)
To stop and clean up Minikube, use the stop-minikube.sh script:

1. Grant execution permissions to the stop-minikube.sh script:
``` bash
chmod +x stop-minikube.sh
```
2. Stop Minikube by running:
``` bash
./stop-minikube.sh
```
This script will:

<li>Stop the running Minikube instance if active.
<li>Optionally delete the Minikube cluster.

#### 5. Use Dashboard
To see additional informations about your clusters you can use the minikube dashboard
``` bash
minikube dashboard
```

#### 6. Frontend
To see and interact with frontend you can go to your browser to your minikube ip for example mines is 192.168.49.2 and port 30000
``` bash
192.168.49.2:30000
```

---



## 1. ATM Microservice Backend Server (API)

The **ATM Backend Service** is a Spring Boot application that provides RESTful API endpoints to manage accounts, cards, transactions, and user authentication. It handles core business logic, data persistence, and authentication, designed with a microservice architecture to ensure scalability and robustness.

### Key Features
- **User & Account Management:** CRUD operations for managing users and accounts.
- **Transaction Management:** Supports withdrawal, deposit, and transaction history for each account.
- **Authentication:** JWT-based authentication to secure endpoints.
- **Dockerized MySQL Integration:** Database persistence using MySQL within a Docker container.

### Tech Stack
- **Java 21**, **Spring Boot 3.3.4**
- **MySQL** (via Docker)
- **JWT** for API Security

# API Documentation

API documentation is available at: `/api-docs` once the server is running.

## 2. ATM Frontend in Next.js

The Next.js Frontend is an alternative interface currently in development. This frontend will be a modern, responsive web application with interactive UI components provided by Shadcn UI.

## Planned Features

- **Responsive Design**: Optimized for various devices.
- **Component-Based Architecture**: Built with modular components using Shadcn UI.
- **Secure Authentication**: To integrate with backend security features.

## Tech Stack

- **Next.js** for frontend development
- **Shadcn UI** for UI components
- **React** for building interactive UIs

---
---
---

# ATM Project - Developer Specifications

This document outlines the development environment and tools required to build and run the ATM Project.

## Prerequisites

Ensure the following software versions are installed before starting the project:

### 1. **Java Development Kit (JDK)**
- **Version**: OpenJDK 21.0.5 (Amazon Corretto)
- **Installation**: 
  - You can install JDK using package managers like `apt` (for Ubuntu/Linux) or `brew` (for macOS).
  - Verify the version using the following command:
    ```bash
    java --version
    ```

    Example output:
    ```
    openjdk 21.0.5 2024-10-15 LTS
    OpenJDK Runtime Environment Corretto-21.0.5.11.1 (build 21.0.5+11-LTS)
    OpenJDK 64-Bit Server VM Corretto-21.0.5.11.1 (build 21.0.5+11-LTS, mixed mode, sharing)
    ```

### 2. **Apache Maven**
- **Version**: 3.8.7
- **Installation**: 
  - Install Maven via package managers or download it directly from the official website.
  - Verify the installation using:
    ```bash
    mvn --version
    ```

    Example output:
    ```
    Apache Maven 3.8.7
    Maven home: /usr/share/maven
    Java version: 21.0.5, vendor: Amazon.com Inc., runtime: /home/<username>/.jdks/corretto-21.0.5
    Default locale: en_US, platform encoding: UTF-8
    OS name: "linux", version: "6.8.0-51-generic", arch: "amd64", family: "unix"
    ```

### 3. **Node.js**
- **Version**: v23.0.0
- **Installation**: 
  - Install Node.js using the Node Version Manager (NVM) or through your package manager.
  - Verify the installation with:
    ```bash
    node --version
    ```

    Example output:
    ```
    v23.0.0
    ```

### 4. **Node Package Manager (NPM)**
- **Version**: 10.9.0
- **Installation**: 
  - Ensure that NPM is installed with Node.js.
  - Verify the installation with:
    ```bash
    npm --version
    ```

    Example output:
    ```
    10.9.0
    ```

## Development build
Build each project an push the new images to my public repository.\
For you i think the best is to configure your own names for images and your \
registry. So you need your own push-to-dockerhub.sh file. If you dont this just \
delete the section for push in pom.xml of frontend in the last plugin.
``` bash
mvn clean install
```


## Further Information

If you have any questions, suggestions, or need additional information, feel free to reach out. You can contact me at:

📧 **xrisostomospenloglou13@gmail.com**\
My academic email at University of Macedonia dep. of Applied informatics.\
📧 **ics22116@uom.edu.gr**

I look forward to hearing from you!
