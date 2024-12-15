# ATM Service Application Suite

This repository, hosted at [ATM Project GitHub](https://github.com/cppenloglou/ATM-project.git), contains three interconnected applications designed for an ATM service system:

1. **ATM Microservice Backend Server (API)**
2. **ATM Frontend in Next.js (Under Construction)**

Each application serves a distinct role, providing a modular, scalable solution for the ATM service project.

### Setup
1. Clone the repository:
```bash
git clone https://github.com/cppenloglou/ATM-project.git
```
2. Start the MySQL Docker container:
```bash
docker-compose -f compose-remote.yaml up -d
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

## Development run



```bash
npm install
npm run dev