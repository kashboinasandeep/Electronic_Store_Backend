# Electronic Store Backend

A Spring Boot–based backend for an e-commerce electronics store, providing secure REST APIs for user management, product catalog, cart, orders, and category management.

##  Features

- **User Authentication & Authorization** — JWT-based authentication with Google OAuth login support
- **Role-Based Access Control (RBAC)** — Secure endpoints for Admin and Customer roles
- **Product Management** — CRUD APIs for managing product catalog, including image upload
- **Category Management** — Create and manage product categories
- **Cart Management** — Add, view, and remove items from user carts
- **Order Management** — Place orders, update order status, and track order history
- **User Management** — Registration, profile updates, search, and profile image upload
- **Secure APIs** — Endpoint protection using Spring Security
- **Database Integration** — JPA/Hibernate with MySQL for data persistence
- **API Documentation** — Interactive API docs via Swagger/OpenAPI
- **Containerized** — Dockerized for easy deployment

##  Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Security:** Spring Security, JWT, Google OAuth
- **Database:** MySQL
- **ORM:** JPA/Hibernate
- **API Docs:** Swagger/OpenAPI
- **Deployment:** Docker, Render
- **Build Tool:** Maven

##  API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/generate-token` | Login and generate JWT access token |
| POST | `/auth/regenerate-token` | Refresh an expired access token |
| POST | `/auth/login-with-google` | Login/register via Google OAuth |

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users` | Get all users |
| POST | `/users` | Create a new user |
| GET | `/users/{userId}` | Get user by ID |
| PUT | `/users/{userId}` | Update user details |
| DELETE | `/users/{userId}` | Delete a user |
| GET | `/users/search/{keywords}` | Search users by keyword |
| GET | `/users/email/{email}` | Get user by email |
| POST | `/users/image/{userId}` | Upload user profile image |

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/products` | Get all products |
| POST | `/products` | Add a new product |
| GET | `/products/{productId}` | Get product by ID |
| PUT | `/products/{productId}` | Update product details |
| DELETE | `/products/{productId}` | Delete a product |
| GET | `/products/live` | Get live/active products |
| GET | `/products/search/{query}` | Search products |
| POST | `/products/image/{productId}` | Upload product image |

### Categories
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/categories` | Get all categories |
| POST | `/categories` | Create a new category |
| GET | `/categories/{categoryId}` | Get category by ID |
| PUT | `/categories/{categoryId}` | Update category |
| DELETE | `/categories/{categoryId}` | Delete category |
| GET | `/categories/{categoryId}/products` | Get products in a category |
| POST | `/categories/{categoryId}/products` | Add product to category |

### Cart
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/carts/{userId}` | Get user's cart |
| POST | `/carts/{userId}` | Add item to cart |
| DELETE | `/carts/{userId}` | Clear cart |
| DELETE | `/carts/{userId}/items/{itemId}` | Remove item from cart |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/orders` | Get all orders |
| POST | `/orders` | Place a new order |
| PUT | `/orders/{orderId}` | Update order status |
| DELETE | `/orders/{orderId}` | Cancel/delete order |
| GET | `/orders/users/{userId}` | Get orders by user |

## Live Demo

Swagger API Documentation: https://electronic-store-backend-pgl0.onrender.com/swagger-ui/index.html

> Note: hosted on a free tier — first load may take up to a minute to spin up.

 ## 📄 Documentation

Detailed API documentation: [Electronics-Store-API-Documentation.pdf](./Electronics-Store-API-Documentation.pdf)

##  Author

**Sandeep Kashaboina**
- LinkedIn: [linkedin.com/in/sandeep-kashaboina](https://www.linkedin.com/in/sandeep-kashaboina)
- Email: kashaboinasandeep789@gmail.com
