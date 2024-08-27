# ElectroVerse - Electronic Store Management System (Backend)

## Table of Contents

1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Installation](#installation)

---
## Project Overview

ElectroVerse is a system built on microservices that supports electronic component retailers by leveraging technology for management and operations.

## Software Architecture

![image](https://github.com/user-attachments/assets/fa08c90a-908f-4363-8c45-22f84bc606da)

## Main Features
  ### Authentication
  
  - **User Registration**: Users can create an account using their email and password.
  - **User Login**: Users can log in to their account using their email and password.
  - **Password Reset**: Users can reset their password if they forget it.
  - **JWT Authentication**: Secure authentication using JSON Web Tokens.
  
  ### Product Management
  
  - **CRUD Operations for Products**: Administrators can create, read, update, and delete products.
  - **Category Management**: Products can be categorized for better organization and searchability.
  
  ### Customer Management
  - **CRUD Operations for Customers**: Administrators can create, read, update, and delete products.
  - **View Customer's Order History:**  Administrators can view a comprehensive list of all orders placed by a specific customer. This includes details such as order dates, statuses, and total amounts.
  
  ### Order Management
  
  - **Order Creation and Tracking**: Users can place orders and track their status from processing to delivery.
  
  ### Statistics
   - **Revenue Tracking**:
       + Daily/Weekly/Monthly Revenue: Display total revenue on a daily, weekly, and monthly basis. This helps administrators easily monitor revenue fluctuations over time.
       + Revenue by Product: Provides statistics on revenue distribution by product, allowing identification of high and low-performing products.
   - **Expense Tracking:** Daily/Weekly/Monthly Expenses: Display total expenses on a daily, weekly, and monthly basis. Helps in continuous expense tracking and budget control.

  ### Notification
  
  ## Installation
  
  To set up the project on Docker, follow these steps: Make sure Docker is installed and running properly on your computer

1. **Clone the repository**:
   
     git clone https://github.com/SONLE03/Project01
     
     cd ClothingStoreManagement_BE

2. **Start the server:**
   
   docker-compose up -d
