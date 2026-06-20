# Prescripto - Application Fullstack (JEE & React)

Bienvenue sur le dépôt du projet **Prescripto**. Il s'agit d'une application complète de gestion de prescriptions et de rendez-vous médicaux, développée avec un backend robuste en Java EE et un frontend moderne en React.

---

## 🏗️ Architecture du Projet

Le projet est divisé en plusieurs dossiers principaux à la racine :

- **`/backend`** : L'API et la logique serveur. Développé en Java EE (Servlets, DAO) et géré avec Maven. Il gère la connexion à la base de données (MySQL) et expose les endpoints pour les clients.
- **`/admin`** : Le panneau d'administration. Une application web développée en React (avec Vite) permettant de gérer les médecins, les patients et les rendez-vous.
- **`/frontend`** : L'interface utilisateur/patient (React).
- **`/Sécurité_des_SI`** : Dossier contenant les rapports, preuves de tests et vérifications liées à la sécurité des systèmes d'information (ex: tests JUnit, rapports Maven).

---

## 🚀 Technologies Utilisées

### Backend (Serveur)
* **Langage** : Java 8+
* **Framework** : Java EE (Servlets, JDBC)
* **Gestionnaire de dépendances** : Maven
* **Base de données** : MySQL
* **Tests unitaires** : JUnit 5
* **Outils** : NetBeans, GlassFish / Tomcat

### Frontend & Admin (Client)
* **Bibliothèque** : React (via Vite)
* **Styling** : CSS / TailwindCSS (selon la configuration)
* **Gestionnaire de paquets** : npm / yarn

---

## ⚙️ Installation et Démarrage

### 1. Prérequis
- Java JDK 1.8 (minimum)
- Maven 3.x
- Node.js et npm
- Un serveur MySQL local (ou distant)

### 2. Configuration de la base de données
1. Importez le fichier SQL de base (généralement `backend/database.sql` s'il existe) dans votre serveur MySQL.
2. Modifiez le fichier de connexion à la base de données (`backend/src/main/java/com/prescripto/utils/DBConnection.java`) pour y mettre vos identifiants (utilisateur, mot de passe, URL de la base de données).

### 3. Démarrer le Backend
1. Ouvrez le dossier `backend` dans NetBeans (ou votre IDE favori).
2. Vérifiez que la version de Java utilisée par le projet est bien configurée (JDK 1.8 recommandé).
3. Compilez et téléchargez les dépendances :
   ```bash
   cd backend
   mvn clean install
   ```
4. Déployez le projet sur votre serveur d'application (ex: GlassFish ou Tomcat depuis NetBeans).

### 4. Démarrer le panneau Admin
1. Ouvrez un terminal dans le dossier `admin`.
2. Installez les dépendances :
   ```bash
   npm install
   # ou yarn install
   ```
3. Lancez le serveur de développement :
   ```bash
   npm run dev
   # ou yarn dev
   ```

---

## 🧪 Tests Unitaires

Le backend inclut des tests unitaires configurés avec **JUnit 5**.
Pour les lancer, placez-vous dans le dossier `backend` et exécutez :
```bash
mvn test
```
Les rapports de réussite et captures d'écran des builds sont disponibles dans le dossier `Sécurité_des_SI`.

---

## 🤝 Contribution

Si vous souhaitez contribuer :
1. Créez une nouvelle branche pour votre fonctionnalité (`git checkout -b feature/ma-fonctionnalite`)
2. Commitez vos changements (`git commit -m "Ajout d'une nouvelle fonctionnalité"`)
3. Poussez vers la branche (`git push origin feature/ma-fonctionnalite`)
4. Ouvrez une Pull Request.
