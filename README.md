ShopSphere — E-Commerce App
A full-stack e-commerce web application built with Spring MVC 6, Hibernate 6,Embedded Tomcat 10, and JSP. Layered architecture with POJO entities,session-scoped cart, transactional services, and a clean separation betweencontrollers, services, and repositories.

Tech Stack
Layer	Technology
Build	Maven 3
Java	17
Web	Spring MVC 6.1 (Jakarta namespace)
ORM	Hibernate 6.4 (JPA)
Server	Embedded Tomcat 10.1
Views	JSP + JSTL 3.0
Database	MySQL 8 (HikariCP connection pool)
Logging	SLF4J + Logback
Features
Authentication — Signup, login, logout, session-based auth
Product catalog — Browse, search, detail view with reviews
Reviews — Authenticated users can post (1 per product), delete own reviews
Cart — Session-scoped, add/update/remove/clear
Checkout — Convert cart into persisted order with price snapshotting
Order history — View past orders with line items; ownership-enforced
Error pages — Custom 404 / 500 / generic error views
Responsive — Mobile-friendly layout
Project Structure
src/main/java/com/example/ecom/├── Main.java                  # Embedded Tomcat bootstrap├── config/                    # Spring + Hibernate configuration│   ├── WebAppInitializer.java│   ├── RootConfig.java│   ├── WebConfig.java│   ├── DatabaseConfig.java│   ├── HibernateConfig.java│   ├── GlobalModelAttributes.java│   └── GlobalExceptionHandler.java├── controller/                # HTTP endpoints│   ├── HomeController.java│   ├── AuthController.java│   ├── ProductController.java│   ├── ReviewController.java│   ├── CartController.java│   ├── OrderController.java│   ├── LogoutController.java│   └── ErrorController.java├── interceptor/│   └── AuthInterceptor.java   # Routes protected by authentication├── model/                     # JPA entities (POJOs)│   ├── User.java│   ├── Product.java│   ├── Review.java│   ├── Order.java│   └── OrderItem.java├── repository/                # DAO layer (Hibernate Session)│   ├── GenericDao.java│   ├── UserDao.java│   ├── ProductDao.java│   ├── ReviewDao.java│   └── OrderDao.java├── service/                   # Business logic (@Transactional)│   ├── UserService.java│   ├── ProductService.java│   ├── ReviewService.java│   ├── CartService.java       # Session-scoped│   └── OrderService.java└── util/    └── SessionUtil.java       # Session attribute helpers
Prerequisites
JDK 17 or higher
Maven 3.6+
MySQL 8 (or MySQL via WAMP/XAMPP/MAMP)
Setup
1. Create the database
CREATE DATABASE ecommerce  CHARACTER SET utf8mb4  COLLATE utf8mb4_unicode_ci;
2. Configure DB credentials
Edit src/main/resources/application.properties:

db.url=jdbc:mysql://localhost:3306/ecommerce?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=truedb.username=rootdb.password=YOUR_PASSWORD_HEREdb.driver=com.mysql.cj.jdbc.Driver
3. Run the app
mvn clean compile exec:java
Visit http://localhost:8081/

4. Seed sample data
Once the app starts and Hibernate creates the schema, run the seed SQL:

mysql -u root -p ecommerce < sql/seed.sql
Or paste sql/seed.sql contents into phpMyAdmin's SQL tab.

Demo Accounts
Username	Password	Role
admin	admin123	ADMIN
alice	alice123	USER
bob	bob123	USER
charlie	charlie123	USER
URL Map
Path	Method	Description
/	GET	Homepage with featured products
/products	GET	Catalog listing (with ?q= search)
/products/{id}	GET	Product detail + reviews
/products/{id}/reviews	POST	Submit a review (auth required)
/cart	GET	View cart
/cart	POST	Add to cart
/cart/update	POST	Change line quantity
/cart/remove	POST	Remove a line
/cart/clear	POST	Empty the cart
/orders	GET	Order history (own orders)
/orders/{id}	GET	Order detail (own or admin)
/orders/checkout	GET	Checkout form
/orders/checkout	POST	Place order
/auth/login	GET	Login form
/auth/login	POST	Submit login
/auth/signup	GET	Signup form
/auth/signup	POST	Submit signup (auto-login)
/auth/logout	GET	Logout
Architecture
HTTP Request     ↓DispatcherServlet (Spring)     ↓AuthInterceptor (auth check)     ↓Controller (thin — request/response handling)     ↓Service (@Transactional — business logic)     ↓DAO (@Repository — Hibernate Session queries)     ↓MySQL
Key design decisions
Layered architecture — Controllers never touch DAOs directly. Services orchestrate.
Session-scoped cart — Cart state lives in CartService (one bean per HTTP session), persisted to DB only on checkout.
Price snapshotting — OrderItem.unitPrice captures product price at order time, so historical orders stay accurate even if the product price changes.
Soft-delete products — Product.active flag, never hard-delete (preserves order history integrity).
Ownership-enforced order views — Users can only see their own orders; admins can see any.
Post-Redirect-Get — All mutations use POST + redirect to prevent accidental re-submission on refresh.
Development Notes
The compiler uses -parameters flag (set in pom.xml) — required by Spring 6 for @RequestParam name resolution.
Hibernate's hbm2ddl.auto=update — schema is auto-created on startup. Change to validate or none for production.
Passwords are stored in plain text for demo. Use BCrypt in production (spring-security-crypto).
No CSRF protection — acceptable for demo; add Spring Security for production.
Resetting the Database
To wipe and reseed all data:

mysql -u root -p ecommerce < sql/reset.sqlmysql -u root -p ecommerce < sql/seed.sql
License
MIT — for educational purposes.