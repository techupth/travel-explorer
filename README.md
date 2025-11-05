# Travel Explorer API

A Spring Boot REST API for managing travel trips and user authentication with JWT-based security.

## 🚀 Quick Start

### Run the Application

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## 📋 API Overview

### **Public APIs** (No Authentication Required)

| Method | Endpoint                     | Description             |
| ------ | ---------------------------- | ----------------------- |
| POST   | `/api/auth/register`         | Register new user       |
| POST   | `/api/auth/login`            | Login and get JWT token |
| GET    | `/api/trips`                 | Get all trips           |
| GET    | `/api/trips?query={keyword}` | Search trips by keyword |
| GET    | `/api/trips/{id}`            | Get trip details by ID  |

### **Protected APIs** (Requires JWT Token)

| Method | Endpoint            | Description                  |
| ------ | ------------------- | ---------------------------- |
| GET    | `/api/auth/me`      | Get current user info        |
| GET    | `/api/trips/mine`   | Get my trips                 |
| POST   | `/api/trips`        | Create new trip              |
| PUT    | `/api/trips/{id}`   | Update trip (partial update) |
| DELETE | `/api/trips/{id}`   | Delete trip                  |
| POST   | `/api/files/upload` | Upload image file            |

---

## 📚 API Documentation

### 🔓 Authentication APIs

#### 1. Register User

Register a new user account.

**Endpoint:** `POST /api/auth/register`

**Request Body:**

```json
{
  "email": "user@example.com",
  "password": "password123",
  "displayName": "John Doe"
}
```

**Response:** `200 OK`

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "displayName": "John Doe"
  }
}
```

**Example:**

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "test1234",
    "displayName": "Test User"
  }'
```

---

#### 2. Login

Login with email and password to get JWT access token.

**Endpoint:** `POST /api/auth/login`

**Request Body:**

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:** `200 OK`

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "displayName": "John Doe"
  }
}
```

**Example:**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "test1234"
  }'
```

---

#### 3. Get Current User

Get current authenticated user information.

**Endpoint:** `GET /api/auth/me`

**Headers:**

```
Authorization: Bearer <your-access-token>
```

**Response:** `200 OK`

```json
{
  "id": 1,
  "email": "user@example.com",
  "displayName": "John Doe",
  "createdAt": "2025-11-05T10:00:00+07:00"
}
```

**Example:**

```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

### 🌍 Trip APIs (Public)

#### 4. Get All Trips

Get list of all trips, ordered by creation date (newest first).

**Endpoint:** `GET /api/trips`

**Response:** `200 OK`

```json
[
  {
    "id": 1,
    "title": "คู่มือเที่ยวเกาะช้าง",
    "description": "วันว่างนี้ไปเที่ยวเกาะช้างกัน...",
    "photos": [
      "https://example.com/photo1.jpg",
      "https://example.com/photo2.jpg"
    ],
    "tags": ["เกาะ", "ทะเล", "ตราด"],
    "latitude": 12.048,
    "longitude": 102.3225,
    "authorId": 1,
    "authorDisplayName": "John Doe",
    "createdAt": "2025-11-05T10:30:00+07:00",
    "updatedAt": "2025-11-05T10:30:00+07:00"
  }
]
```

**Example:**

```bash
curl -X GET http://localhost:8080/api/trips
```

---

#### 5. Search Trips

Search trips by keyword (searches in title, description, and tags).

**Endpoint:** `GET /api/trips?query={keyword}`

**Query Parameters:**

- `query` (string, optional): Search keyword

**Response:** `200 OK` - Same format as Get All Trips

**Examples:**

```bash
# Search by title
curl -X GET "http://localhost:8080/api/trips?query=เกาะช้าง"

# Search by tag
curl -X GET "http://localhost:8080/api/trips?query=ทะเล"

# Search by description
curl -X GET "http://localhost:8080/api/trips?query=ธรรมชาติ"
```

---

#### 6. Get Trip by ID

Get detailed information of a specific trip.

**Endpoint:** `GET /api/trips/{id}`

**Path Parameters:**

- `id` (long): Trip ID

**Response:** `200 OK`

```json
{
  "id": 1,
  "title": "คู่มือเที่ยวเกาะช้าง",
  "description": "วันว่างนี้ไปเที่ยวเกาะช้างกัน...",
  "photos": [
    "https://example.com/photo1.jpg",
    "https://example.com/photo2.jpg"
  ],
  "tags": ["เกาะ", "ทะเล", "ตราด"],
  "latitude": 12.048,
  "longitude": 102.3225,
  "authorId": 1,
  "authorDisplayName": "John Doe",
  "createdAt": "2025-11-05T10:30:00+07:00",
  "updatedAt": "2025-11-05T10:30:00+07:00"
}
```

**Example:**

```bash
curl -X GET http://localhost:8080/api/trips/1
```

---

### 🔐 Trip Management APIs (Protected)

All protected endpoints require JWT token in the Authorization header:

```
Authorization: Bearer <your-access-token>
```

---

#### 7. Get My Trips

Get list of trips created by the authenticated user.

**Endpoint:** `GET /api/trips/mine`

**Headers:**

```
Authorization: Bearer <your-access-token>
```

**Response:** `200 OK` - Same format as Get All Trips

**Example:**

```bash
curl -X GET http://localhost:8080/api/trips/mine \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

#### 8. Create Trip

Create a new trip. The authenticated user will be set as the author automatically.

**Endpoint:** `POST /api/trips`

**Headers:**

```
Authorization: Bearer <your-access-token>
Content-Type: application/json
```

**Request Body:**

```json
{
  "title": "เที่ยวเกาะช้าง 3 วาน 2 คืน",
  "description": "ทริปสุดมันส์ กับการเที่ยวเกาะช้าง...",
  "photos": [
    "https://example.com/photo1.jpg",
    "https://example.com/photo2.jpg",
    "https://example.com/photo3.jpg"
  ],
  "tags": ["เกาะ", "ทะเล", "ตราด", "ธรรมชาติ"],
  "latitude": 12.048,
  "longitude": 102.3225
}
```

**Field Requirements:**

- `title` (string, required): Trip title
- `description` (string, optional): Trip description
- `photos` (array, optional): Array of photo URLs
- `tags` (array, optional): Array of tags
- `latitude` (double, optional): Latitude coordinate
- `longitude` (double, optional): Longitude coordinate

**Response:** `200 OK` - Returns created trip object

**Example:**

```bash
curl -X POST http://localhost:8080/api/trips \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "title": "ทดสอบสร้างทริป",
    "description": "ทริปทดสอบของฉัน",
    "photos": ["https://example.com/photo1.jpg"],
    "tags": ["ทดสอบ"],
    "latitude": 13.7563,
    "longitude": 100.5018
  }'
```

---

#### 9. Update Trip (Partial Update)

Update trip information. Only the authenticated user who created the trip can update it.

**Endpoint:** `PUT /api/trips/{id}`

**Path Parameters:**

- `id` (long): Trip ID

**Headers:**

```
Authorization: Bearer <your-access-token>
Content-Type: application/json
```

**Request Body (Partial Update):**

You can send only the fields you want to update. Fields not included will remain unchanged.

**Example 1: Update only title and description**

```json
{
  "title": "เที่ยวเกาะช้าง - Updated",
  "description": "คำอธิบายใหม่"
}
```

**Example 2: Update only photos (replace all)**

```json
{
  "title": "เที่ยวเกาะช้าง",
  "photos": [
    "https://example.com/new1.jpg",
    "https://example.com/new2.jpg",
    "https://example.com/new3.jpg",
    "https://example.com/new4.jpg"
  ]
}
```

**Example 3: Update multiple fields**

```json
{
  "title": "เที่ยวเกาะช้าง - Final",
  "description": "อัปเดตคำอธิบาย",
  "tags": ["เกาะ", "ทะเล", "ตราด", "พักผ่อน"],
  "latitude": 12.05,
  "longitude": 102.325
}
```

**Notes:**

- `title` must always be included (required)
- Other fields are optional
- To update photos, send the complete new array (not incremental)

**Response:** `200 OK` - Returns updated trip object

**Example:**

```bash
curl -X PUT http://localhost:8080/api/trips/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "title": "เที่ยวเกาะช้าง - Updated",
    "description": "คำอธิบายใหม่"
  }'
```

---

#### 10. Delete Trip

Delete a trip. Only the authenticated user who created the trip can delete it.

**Endpoint:** `DELETE /api/trips/{id}`

**Path Parameters:**

- `id` (long): Trip ID

**Headers:**

```
Authorization: Bearer <your-access-token>
```

**Response:** `204 No Content`

**Example:**

```bash
curl -X DELETE http://localhost:8080/api/trips/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

### 📸 File Upload API

#### 11. Upload Image

Upload an image file to cloud storage (Supabase). Returns the public URL.

**Endpoint:** `POST /api/files/upload`

**Headers:**

```
Authorization: Bearer <your-access-token>
Content-Type: multipart/form-data
```

**Request Body (multipart/form-data):**

- `file` (file): Image file to upload

**Response:** `200 OK`

```json
{
  "url": "https://your-bucket.supabase.co/storage/v1/object/public/trips/abc123.jpg"
}
```

**Example (using curl):**

```bash
curl -X POST http://localhost:8080/api/files/upload \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -F "file=@/path/to/your/image.jpg"
```

**Example (using JavaScript/Fetch):**

```javascript
const formData = new FormData();
formData.append("file", fileInput.files[0]);

fetch("http://localhost:8080/api/files/upload", {
  method: "POST",
  headers: {
    Authorization: `Bearer ${accessToken}`,
  },
  body: formData,
})
  .then((response) => response.json())
  .then((data) => {
    console.log("Uploaded URL:", data.url);
  });
```

---

## 🔧 Setup & Configuration

### 1. Database Configuration

Copy the example configuration file:

```bash
cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties
```

Then edit `application-local.properties` with your actual credentials:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://your-host:5432/postgres
spring.datasource.username=your-username
spring.datasource.password=your-password

# JWT Configuration
jwt.secret=your-generated-secret-here
jwt.expiration=86400000

# Supabase Storage (for file upload)
supabase.url=https://your-project.supabase.co
supabase.key=your-supabase-anon-key
supabase.bucket-name=trips
```

### 2. Generate JWT Secret

Generate a secure JWT secret key:

```bash
openssl rand -base64 64
```

Add it to `application-local.properties`:

```properties
jwt.secret=your-generated-secret-here
```

### 3. Environment Variables (Production)

For production, use environment variables instead of property files:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://your-host:5432/postgres
export SPRING_DATASOURCE_USERNAME=your-username
export SPRING_DATASOURCE_PASSWORD=your-password
export JWT_SECRET=your-secure-jwt-secret
export SUPABASE_URL=https://your-project.supabase.co
export SUPABASE_KEY=your-supabase-key
export SUPABASE_BUCKET_NAME=trips
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

The API will start on `http://localhost:8080`

---

## 🧪 Testing the API

### Quick Test Flow

1. **Register a user**

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"test1234","displayName":"Test User"}'
```

2. **Login and get token**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"test1234"}'
```

3. **Upload an image**

```bash
curl -X POST http://localhost:8080/api/files/upload \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -F "file=@image.jpg"
```

4. **Create a trip**

```bash
curl -X POST http://localhost:8080/api/trips \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My Trip",
    "description": "Trip description",
    "photos": ["URL_FROM_UPLOAD"],
    "tags": ["test"],
    "latitude": 13.7563,
    "longitude": 100.5018
  }'
```

5. **Get your trips**

```bash
curl -X GET http://localhost:8080/api/trips/mine \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 🔒 Security Best Practices

1. ✅ Never commit `application-local.properties` to Git
2. ✅ Use environment variables in production
3. ✅ Keep `application-local.properties.example` updated as a template
4. ✅ Use strong, randomly generated JWT secrets (at least 256 bits)
5. ✅ Rotate secrets regularly in production
6. ✅ Always use HTTPS in production
7. ✅ Implement rate limiting for authentication endpoints

---

## 📝 Error Responses

### Common HTTP Status Codes

| Status Code               | Description                                |
| ------------------------- | ------------------------------------------ |
| 200 OK                    | Request succeeded                          |
| 204 No Content            | Request succeeded (no response body)       |
| 400 Bad Request           | Invalid request body or parameters         |
| 401 Unauthorized          | Missing or invalid authentication token    |
| 403 Forbidden             | User not authorized to perform this action |
| 404 Not Found             | Resource not found                         |
| 500 Internal Server Error | Server error occurred                      |

### Error Response Format

```json
{
  "timestamp": "2025-11-05T10:30:00.000+00:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource",
  "path": "/api/trips/mine"
}
```

---

## 🏗️ Project Structure

```
src/main/java/com/travelapp/travel_explorer/
├── controller/          # REST API Controllers
│   ├── AuthController.java
│   ├── TripController.java
│   └── FileUploadController.java
├── dto/                # Data Transfer Objects
│   ├── TripDto.java
│   ├── UserDto.java
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   └── AuthResponse.java
├── entity/             # JPA Entities
│   ├── Trip.java
│   └── User.java
├── repository/         # Database Repositories
│   ├── TripRepository.java
│   └── UserRepository.java
├── security/           # Security Configuration
│   ├── SecurityConfig.java
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
└── service/            # Business Logic
    ├── AuthService.java
    ├── TripService.java
    ├── UserService.java
    └── SupabaseStorageService.java
```

---

## 🛠️ Technologies Used

- **Spring Boot 3.x** - Application framework
- **Spring Security** - Authentication & authorization
- **JWT** - Token-based authentication
- **PostgreSQL** - Database
- **JPA/Hibernate** - ORM
- **Supabase** - Cloud storage for images
- **Lombok** - Reduce boilerplate code
- **Maven** - Build tool

---

## 📄 License

This project is licensed under the MIT License.
