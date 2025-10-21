# Travel Explorer API

A Spring Boot REST API for managing travel trips and user authentication.

## Setup

### 1. Database Configuration

Copy the example configuration file:

```bash
cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties
```

Then edit `application-local.properties` with your actual credentials:

- Database URL (Supabase or local PostgreSQL)
- Database username and password
- JWT secret key

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
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Trips

- `GET /api/trips` - Get all trips
- `GET /api/trips/{id}` - Get trip by ID
- `GET /api/trips/eid/{eid}` - Get trip by EID
- `POST /api/trips` - Create new trip
- `PUT /api/trips/{id}` - Update trip
- `DELETE /api/trips/{id}` - Delete trip

## Security Best Practices

1. ✅ Never commit `application-local.properties` to Git
2. ✅ Use environment variables in production
3. ✅ Keep `application-local.properties.example` updated as a template
4. ✅ Use strong, randomly generated JWT secrets (at least 256 bits)
5. ✅ Rotate secrets regularly in production
