# Antworld

## Overview
Antworld is an educational project designed to help students develop autonomous code. Originally implemented in Java, Antworld is now being ported to Golang, with additional features to support client-server communication. The project simulates a world consisting of land, water, food, and ant nests, where students can implement autonomous behaviors for their ants.

## Objectives
- Port the original Java client-side code to Golang.
- Develop a server to manage the Antworld environment.
- Implement a messaging protocol similar to Protocol Buffers for communication between the client and server.
- Provide a framework for students to develop and test autonomous ant behavior.

## Project Structure
### 1. Client
The client will simulate ants exploring the world, gathering resources, and interacting with their environment. It will send and receive messages using the defined messaging protocol.

### 2. Server
The server will maintain the state of the world, including:
- Terrain (land, water, food, nests)
- Ant positions and statuses
- Game rules and mechanics
It will process messages from clients and update the world accordingly.

### 3. Messaging Protocol
To facilitate efficient communication, we will develop a custom messaging protocol that:
- Encodes and decodes structured messages
- Supports essential operations such as movement, resource collection, and interaction with other ants
- Ensures efficient data transmission between client and server

## Development Plan
1. **Port existing Java client code to Golang**
2. **Design and implement the server**
3. **Define and implement the messaging protocol**
4. **Test communication between client and server**
5. **Develop and refine autonomous agent behaviors**

## Getting Started
### Prerequisites
- Golang (latest version)
- Basic knowledge of networking and protocol design
- Understanding of autonomous agent programming concepts

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/antworld.git
   cd antworld
   ```
2. Install dependencies:
   ```sh
   go mod tidy
   ```
3. Run the server:
   ```sh
   go run server/main.go
   ```
4. Run the client:
   ```sh
   go run client/main.go
   ```

## Contribution
We welcome contributions! If you’d like to help, please:
- Submit bug reports and feature requests via GitHub issues
- Follow our coding guidelines when submitting pull requests
- Participate in discussions about improvements and new features

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contact
For questions or collaboration inquiries, please reach out to the project maintainers or submit an issue on GitHub.

Happy coding!

