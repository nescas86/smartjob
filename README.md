# SmartJob User API

API REST para gestión de usuarios con autenticación JWT.

## Requisitos Previos

- Java 17 o superior
- Gradle 7.x o superior
- Postman o similar para probar los endpoints
- IntelliJ IDEA (opcional)

## Pasos para Probar la Aplicación

### Opción 1: Usando Terminal

1. **Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/user-project.git
cd user-project
```

2. **Verificar la instalación de Java**
```bash
java -version
# Debe mostrar Java 17 o superior
```

3. **Verificar la instalación de Gradle**
```bash
./gradlew --version
# Debe mostrar Gradle 7.x o superior
```

4. **Compilar el proyecto**
```bash
./gradlew clean build
```

5. **Ejecutar la aplicación**
```bash
./gradlew bootRun
```

La aplicación se iniciará en `http://localhost:8080`

### Opción 2: Usando IntelliJ IDEA

1. **Abrir el proyecto**
   - Abre IntelliJ IDEA
   - Selecciona "Open" o "File > Open"
   - Navega hasta la carpeta del proyecto y selecciónala
   - Selecciona "Open as Project"
   - Espera a que IntelliJ indexe el proyecto y descargue las dependencias

2. **Configurar el JDK**
   - Ve a "File > Project Structure" (Ctrl + Alt + Shift + S)
   - En "Project Settings > Project":
     - Project SDK: Selecciona Java 17
     - Language level: 17
   - En "Project Settings > Modules":
     - Language level: 17
   - Click en "Apply" y "OK"

3. **Ejecutar la aplicación**
   - Encuentra la clase `UserProjectApplication.java` en el panel de Project
   - Click derecho sobre la clase
   - Selecciona "Run 'UserProjectApplication'"
   - O usa el botón verde de "Run" en la barra de herramientas

La aplicación se iniciará en `http://localhost:8080`

## Probar los Endpoints

### Usando Postman

1. **Abrir Postman**
2. **Crear una nueva colección** (opcional)
3. **Probar el registro de usuario**:
   - Click en "New" > "Request"
   - Método: POST
   - URL: `http://localhost:8080/api/registro`
   - Headers: 
     - Key: Content-Type
     - Value: application/json
   - Body: 
     - Selecciona "raw"
     - Selecciona "JSON"
     - Pega el siguiente JSON:
```json
{
    "name": "Juan Rodriguez",
    "email": "aaaaaaa@dominio.cl",
    "password": "Abc12",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}
```
   - Click en "Send"

4. **Probar obtener usuarios**:
   - Click en "New" > "Request"
   - Método: GET
   - URL: `http://localhost:8080/api/usuarios`
   - Headers:
     - Key: Content-Type
     - Value: application/json
   - Click en "Send"

### Usando cURL (Terminal)

1. **Registrar usuario**:
```bash
curl -X POST http://localhost:8080/api/registro \
-H "Content-Type: application/json" \
-d '{
    "name": "Juan Rodriguez",
    "email": "aaaaaaa@dominio.cl",
    "password": "Abc12",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}'
```

2. **Obtener usuarios**:
```bash
curl -X GET http://localhost:8080/api/usuarios \
-H "Content-Type: application/json"
```

## Verificar la Base de Datos

### Usando la Consola H2

1. Abre tu navegador
2. Ve a `http://localhost:8080/h2-console`
3. En la pantalla de login:
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: (déjalo vacío)
4. Click en "Connect"
5. En el panel de consultas, puedes ejecutar:
```sql
SELECT * FROM users;
SELECT * FROM phones;
```

## Ver la Documentación de la API

1. Abre tu navegador
2. Ve a `http://localhost:8080/swagger-ui.html`
3. Aquí podrás ver todos los endpoints disponibles y probarlos directamente

## Ejecutar Tests

### Usando Terminal
```bash
./gradlew test
```

### Usando IntelliJ IDEA
1. Click derecho en la carpeta `src/test/java`
2. Selecciona "Run 'Tests in 'src/test/java'"
3. O usa el botón verde de "Run" junto a cualquier clase de test

## Solución de Problemas

### Error de puerto en uso
1. **En Terminal**:
   - Encuentra el proceso usando el puerto:
     ```bash
     # En Windows
     netstat -ano | findstr :8080
     # En Linux/Mac
     lsof -i :8080
     ```
   - Mata el proceso o cambia el puerto en `application.yaml`

2. **En IntelliJ IDEA**:
   - Ve a "Run > Edit Configurations"
   - En "VM options" añade: `-Dserver.port=8081`
   - O modifica `application.yaml`:
     ```yaml
     server:
       port: 8081
     ```

### Error de compilación
1. **En Terminal**:
   ```bash
   ./gradlew clean build --stacktrace
   ```

2. **En IntelliJ IDEA**:
   - Ve a "View > Tool Windows > Build"
   - Click en "Rebuild Project"
   - Revisa los errores en la ventana "Build"

### Error de conexión a la base de datos
1. Verifica que la aplicación esté corriendo
2. Reinicia la aplicación
3. Verifica que no haya otra instancia corriendo

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── smartjob/
│   │           ├── controller/    # Controladores REST
│   │           ├── dto/          # Objetos de transferencia de datos
│   │           ├── model/        # Entidades JPA
│   │           ├── repository/   # Repositorios de datos
│   │           ├── service/      # Lógica de negocio
│   │           └── util/         # Utilidades (JWT, etc.)
│   └── resources/
│       └── application.yaml      # Configuración de la aplicación
└── test/
    └── java/
        └── com/
            └── smartjob/
                ├── service/      # Tests de servicios
                └── util/         # Tests de utilidades
```

## Validaciones a Probar

1. **Registro exitoso**
- Usa el JSON de ejemplo proporcionado arriba
- Deberías recibir un código 200 con los datos del usuario creado

2. **Email duplicado**
- Intenta registrar otro usuario con el mismo email
- Deberías recibir un código 409 con el mensaje "El correo ya registrado"

3. **Contraseña inválida**
- Intenta registrar con una contraseña que no cumpla los requisitos
- Deberías recibir un código 400 con el mensaje de error
- Prueba con: "abc" (falta mayúscula y número)
- Prueba con: "abcde" (falta mayúscula y número)
- Prueba con: "abcde1" (falta mayúscula)

4. **Teléfono inválido**
- Intenta registrar sin teléfono
- Intenta registrar con teléfono sin número
- Intenta registrar con teléfono sin código de ciudad
- Intenta registrar con teléfono sin código de país

## Contribuir

1. Haz fork del repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Endpoints Disponibles

### 1. Registro de Usuario
- **URL**: `POST /api/registro`
- **Headers**:
  - Content-Type: application/json
- **Body**:
```json
{
    "name": "Juan Rodriguez",
    "email": "aaaaaaa@dominio.cl",
    "password": "Abc12",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}
```

### 2. Obtener Todos los Usuarios
- **URL**: `GET /api/usuarios`
- **Headers**:
  - Content-Type: application/json

## Validaciones

### Contraseña
- Mínimo 5 caracteres
- Al menos una letra mayúscula
- Al menos un número
- Ejemplo válido: "Abc12"

### Email
- Formato válido de correo electrónico
- No puede estar duplicado

### Teléfono
- Número: No puede estar vacío
- Código de ciudad: No puede estar vacío
- Código de país: No puede estar vacío

## Documentación API

La documentación de la API está disponible en Swagger UI:
- URL: `http://localhost:8080/swagger-ui.html`

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── smartjob/
│   │           ├── controller/
│   │           ├── dto/
│   │           ├── model/
│   │           ├── repository/
│   │           ├── service/
│   │           └── util/
│   └── resources/
│       └── application.yaml
└── test/
    └── java/
        └── com/
            └── smartjob/
                ├── service/
                └── util/
```

## Ejemplos de Uso

### Registro Exitoso
```bash
curl -X POST http://localhost:8080/api/registro \
-H "Content-Type: application/json" \
-d '{
    "name": "Juan Rodriguez",
    "email": "aaaaaaa@dominio.cl",
    "password": "Abc12",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}'
```

### Obtener Usuarios
```bash
curl -X GET http://localhost:8080/api/usuarios \
-H "Content-Type: application/json"
```

## Manejo de Errores

La API devuelve los siguientes códigos de error:
- 400: Error de validación
- 409: Email duplicado
- 500: Error interno del servidor

## Base de Datos

La aplicación usa H2 como base de datos en memoria. Puedes acceder a la consola H2 en:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Contraseña: (vacía)

## Diagrama de la Solución

```