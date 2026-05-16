<div align="center">

#  PadelClub — Reserva de Pistas

**Aplicación web fullstack para la gestión y reserva de pistas de pádel**

Proyecto *Programación de Aplicaciones Telemáticas*
3º GITT · ETSI ICAI · Universidad Pontificia Comillas · 2025–2026

</div>

---

##  Descripción

PadelClub es una aplicación web que permite a los usuarios **consultar, reservar y gestionar pistas de pádel** de forma sencilla. Los administradores disponen de un panel completo para gestionar pistas, usuarios y reservas.

---

## 👥 Equipo de desarrollo


 _Pinar Moreno, Miguel_  
 _Martín Marina, Jorge_   
 _Sanz Sáiz, Ignacio_   
 _Recarte Pacheco, Ana_   
 _López, Juan David_   
 
---

## 🌐 Acceso a la aplicación

| Entorno | URL |
|---------|-----|
| 🖥️ Frontend | https://juan-pan.github.io/FrontEnd-PadelClub/frontend/ |
| ⚙️ Backend API | https://TU-APP.onrender.com/pistaPadel |

---

## 🔑 Credenciales de prueba

Para probar la aplicación sin necesidad de registrarse, se pueden usar las siguientes cuentas:

### 👤 Usuario estándar (rol USER)
| Campo | Valor |
|-------|-------|
| Email | `carlos@padel.com` |
| Contraseña | `Password123!` |

### 🛡️ Administrador (rol ADMIN)
| Campo | Valor |
|-------|-------|
| Email | `ana.admin@padel.com` |
| Contraseña | `123456` |

> Las cuentas están precargadas en la base de datos mediante `data.sql`.

---

## 🚀 Ejecución en local

### Requisitos previos
- Java 21+
- Maven (o usar el wrapper `./mvnw` incluido)
- Navegador moderno / extensión Live Server en VS Code

### 1. Arrancar el backend

```bash
# Desde la raíz del proyecto
./mvnw spring-boot:run
```

El servidor arranca en `http://localhost:8080`.
La consola H2 está disponible en `http://localhost:8080/h2-console` (usuario: `sa`, contraseña: vacía).

### 2. Abrir el frontend

Abre la carpeta `frontend/` con **Live Server** (VS Code) o navega directamente a:

```
frontend/index.html
```

---

## 🗂️ Estructura del proyecto

```
PadelClub/
├── frontend/                   # Aplicación web (HTML + CSS + JS)
│   ├── css/
│   │   └── style.css
│   ├── js/
│   │   ├── api.js              # Cliente HTTP con autenticación
│   │   ├── auth.js             # Login, logout, gestión de sesión
│   │   ├── guards.js           # Protección de rutas por rol
│   │   ├── pistas.js           # Listado de pistas
│   │   ├── detallesPista.js    # Detalle, disponibilidad y reserva
│   │   ├── mis-reservas.js     # Mis reservas (modificar/cancelar)
│   │   └── admin.js            # Panel de administración
│   ├── index.html
│   ├── login.html
│   ├── register.html
│   ├── pistas.html
│   ├── pista.html
│   ├── mis-reservas.html
│   ├── admin.html
│   └── profile.html
│
└── src/main/java/.../          # Backend Spring Boot
    ├── controller/             # Endpoints REST
    ├── service/                # Lógica de negocio
    ├── entity/                 # Entidades JPA
    ├── repos/                  # Repositorios
    └── security/               # Configuración Spring Security
```

---

## 🧪 Tests

### Backend (JUnit 5)
```bash
./mvnw test
```
Los informes se generan en `target/surefire-reports/`.

### Frontend (Jest)
```bash
cd frontend
npm install
npm test
```

---

## ⚙️ CI/CD

El repositorio incluye dos workflows de **GitHub Actions** en `.github/workflows/`:

| Workflow | Trigger | Acción |
|----------|---------|--------|
| `deploy.yml` | Push a `main` | Despliega `frontend/` en GitHub Pages |
| `backend-tests.yml` | Push a `main` / `develop` | Ejecuta los tests con `./mvnw test` |

---

## 🛠️ Tecnologías

| Capa | Tecnología |
|------|------------|
| Frontend | HTML5, CSS3, JavaScript Vanilla |
| Backend | Java 21, Spring Boot 3, Spring Security |
| Base de datos | H2 (desarrollo) |
| Tests backend | JUnit 5 |
| Tests frontend | Jest + jsdom |
| CI/CD | GitHub Actions |
| Despliegue frontend | GitHub Pages |
| Despliegue backend | Render |

---

<div align="center">
<sub>ETSI ICAI · Universidad Pontificia Comillas · Proyecto PAT 2025–2026</sub>
</div>
