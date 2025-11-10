# Travel Explorer API

A Spring Boot REST API for managing travel trips and user authentication with JWT-based security.

## ✨ Features

- 🔐 **JWT Authentication** - Secure token-based authentication
- 👤 **User Management** - Register, login, and profile management
- 🗺️ **Trip CRUD Operations** - Create, read, update, and delete trips
- 🔍 **Multi-field Search** - Search trips by title, description, and tags
- 📸 **Image Upload** - Upload images to cloud storage (Supabase)
- 🔒 **Authorization** - Users can only edit/delete their own trips
- ✅ **Comprehensive Error Handling** - Structured error responses with clear messages
- 📝 **Field Validation** - Input validation with detailed error messages
- 🌐 **RESTful Design** - Following REST API best practices

## 🚀 Quick Start

### Run the Application

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## 📋 API Overview

### Table of Contents

- [🔓 Authentication APIs](#-authentication-apis)
  - Register, Login, Get Current User
- [🌍 Trip APIs (Public)](#-trip-apis-public)
  - Get All Trips, Search Trips, Get Trip by ID
- [🔐 Trip Management (Protected)](#-trip-management-apis-protected)
  - Get My Trips, Create Trip, Update Trip, Delete Trip
- [📸 File Upload API](#-file-upload-api)
  - Upload Image
- [🎨 Frontend Integration Guide](#-frontend-integration-guide)
  - Complete React examples with photo upload workflow
- [📝 Error Responses](#-error-responses)
  - Error codes, formats, and handling examples

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
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "displayName": "John Doe"
  }
}
```

**Error Responses:**

- `400 Bad Request` - Validation error (missing/invalid fields)
- `409 Conflict` - Email already registered

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
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "displayName": "John Doe"
  }
}
```

**Error Responses:**

- `400 Bad Request` - Validation error (missing/invalid fields)
- `401 Unauthorized` - Invalid email or password

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

**Error Responses:**

- `401 Unauthorized` - Missing or invalid token
- `404 Not Found` - User not found

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

**Error Responses:**

- `500 Internal Server Error` - Server error

**Example:**

```bash
curl -X GET http://localhost:8080/api/trips
```

````

**Error Responses:**

- `500 Internal Server Error` - Server error

**Example:**

```bash
curl -X GET http://localhost:8080/api/trips
````

---

#### 5. Search Trips

Search trips by keyword (searches in title, description, and tags).

**Endpoint:** `GET /api/trips?query={keyword}`

**Query Parameters:**

- `query` (string, optional): Search keyword

**Response:** `200 OK` - Same format as Get All Trips

**Error Responses:**

- `400 Bad Request` - Invalid query parameter

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

**Error Responses:**

- `404 Not Found` - Trip with specified ID not found

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

**Error Responses:**

- `401 Unauthorized` - Missing or invalid token
- `404 Not Found` - User not found

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

**Error Responses:**

- `400 Bad Request` - Validation error (missing title)
- `401 Unauthorized` - Missing or invalid token
- `404 Not Found` - User not found

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

**Error Responses:**

- `400 Bad Request` - Validation error (title required)
- `401 Unauthorized` - Missing or invalid token
- `403 Forbidden` - User can only edit their own trips
- `404 Not Found` - Trip not found

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

**Error Responses:**

- `401 Unauthorized` - Missing or invalid token
- `403 Forbidden` - User can only delete their own trips
- `404 Not Found` - Trip not found

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

**Error Responses:**

- `400 Bad Request` - No file selected or invalid file type
- `401 Unauthorized` - Missing or invalid token
- `413 Payload Too Large` - File size exceeds maximum allowed size

**Example (using curl):**

```bash
curl -X POST http://localhost:8080/api/files/upload \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -F "file=@/path/to/your/image.jpg"
```

}

````

**Error Responses:**

- `400 Bad Request` - No file selected or invalid file type
- `401 Unauthorized` - Missing or invalid token
- `413 Payload Too Large` - File size exceeds maximum allowed size

**Example (using curl):**

```bash
curl -X POST http://localhost:8080/api/files/upload \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -F "file=@/path/to/your/image.jpg"
````

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

## 🎨 Frontend Integration Guide

### Complete Flow: Create Trip with Photos

This section explains the recommended workflow for creating a trip with photos from the frontend.

#### Why Two Separate APIs?

The API design separates file upload from trip creation for several reasons:

1. **Flexibility** - Upload photos independently, use URLs anywhere
2. **Progress Tracking** - Show upload progress per file
3. **Error Handling** - Handle upload failures separately from trip creation
4. **Reusability** - Use uploaded photos in multiple trips
5. **Performance** - Upload large files without blocking other operations

#### Recommended Workflow

**Step 1: Upload Photos** → **Step 2: Create Trip with Photo URLs**

```
┌─────────────┐      ┌──────────────┐      ┌─────────────┐
│ Select      │      │ Upload Files │      │ Create Trip │
│ Images      │  →   │ Get URLs     │  →   │ with URLs   │
└─────────────┘      └──────────────┘      └─────────────┘
```

#### React Example: Complete Implementation

```jsx
import { useState } from "react";

function CreateTripForm() {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    tags: [],
    latitude: null,
    longitude: null,
    selectedFiles: [], // File objects from input
  });

  const [loading, setLoading] = useState(false);
  const [progress, setProgress] = useState(0);
  const [loadingMessage, setLoadingMessage] = useState("");

  const handleFileSelect = (e) => {
    const files = Array.from(e.target.files);
    setFormData((prev) => ({
      ...prev,
      selectedFiles: files,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      // ====================================
      // STEP 1: Upload photos
      // ====================================
      setLoadingMessage("Uploading images...");
      const uploadedUrls = [];

      for (let i = 0; i < formData.selectedFiles.length; i++) {
        const file = formData.selectedFiles[i];

        const photoFormData = new FormData();
        photoFormData.append("file", file);

        const uploadRes = await fetch("/api/files/upload", {
          method: "POST",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
          body: photoFormData,
        });

        if (!uploadRes.ok) {
          const error = await uploadRes.json();
          throw new Error(error.message || "Upload failed");
        }

        const { url } = await uploadRes.json();
        uploadedUrls.push(url);

        // Update progress
        const uploadProgress = ((i + 1) / formData.selectedFiles.length) * 50;
        setProgress(uploadProgress);
        setLoadingMessage(
          `Uploading ${i + 1}/${formData.selectedFiles.length} images`
        );
      }

      // ====================================
      // STEP 2: Create trip with photo URLs
      // ====================================
      setLoadingMessage("Creating trip...");
      setProgress(75);

      const tripData = {
        title: formData.title,
        description: formData.description,
        photos: uploadedUrls, // ✅ Use uploaded URLs
        tags: formData.tags,
        latitude: formData.latitude,
        longitude: formData.longitude,
      };

      const createRes = await fetch("/api/trips", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(tripData),
      });

      if (!createRes.ok) {
        const error = await createRes.json();
        throw new Error(error.message || "Create trip failed");
      }

      const createdTrip = await createRes.json();

      // ====================================
      // STEP 3: Success!
      // ====================================
      setProgress(100);
      setLoadingMessage("Trip created successfully!");

      // Redirect to trip detail page
      setTimeout(() => {
        window.location.href = `/trips/${createdTrip.id}`;
      }, 1000);
    } catch (error) {
      alert("Error: " + error.message);
      setProgress(0);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      {/* Title Input */}
      <div>
        <label>Trip Title *</label>
        <input
          type="text"
          placeholder="Enter trip title"
          value={formData.title}
          onChange={(e) =>
            setFormData((prev) => ({ ...prev, title: e.target.value }))
          }
          required
        />
      </div>

      {/* Description Input */}
      <div>
        <label>Description</label>
        <textarea
          placeholder="Describe your trip..."
          value={formData.description}
          onChange={(e) =>
            setFormData((prev) => ({ ...prev, description: e.target.value }))
          }
        />
      </div>

      {/* File Input */}
      <div>
        <label>Photos *</label>
        <input
          type="file"
          multiple
          accept="image/*"
          onChange={handleFileSelect}
          required
        />
        {formData.selectedFiles.length > 0 && (
          <p>Selected {formData.selectedFiles.length} image(s)</p>
        )}
      </div>

      {/* Tags Input */}
      <div>
        <label>Tags</label>
        <input
          type="text"
          placeholder="beach, nature, adventure (comma separated)"
          onChange={(e) =>
            setFormData((prev) => ({
              ...prev,
              tags: e.target.value
                .split(",")
                .map((t) => t.trim())
                .filter((t) => t),
            }))
          }
        />
      </div>

      {/* Submit Button */}
      <button type="submit" disabled={loading}>
        {loading
          ? `${loadingMessage} (${Math.round(progress)}%)`
          : "Create Trip"}
      </button>

      {/* Progress Bar */}
      {loading && (
        <div className="progress-bar">
          <div className="progress-fill" style={{ width: `${progress}%` }} />
        </div>
      )}
    </form>
  );
}

export default CreateTripForm;
```

#### Update Trip Example

```jsx
async function handleUpdateTrip(tripId, newPhotos = []) {
  const accessToken = localStorage.getItem("token");

  try {
    // Step 1: Upload new photos (if any)
    const newPhotoUrls = [];

    if (newPhotos.length > 0) {
      for (const file of newPhotos) {
        const formData = new FormData();
        formData.append("file", file);

        const res = await fetch("/api/files/upload", {
          method: "POST",
          headers: { Authorization: `Bearer ${accessToken}` },
          body: formData,
        });

        const { url } = await res.json();
        newPhotoUrls.push(url);
      }
    }

    // Step 2: Update trip with new photo URLs
    const existingPhotos = ["existing-url-1.jpg", "existing-url-2.jpg"];
    const allPhotos = [...existingPhotos, ...newPhotoUrls];

    const updateRes = await fetch(`/api/trips/${tripId}`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        title: "Updated Title",
        photos: allPhotos, // ✅ Combine existing + new URLs
      }),
    });

    const updatedTrip = await updateRes.json();
    console.log("Updated trip:", updatedTrip);
  } catch (error) {
    console.error("Update failed:", error);
  }
}
```

#### Image Preview (No Upload Required)

For showing image previews before upload, use `URL.createObjectURL()`:

```jsx
function ImagePreview({ files }) {
  return (
    <div className="preview-grid">
      {files.map((file, index) => (
        <img
          key={index}
          src={URL.createObjectURL(file)} // ✅ Local preview, no upload
          alt={`Preview ${index + 1}`}
          style={{ width: 150, height: 150, objectFit: "cover" }}
        />
      ))}
    </div>
  );
}
```

#### Error Handling Best Practices

```jsx
async function handlePhotoUpload(file) {
  try {
    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch("/api/files/upload", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      body: formData,
    });

    if (!response.ok) {
      const error = await response.json();

      // Handle specific error cases
      switch (error.status) {
        case 400:
          if (error.message.includes("image files")) {
            throw new Error("Only image files are supported (JPEG, PNG, GIF)");
          }
          throw new Error(error.message);

        case 401:
          // Redirect to login
          window.location.href = "/login";
          return;

        case 413:
          throw new Error("Image file is too large. Maximum size is 5MB");

        default:
          throw new Error("Failed to upload image. Please try again.");
      }
    }

    return await response.json();
  } catch (error) {
    console.error("Upload error:", error);
    throw error;
  }
}
```

#### Common Pitfalls to Avoid

❌ **DON'T: Try to upload files in trip creation request**

```javascript
// ❌ This won't work!
fetch("/api/trips", {
  method: "POST",
  body: formData, // Contains files directly
});
```

✅ **DO: Upload files first, then use URLs**

```javascript
// ✅ Correct approach
const urls = await uploadFiles(files);
fetch("/api/trips", {
  method: "POST",
  body: JSON.stringify({ photos: urls }),
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
| 409 Conflict              | Resource conflict (e.g., duplicate email)  |
| 413 Payload Too Large     | File size exceeds maximum allowed size     |
| 500 Internal Server Error | Server error occurred                      |

### Error Response Format

All error responses follow a consistent structure:

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 400,
  "error": "Bad Request",
  "message": "Descriptive error message",
  "path": "/api/endpoint"
}
```

For validation errors, additional field-level details are included:

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input data",
  "path": "/api/auth/register",
  "errors": [
    {
      "field": "email",
      "message": "Invalid email format"
    },
    {
      "field": "password",
      "message": "Password must be at least 6 characters"
    }
  ]
}
```

### Error Examples by Endpoint

#### Authentication Errors

**409 Conflict - Duplicate Email (Register)**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 409,
  "error": "Conflict",
  "message": "This email is already registered",
  "path": "/api/auth/register"
}
```

**400 Bad Request - Validation Error (Register)**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input data",
  "path": "/api/auth/register",
  "errors": [
    {
      "field": "email",
      "message": "Email is required"
    },
    {
      "field": "password",
      "message": "Password must be at least 6 characters"
    }
  ]
}
```

**401 Unauthorized - Wrong Credentials (Login)**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email or password",
  "path": "/api/auth/login"
}
```

**401 Unauthorized - Missing Token**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource",
  "path": "/api/trips/mine"
}
```

#### Trip Errors

**404 Not Found - Trip Not Found**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 404,
  "error": "Not Found",
  "message": "Trip not found with id: 999",
  "path": "/api/trips/999"
}
```

**403 Forbidden - Cannot Edit Others' Trips**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 403,
  "error": "Forbidden",
  "message": "You can only edit your own trips",
  "path": "/api/trips/1"
}
```

**403 Forbidden - Cannot Delete Others' Trips**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 403,
  "error": "Forbidden",
  "message": "You can only delete your own trips",
  "path": "/api/trips/1"
}
```

**400 Bad Request - Validation Error (Create/Update Trip)**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input data",
  "path": "/api/trips",
  "errors": [
    {
      "field": "title",
      "message": "Title is required"
    }
  ]
}
```

#### File Upload Errors

**400 Bad Request - No File Selected**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 400,
  "error": "Bad Request",
  "message": "Please select a file to upload",
  "path": "/api/files/upload"
}
```

**400 Bad Request - Invalid File Type**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 400,
  "error": "Bad Request",
  "message": "Only image files are supported",
  "path": "/api/files/upload"
}
```

**413 Payload Too Large - File Too Large**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 413,
  "error": "Payload Too Large",
  "message": "File size exceeds maximum allowed size",
  "path": "/api/files/upload"
}
```

#### Server Errors

**500 Internal Server Error**

```json
{
  "timestamp": "2025-11-06T10:20:21",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An internal server error occurred",
  "path": "/api/trips"
}
```

### Error Handling in Frontend

**JavaScript/Fetch Example:**

```javascript
async function createTrip(tripData, accessToken) {
  try {
    const response = await fetch("http://localhost:8080/api/trips", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(tripData),
    });

    if (!response.ok) {
      const error = await response.json();

      // Handle validation errors
      if (error.status === 400 && error.errors) {
        error.errors.forEach((fieldError) => {
          console.error(`${fieldError.field}: ${fieldError.message}`);
        });
        throw new Error("Validation failed");
      }

      // Handle other errors
      throw new Error(error.message || "Request failed");
    }

    return await response.json();
  } catch (error) {
    console.error("Error creating trip:", error.message);
    throw error;
  }
}
```

**Axios Example:**

```javascript
import axios from "axios";

// Setup axios interceptor for error handling
axios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      const { status, data } = error.response;

      // Handle specific status codes
      switch (status) {
        case 400:
          if (data.errors) {
            // Validation errors
            console.error("Validation errors:", data.errors);
          } else {
            console.error("Bad request:", data.message);
          }
          break;
        case 401:
          // Redirect to login
          console.error("Unauthorized:", data.message);
          window.location.href = "/login";
          break;
        case 403:
          console.error("Forbidden:", data.message);
          break;
        case 404:
          console.error("Not found:", data.message);
          break;
        case 409:
          console.error("Conflict:", data.message);
          break;
        default:
          console.error("Error:", data.message);
      }
    }
    return Promise.reject(error);
  }
);
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
│   ├── AuthResponse.java
│   └── ErrorResponse.java
├── entity/             # JPA Entities
│   ├── Trip.java
│   └── User.java
├── exception/          # Custom Exceptions & Global Handler
│   ├── DuplicateEmailException.java
│   ├── ResourceNotFoundException.java
│   ├── UnauthorizedException.java
│   ├── ForbiddenException.java
│   ├── InvalidFileException.java
│   └── GlobalExceptionHandler.java
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

## 🎯 Key Implementation Highlights

### Error Handling

- **Global Exception Handler** (`@RestControllerAdvice`) catches all exceptions
- **Custom Exception Classes** for specific error scenarios
- **Structured Error Responses** with consistent JSON format
- **Field-level Validation Errors** for better user feedback
- **HTTP Status Codes** following REST conventions

### Security

- **JWT Authentication** with configurable expiration
- **Password Encryption** using BCrypt
- **Ownership Verification** - users can only modify their own trips
- **Public/Protected Routes** configuration
- **CORS** configuration ready for frontend integration

### Data Management

- **Partial Update Support** - update only changed fields
- **Multi-field Search** - search across title, description, and tags
- **PostgreSQL Arrays** for storing photos and tags
- **Soft Ownership** - automatic author assignment on creation

### File Handling

- **Image Upload Validation** - file type and size checks
- **Cloud Storage Integration** - Supabase storage
- **Public URL Generation** - instant access to uploaded images

---

## 📄 License

This project is licensed under the MIT License.
