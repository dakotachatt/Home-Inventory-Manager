# Home-Inventory-Manager
Home inventory management system

Description and Functionality
This was the final project in the Web Application Development course in SAIT's Two Year Information Technology (Software Development) program.
The application utilizes an MVC architecture with a JSP based front-end, using Java Servlets for routing and business logic, and accesses a database 
utilizing MySQL and JPA.

Languages and frameworks used include: Java, JSP, JSTL, JPA, MySQL, Bootstrap, JavaMail

Features:
  Three types of user permissions
    Regular User: Can access inventory management screen (CRUD functionality), as well as update their account information after account is verified
    Company Admin: Can perform regular user functions as well as manage other users of their own company (CRUD functionality), cannot promote users to system 
    admin
    System admin: Can perform regular user functions as well as manage all users regardless of company, promote and demote users to any role, and manage
    item categories

  Account creation
    When regular user creates an account, an email is sent and the link must be clicked before the user can log in which will verify their account
    When a company admin creates a user account, the user is automatically added to the same company as the admin, and does not need to verify their account
    When a system admin creates a user account, the user is not associated with any company and does not need to verify their account
  
  Account Authentication
    All user credentials are authenticated to be able to login successfully, forgot password functionality is built in sending a reset link to user's email
    Filters are used to check for valid logins and user roles to ensure only users with appropriate access can access certain pages and functionality
  
  Inventory Management
    All user roles can perform CRUD operations on their own inventory
  
  User Management
    Company admins can only manage users of their own company, whereas System admins can manage all users
    
  Category Management
    Only system admins can update existing item category titles


Screenshots

Create Account Page
![CreateAccount](https://user-images.githubusercontent.com/41240205/187527149-266d8cb2-75fe-4bcb-a639-e6b5f03228ce.PNG)

Login/Landing Page
![Login](https://user-images.githubusercontent.com/41240205/187527196-acd6889b-9dc3-4639-a031-a4d93e6c6c5e.PNG)

Regular User Inventory Management Page
![RegUserInventory](https://user-images.githubusercontent.com/41240205/187527239-0aefde60-118e-4f72-96ca-209dd8354926.PNG)

Update Account Info Page
![UpdateAccount](https://user-images.githubusercontent.com/41240205/187527302-fe8a8354-badb-43f6-8fd1-b23ebd3f75f6.PNG)

Company Admin User Management Page
![CompanyAdminManageUsers](https://user-images.githubusercontent.com/41240205/187527321-fee644d0-43d7-458e-a010-8a0b7183a6c6.PNG)

System Admin User Management Page
![SysAdminManageUsers](https://user-images.githubusercontent.com/41240205/187527361-be95a81c-1ba1-43f0-ade4-1491107b7aab.PNG)

System Admin Category Management Page
![SysAdminManageCategories](https://user-images.githubusercontent.com/41240205/187527389-8ad44cb8-9afa-4bde-9014-e5a9a8ab7570.PNG)
