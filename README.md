# Ecommerce System

## Table of Contents

- [Overview](#overview)
- [Installation and Set Up](#installation-and-set-up)
- [Usage](#usage)
- [Testing](#testing)
- [Code Formatting](#code-formatting)

## Overview

This is an Ecommerce system that allows users to browse, manage, and order products purchases
through a scalable, distributed architecture built with Spring Boot and Temporal.

Features: 
- Home: Welcome page for users.
- Products: Allows users to browse products, view inventory availability, and place orders.
  - View Products and Inventory: Displays all available products and their inventory information
  - Submit Order: Allows users to place an order by entering their email address. Triggers a
    Temporal workflow to orchestrate the order process. This creates a new user if one does not
    already exist. Updates inventory by increasing the quantityReserved. Creates the order record
    with a status of CREATED. After the workflow completes successfully, the user receives a
    confirmation email indicating the order was placed successfully.
- Admin: Allows a user to do any backend admin functionality.
  - Create Products: Allows the admin to create a new product. The admin passes in information about
    the product and inventory which then it calls the a temporal workflow to create the product. 
  - Update Products and Inventory: Allows the admin to update the product or inventory of the
    product. 
  - Delete Products: Allows the admin to delete any product. The admin clicks the delete button next
    to a product which then it calls the a temporal workflow to create the product. 
  - Process Orders: Allows the admin to manage and process customer orders. Administrators can
    review order details, update order statuses fro(such as PROCESSING, COMPLETED, or CANCELLED),
    and coordinate inventory updates as orders move through the fulfillment lifecycle. If the admin
    sets the status to COMPLETED it will update the inventory by setting the reservedQuantity - 1
    and the quantityAvailable + 1.  If the admin set the status to CANCELLED it will just set the
    reservedQuantity - 1.  
- Test: Has two buttons to test Temporal Workflows which calls test endpoints for each service.
- Analytics: Create an analytics page where a user can trigger various reports (ex: top 5 products).
  Also have this run automatically every night to sent a user the reports. 

Architecture Overview: 
- UI Service: Provides the React frontend interface for users and administrators. It allows users to
  browse products, place orders, manage inventory, and monitor workflow execution. The UI
  communicates with backend APIs and workflow endpoints to retrieve data and initiate distributed
  business operations.
- Worker Service: Hosts Temporal workflow and activity implementations responsible for orchestrating
  distributed business processes. It executes workflows for product creation, inventory management,
  order processing, order status updates, retries, compensation logic, and failure recovery. Workers
  can be horizontally scaled to support increased workload and long-running workflow execution.
- User Service: Manages user operations, including creating, retrieving, updating, and deleting user
  data.
- Order Service: Manages order operations, including creating, retrieving, updating, and deleting
  orders.
- Product Service: Manages product operations, including creating, retrieving, updating, and
  deleting product data.
- Inventory Service: Manages inventory operations, including inventory creation, retrieval, updates,
  reservation management, and stock tracking.

![alt text](docs/architecture-diagram.drawio.svg)

Tech Used: 
- React: React is used to build the UI and create reusable frontend components for managing
  products, inventory, orders, and workflow interactions. It provides a dynamic and responsive user
  experience through component-based architecture and client-side rendering.
- Spring Boot: Spring Boot is used to build all backend microservices. It provides REST APIs,
  dependency injection, configuration management, and production-ready capabilities for rapid
  development and deployment.
- Temporal: Temporal is used for workflow orchestration. It manages durable execution, retries,
  state persistence, and failure recovery for distributed processes, ensuring reliable coordination
  between services in the system. 

## Installation and Set Up

1. Prerequisites: Before you begin, ensure you have the following installed on your machine:
    - [Visual Studio Code](https://code.visualstudio.com/download): This app is set up for VS Code,
      but you may use a different editor if you would like.
    - [Java 17](https://www.oracle.com/java/technologies/downloads/#java17): To verify if Java is
      installed, run the following command and you should see java 17 returned.
        ```bash
        java --version
        ```
    - [Node.js](https://nodejs.org/en/download): To verify node is installed, run the following
      command and you should see the node version you installed output. On a mac you can use brew to
      install node too: `brew install node`.
        ```bash
        node -v
        npm -v
        ```
    - [Git](https://git-scm.com/downloads/): To check if Git is installed, run the command below. If
      it is installed correctly you'll see its version number. 
        ```bash
        git --version
        ```
    - [Docker Desktop](https://www.docker.com/get-started/): To check if Docker is installed, run
      the command below. If it is installed correctly you'll see its version number.
        ```bash
        docker version
        ```
    - [Postman](https://www.postman.com/downloads/): Verify Postman was installed on your computer.
    - [Temporal](https://learn.temporal.io/getting_started/typescript/dev_environment/#set-up-a-local-temporal-service-for-development-with-temporal-cli):
      Install temporal using the cli 

2. Clone Repository: 
    - Open a terminal or command prompt and run the following command to clone the repository:
        ```bash
        git clone https://github.com/rchapman001/ecommerce-system.git
        ```
      
3. Setup VS Code: 
    - Install the VS Code Extensions: Navigate to extensions.json file in .vscode folder and install
      all extensions listed in the file.
    - Set Java home path: Navigate to settings.json file in .vscode folder and set the
      `"java.jdt.ls.java.home"` setting to the correct path of your JDK installation.

4. Configure `host.docker.internal`: This configuration allows your frontend application to access
   backend services running in Docker containers using the URL. `http://host.docker.internal:<port>`
    - For Mac and Windows:
        - Open a terminal and edit the `/etc/hosts` file using a text editor with sudo access, such
          as nano or vim:
            ```bash
            sudo nano /etc/hosts
            ```
        - Add the following line to the file:
            ```txt
            127.0.0.1 host.docker.internal
            ```
        - Save and close the file.

## Usage

- How to startup the system:
  1. Open Docker Desktop: To run the app you need to have docker desktop open.
  2. Start Postgres DB: With the Docker extension installed, right click the docker-compose.yml file
     and select 'Compose Up'. This will start the database including creating the schema.
     Alternatively, you can run the following command in the directory where the docker-compose.yml
     file is located: `docker compose up`.
  3. Start Temporal Server Locally: Open a new terminal and run: `temporal server start-dev`.
  4. Start Spring Boot Services: In the Spring Boot Dashboard, run the services you are developing
     and testing. Alternatively, you can navigate to each app individually in a separate terminal
     and run the following command: `./mvnw spring-boot:run`. Make sure to run the worker-service
     not in debug mode, otherwise it won't work with temporal for some reason.
  5. Start ecommerce-ui react app: Run the following command in the ecommerce-ui folder: `npm run
     dev`.

- How to run a temporal workflow:
  1. Open http://localhost:8233
  2. Click: Workflows -> Start Workflows
  3. Enter in the following information then click Start Workflow:
    - Workflow ID: Click Random UUID
    - Task Queue: Name of Task Queue from workflow implementation class
    - Workflow Type: Name of workflow interface

- How to shutdown the system:
  1. In the Spring Boot Dashboard, click the stop button to stop all apps. Alternatively, you can
     type Ctrl + C to kill each app in their respective terminal.
  2. To shutdown temporal, open the terminal you started temporal server, type Ctrl + C.
  3. To stop the Postgres DB: With the Docker extension installed, right click the
     docker-compose.yml file and select 'Compose Down'. Alternatively, you can run the following
     command in the directory where the docker-compose.yml file is located: `docker compose down -v`

## Testing

TODO

## Code Formatting

1. [spotless](https://github.com/diffplug/spotless) is used to format java code. To setup Spotless
   for a Spring Boot app, just add the plugin to the pom.xml. For example,
    ```xml
        <!-- Spotless plugin configuration -->
        <plugin>
          <groupId>com.diffplug.spotless</groupId>
          <artifactId>spotless-maven-plugin</artifactId>
          <configuration>
            <java>
              <includes>
                <include>src/main/java/**/*.java</include> <!-- Check application code -->
                <include>src/test/java/**/*.java</include> <!-- Check application tests code -->
              </includes>
              <googleJavaFormat>
                <style>GOOGLE</style>
              </googleJavaFormat>
            </java>
          </configuration>
        </plugin>
    ```
  - Once the plugin is added to the pom.xml, you can use the following command to check the
    formatting for each service and stop if any violations are found.
    ```bash
    (cd inventory-service && ./mvnw spotless:check) && (cd order-service && ./mvnw spotless:check) && (cd product-service && ./mvnw spotless:check) && (cd user-service && ./mvnw spotless:check) && (cd worker-service && ./mvnw spotless:check)
    ```
  - Then uses this command to apply the formatting to each app.
    ```bash
    (cd inventory-service && ./mvnw spotless:apply) && (cd order-service && ./mvnw spotless:apply) && (cd product-service && ./mvnw spotless:apply) && (cd user-service && ./mvnw spotless:apply) && (cd worker-service && ./mvnw spotless:apply)
    ```
  - If you want to automatically format your Java code using Spotless before committing, follow
    these steps.
    - Set the custom Git hooks path: Run the following command to configure Git to use the .githooks
      directory for hooks.
      ```bash
      git config core.hooksPath .githooks
      ```
    - Make the pre-commit script executable: Ensure that the pre-commit hook script in the .githooks
      directory is executable.
      ```bash
      chmod +x .githooks/pre-commit
      ```

2. [rewrap](https://github.com/staabm/vscode-rewrap) is a Visual Studio Code extension that wraps
   comments and other text to a specified line length. To use rewrap in VS Code:
  - Install the rewrap extension from the Visual Studio Code Marketplace.
  - Set the desired line length for wrapping comments in your `settings.json`:
    ```json
    {
        "editor.wordWrap": "wordWrapColumn", // Rewrap setting
        "editor.wordWrapColumn": 88, // Rewrap setting
        "editor.rulers": [88], // Rewrap setting
    }
    ```
  - With your cursor in the comment block, press the appropriate keyboard shortcut (usually Alt+Q or
    Alt+Shift+Q) to rewrap the comment to the specified line length.