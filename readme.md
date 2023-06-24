# Document Management System Backend - Spring Boot

This repository contains the backend implementation of a Document Management System. It provides a set of APIs for managing folders and documents, allowing users to organize and store their files efficiently.

## Features

- Create, retrieve, update, and delete folders
- Upload documents to specific folders
- Download documents from the system
- [PROGRESS] Folder and Document permission management

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Gradle
- RESTful API

## Getting Started

To get started with the Document Management System backend, follow these steps:

1. Clone the repository:

```shell
git clone https://github.com/gitshishirkarki/spring-document-management-system.git
```

2. Navigate to the project directory:

```shell
cd spring-document-management-backend
```

3. Build the project:

```shell
gradle clean build  # For Gradle
```

4. Run the application:

```shell
gradle bootRun       # For Gradle
```

5. The application will start running on `http://localhost:8080`.

## API Documentation

The API documentation is available in Postman collection format. You can import the collection into Postman by clicking on the following link:

[Import Postman Collection](https://api.postman.com/collections/25891149-27b7516d-b2ca-4bc0-8616-9a3c981e1336?access_key=PMAT-01H3P1D98YEB8JY6922JP17HBR)

The collection includes all the endpoints and example requests for the Document Management System backend.

## Contributing

Contributions to the Document Management System backend are welcome! If you have any suggestions, bug reports, or feature requests, please open an issue on the GitHub repository.

If you'd like to contribute code to the project, you can fork the repository, make your changes, and submit a pull request.

## License

The Document Management System backend is open-source software licensed under the [MIT license](LICENSE).
