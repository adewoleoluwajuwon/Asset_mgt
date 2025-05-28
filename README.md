# 🏢 Asset Management System

A Spring Boot + Thymeleaf web application for managing company assets, tracking their statuses, and monitoring their storage in warehouses. Built with MySQL as the backend database, this system supports full CRUD operations, search, and role-based access control.

## 🚀 Features

- ✅ CRUD operations for:
  - Assets
  - Warehouses
  - Statuses
- 🔍 Search functionality for all entities
- 🧮 Auto-calculated total cost of assets (`unit cost × quantity`)
- 🔐 Role-based access:
  - Admins can add/delete users and assets
  - Regular users have restricted access
- 🖥️ Web interface built with Thymeleaf
- Responsive and intuitive UI

## 🧰 Tech Stack

- **Backend**: Spring Boot (Java)
- **Frontend**: Thymeleaf, HTML/CSS
- **Database**: MySQL
- **ORM**: Spring Data JPA (Hibernate)
- **Build Tool**: Maven
- **IDE**: Eclips
- **Database GUI**: MySQL Workbench

## ⚙️ Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- MySQL Server
- MySQL Workbench (optional)

### Clone the Repository

```bash
git clone https://github.com/yourusername/Asset_mgt.git
cd Asset_mgt
