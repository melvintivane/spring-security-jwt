# SPRING SECURITY 6

## Description
This project is a simplified version of Twitter, designed to demonstrate the integration of Spring Security. It includes basic functionality to showcase secure authentication and authorization mechanisms.

## Technologies
- Spring Boot with JAVA 21
- MySQL (configured via Docker)
- Maven, Docker


## Requirements
- JAVA 21
- Docker and Docker Compose
- OpenSSL
- Recommended IDE (IntelliJ)


## Getting Started
### 1. Clone the project

To start, clone the repository to your local environment:
```bash
git clone https://github.com/melvintivane/spring-security-jwt.git
```

### 2. Navigate to the Project Directory

Change to the project directory:
```bash
cd /spring-security-jwt
```

### 3. Set Up Docker Containers

Build and run the Docker containers for the application and database:
```bash
cd /docker

docker-compose up
```

### 6. Key Generation Instructions
Prerequisites

Ensure you have OpenSSL installed. Verify the installation with:

```bash
openssl version
```

Step 1: Generate a Private Key

Run the following command to generate a private key:

```bash
openssl genrsa -out private.key 2048
```

Step 2: Derive the Public Key

Use the private key to derive the public key:

```bash
openssl rsa -in private.key -pubout -out public.pub
```

Step 3: Save Keys

> Place the generated private.key and public.pub files in the src/main/resources directory.



### 7.Start the Application

Run the application from IntelliJ or using the following Maven command:

```bash
mvn spring-boot:run
```

## Contribution Guidelines
Feel free to contribute by submitting pull requests or reporting issues in the repository.

## Contact

For further assistance, contact the maintainer at [melvinshuster47@gmail.com]().
