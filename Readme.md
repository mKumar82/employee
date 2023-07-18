Project Name   :  Employee API
==============================

API is hosted on Render

url: https://employee-api-hdyy.onrender.com 

Description
-----------
This project is a Spring Boot API for managing employee data.

Requirements
------------
- Java 11 or higher
- Apache Maven 3.6.3 or higher
- MongoDB 4.4 or higher
- IDE : Intellij or SpringToolSuite or Eclipse

Setup
-----
1. Clone the repository:
    
    git clone https://github.com/mKumar82/employee.git


2. Navigate to the project directory:

    cd employee


3. Start the application:

    mvn Spring-boot:run

    if you have Docker then run command: docker-compose up



The application will start running on http://localhost:8081.

Database Configuration
----------------------
This project uses MongoDB as the database. Database Configuration is already done. So no need to change.
If you want to use your database then update the application.yml file

URLs
----
- Local: http://localhost:8081

API Documentation
-----------------
1. Add Employee
- Endpoint: POST /employees
- Description: Adds a new employee to the database.
- Input JSON structure:
  ```
  {
    "employeeName": "John Doe",
    "phoneNumber": "1234567890",
    "email": "john.doe@example.com",
    "reportsTo": "manager-id",
    "profileImage": "https://example.com/profile-image.jpg"
  }
  ```

2. Get All Employees
- Endpoint: GET /employees
- Description: Retrieves all employees from the database.

3. Delete Employee
- Endpoint: DELETE /employees/{id}
- Description: Deletes an employee with the specified ID from the database.

4. Update Employee
- Endpoint: PUT /employees/{id}
- Description: Updates the details of an employee with the specified ID in the database.
- Input JSON structure:
  ```
  {
    "employeeName": "John Doe",
    "phoneNumber": "1234567890",
    "email": "john.doe@example.com",
    "reportsTo": "manager-id",
    "profileImage": "https://example.com/profile-image.jpg"
  }
  ```

5. Get Nth Level Manager
- Endpoint: GET /employees/{employeeId}/managers/{level}
- Description: Retrieves the nth level manager of an employee.
- Input: Replace `{employeeId}` with the employee ID and `{level}` with the desired level.


