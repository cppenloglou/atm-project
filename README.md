# ATM Service Application Suite

This repository, hosted at [ATM Project GitHub](https://github.com/cppenloglou/ATM-project.git), contains three interconnected applications designed for an ATM service system:

1. **ATM Microservice Backend Server (API)**
2. **ATM Frontend in Vaadin**
3. **ATM Frontend in Next.js (Under Construction)**

Each application serves a distinct role, providing a modular, scalable solution for the ATM service project.

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

### Setup
1. Clone the repository:
```bash
git clone https://github.com/cppenloglou/ATM-project.git
```
2. Start the MySQL Docker container:
```bash
docker-compose up -d
```
3. Run the application:
```bash
./mvnw spring-boot:run
```

# API Documentation

API documentation is available at: `/api-docs` once the server is running.

## 2. ATM Frontend in Vaadin

The ATM Frontend in Vaadin serves as a responsive, user-friendly interface for ATM operations. Users can access and manage their accounts, view transaction history, and perform ATM operations like withdrawals and deposits.

### Key Features

- **Dashboard**: View user information and account balance.
- **Dynamic Views**: Withdraw and Deposit views share a common layout with respective functionality.
- **Authentication Check**: Only authorized users can access sensitive pages.

### Tech Stack

- Vaadin for UI components
- Java as the core language
- Spring Boot for backend integration

### Setup

1. Ensure the backend server is running.
2. Start the frontend application with:
```bash
./mvnw spring-boot:run
```
## 3. ATM Frontend in Next.js (Under Construction)

The Next.js Frontend is an alternative interface currently in development. This frontend will be a modern, responsive web application with interactive UI components provided by Shadcn UI.

## Planned Features

- **Responsive Design**: Optimized for various devices.
- **Component-Based Architecture**: Built with modular components using Shadcn UI.
- **Secure Authentication**: To integrate with backend security features.

## Tech Stack

- **Next.js** for frontend development
- **Shadcn UI** for UI components
- **React** for building interactive UIs

## Setup

Development for this frontend is ongoing. Once complete, it will support the following setup:

```bash
npm install
npm run dev

