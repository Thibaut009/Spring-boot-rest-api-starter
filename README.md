# crud-springboot

REST API Spring Boot avec authentification JWT et gestion d'utilisateurs.

## Stack

- Java 25 / Spring Boot 4.0.3
- Spring Security + JWT (jjwt 0.13.0)
- Spring Data JPA / Hibernate
- PostgreSQL
- Lombok
- Bean Validation (Jakarta)

## Prérequis

- JDK 25+
- Maven 3.9+
- PostgreSQL (base de données existante)

## Installation

### 1. Cloner le projet

```bash
git clone <url-du-repo>
cd crud-springboot
```

### 2. Configurer la base de données

Créer une base PostgreSQL :

```sql
CREATE DATABASE crud_springboot;
```

Créer le fichier `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/crud_springboot
spring.datasource.username=<votre_utilisateur>
spring.datasource.password=<votre_mot_de_passe>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=<clé_secrète_longue_minimum_32_caractères>
jwt.expiration=86400000
```

### 3. Lancer l'application

```bash
mvn spring-boot:run
```

L'API est disponible sur `http://localhost:8080`.

## Endpoints

### Authentification — `/api/auth` (public)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `POST` | `/api/auth/register` | Créer un compte |
| `POST` | `/api/auth/login` | Se connecter, retourne un JWT |

**Register — body :**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "secret123"
}
```

**Login — body :**
```json
{
  "email": "john@example.com",
  "password": "secret123"
}
```

**Login — réponse :**
```json
{
  "token": "eyJ...",
  "email": "john@example.com"
}
```

### Utilisateurs — `/api/users` (authentifié)

Toutes les requêtes nécessitent le header :
```
Authorization: Bearer <token>
```

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/api/users` | Lister tous les utilisateurs |
| `GET` | `/api/users/{id}` | Récupérer un utilisateur par ID |
| `POST` | `/api/users` | Créer un utilisateur |
| `PUT` | `/api/users/{id}` | Mettre à jour un utilisateur (partiel) |
| `DELETE` | `/api/users/{id}` | Supprimer un utilisateur |

**PUT — tous les champs sont optionnels :**
```json
{
  "name": "Nouveau nom",
  "email": "nouveau@example.com",
  "password": "nouveaumdp"
}
```

## Features

- **CRUD utilisateur** — création, lecture, mise à jour partielle et suppression
- **Authentification JWT** — tokens stateless, expiration configurable
- **Mots de passe hashés** — BCrypt, jamais exposés dans les réponses
- **Validation des entrées** — `@Valid` sur tous les endpoints (taille, format email, champs requis)
- **Gestion des erreurs** — handler global, 404 sur ressource inexistante, 409 si email déjà utilisé, 401 si token invalide ou utilisateur supprimé
- **Sessions stateless** — aucun état côté serveur, compatible déploiement distribué
