# Online Library Application

Welcome to the Online Library Application Repository! This application is designed to facilitate online library management with a range of features and components. Below is an overview of what's included:

## Features and Implementation Highlights

1. **Persistence Layer:** Utilized to manage data storage in the application.

2. **Service Layer:** Business logic and operations are encapsulated in service components.

3. **Controller Layer:** Responsible for handling incoming HTTP requests and managing responses.

4. **DTOs (Data Transfer Objects):** REQUEST and RESPONSE DTOs are used to structure data communication.

5. **Object Mapping:** ENTITY to DTO and vice versa transformations are managed using the MAPSTRUCT library.

6. **Exception Handling:** Implemented mechanisms to handle exceptions gracefully and provide meaningful error responses.

7. **Validation:** Data validation is applied to ensure data integrity and consistency.

8. **User Registration Annotation:** Custom annotation for checking user existence during registration.

9. **Pagination and Sorting:** Implemented features to efficiently manage large datasets.

10. **Database Normalization:** Did database normalization for importing user data.

11. **CSV Data Import:** Imported user data from [CSV file](https://github.com/ErikMargaryan/online-library/blob/master/data-tVJ5E-PoXliPdkzyzbeE0.csv) into different database tables using the OpenCSV library.

12. **Book Data Upload:** Uploaded [book data](https://fakerapi.it/api/v1/books?_quantity=100&_locale=en_US) into the database to enrich the library collection.

13. **Security Mechanism:** Implemented comprehensive authorization and authentication with three user roles.

14. **Open API Specification:** Documented APIs using Open API Specification for easy integration.

15. **Unit Testing:** Comprehensive unit tests for Repository and Service Layers to ensure functionality.

16. **Public Page:** Application includes a single public page http://localhost:8080/public.

## Additional Resources

- Check out the [Database UML](https://github.com/ErikMargaryan/online-library/blob/master/online-library-diagram.uml) or [Database PNG](https://github.com/ErikMargaryan/online-library/blob/master/online-library-diagram.png) for a visual representation of the database schema.

- View the [CSV File](https://github.com/ErikMargaryan/online-library/blob/master/data-tVJ5E-PoXliPdkzyzbeE0.csv) used for data importation.

## API Documentation

Explore the API endpoints and test them using the provided tools:

- **Swagger Documentation:** Access the detailed API documentation using Swagger UI. Visit [Swagger Documentation](http://localhost:8080/swagger-ui/index.html) to get started.

## API Testing

Test the API endpoints using Postman. You can import the Postman collection to easily set up and execute requests:

- **Postman Collection:** Download and import the [Postman Collection](https://erikmargaryan.postman.co/workspace/New-Team-Workspace~b9b7d285-db38-4fc6-abb8-14adaa0ce421/collection/14751799-768c0a90-5c1a-4f7d-8a2e-2b575ebdae7e?action=share&creator=14751799) to quickly test API endpoints.

## Getting Started

To quickly set up and run the Online Library Application, follow these steps:

1. **Clone the Repository:** Start by cloning the repository to your local machine using the following command:
   - **git** clone https://github.com/ErikMargaryan/online-library.git
2. **Database Setup:** The application requires a PostgreSQL database. If you don't have PostgreSQL installed, consider using Docker to set up a container:
   - **docker** run --name some-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=library -d -p 5432:5432 postgres
4. **Run the Application:** Navigate to the project root directory and run the application using Maven:
   - **mvn** spring-boot:run
   - This will start the application on `http://localhost:8080`.

## Initial Users

After running the application, the following initial users with different roles will be loaded into the database:

- **USER Role:**
    - Email: user@example.com
    - Password: password123

- **ADMIN Role:**
    - Email: admin@example.com
    - Password: adminpass

- **SUPER_ADMIN Role:**
    - Email: erickmargarian@gmail.com
    - Password: superpass
   
Feel free to explore the repository, contribute, and enhance the application further!
