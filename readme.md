# Document Management System 
# Backend - Spring Boot

This repository contains the backend implementation of a Document Management System. It provides a set of APIs for managing folders and documents, allowing users to organize and store their files efficiently.

## Features

- Create, retrieve, update, and delete folders
- Upload documents to specific folders
- Download documents from the system
- [PROGRESS] Folder and Document permission management
- [PROGRESS] Document Management React JS

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

---

# Front End - HTML

## Folder and Document Hierarchy

![Alt Text](Screenshot%202023-06-24%20224537.png)

This is a sample HTML document that demonstrates a folder and document hierarchy. It displays a file browser-like structure with folders and associated documents. You can also download the documents directly from the interface.

## Usage

1. Open the `index.html` file in a web browser.
2. The folder and document hierarchy will be rendered on the page.
3. Navigate through the folders to explore the subfolders and documents.
4. Click on the document links to download the respective documents.

## JSON Hierarchy

The folder and document hierarchy is populated using a sample JSON structure. You can replace this JSON structure with your own dynamic data from an API. The JSON structure should follow the same format and include the necessary fields: `folderId`, `name`, `path`, `folders`, and `documents`.

## File Structure

- `index.html`: The main HTML file that displays the folder and document hierarchy.
- `script.js`: The JavaScript code responsible for building the HTML structure based on the JSON hierarchy.

## Notes

- The HTML structure is built dynamically using JavaScript, so you can easily adapt it to integrate with your own application or API.
- Customize the JSON hierarchy or replace it with dynamic data from your backend API to reflect your actual folder and document structure.

---

Feel free to customize the document further to add any additional information or instructions specific to your project.

## Contributing

Contributions to the Document Management System backend are welcome! If you have any suggestions, bug reports, or feature requests, please open an issue on the GitHub repository.

If you'd like to contribute code to the project, you can fork the repository, make your changes, and submit a pull request.

## License

The Document Management System backend is open-source software licensed under the [MIT license](LICENSE).
