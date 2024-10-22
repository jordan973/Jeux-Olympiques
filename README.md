# Application de réservation de billet - Jeux Olympiques

## Description
Cette application permet de commander des billets pour les Jeux Olympiques. Elle inclut un frontend en React et un backend en Spring Boot. Les utilisateurs peuvent s'inscrire, se connecter, ajouter des articles au panier, effectuer un paiement simulé, consulter leurs commandes et télécharger leur billets.

## Sommaire
1. **Fonctionnalités**
2. **Technologies utilisées**
3. **Prérequis**
4. **Installation**
5. **Tests**
6. **Contributeur**

---

## Fonctionnalités
- Inscription et connexion des utilisateurs
- Gestion du panier
- Paiement simulé
- Visualisation des commandes dans un espace personnel
- Téléchargement des billets

---

## Technologies utilisées
- **Frontend :** React
- **Backend :** Spring Boot
- **Base de données :** PostgreSQL
- **Conteneurisation :** Docker

---

## Prérequis
- **Node.js** (version 21.7.2)
- **Java 17**
- **Maven** (version 3.6.3+)
- **Docker**
- **PostgreSQL** (version 12+)

---

## Installation

### Étape 1 : Cloner le dépôt
```bash
git clone https://github.com/jordan973/Jeux-Olympiques.git
cd Jeux-Olympiques
```

### Étape 2 : Construire l'image Docker

Pour construire l'image contenant à la fois le frontend et le backend, exécutez cette commande dans le répertoire où se trouve le `Dockerfile` :
```bash
docker build -t jeux-olympiques-app .
```

### Étape 3 : Exécuter le conteneur

Après avoir construit l'image, lancez le conteneur en exposant le port 8080 :
```bash
docker run -p 8080:8080 jeux-olympiques-app
```

Après avoir lancé le conteneur, l'application sera disponible à l'adresse suivante :
```bash
http://localhost:8080
```

## Tests

### Exécution des tests

Pour exécuter les tests unitaires et obtenir un rapport de couverture de code avec **JaCoCo**, utilisez la commande suivante dans le dossier `backend` :

```bash
cd backend
mvn clean test
```

### Rapport de couverture de code

Une fois les tests exécutés, le rapport de couverture JaCoCo sera généré. Vous pouvez le trouver sous le chemin suivant :

```bash
/backend/target/site/jacoco/index.html
```

## Contributeur
Jordan GAILLET
