# Pay_My_Buddy

# But de l'appilcation

Cette application permet aux clients de transférer de l'argent pour gérer leurs finances ou payer leurs amis.

----------------------------------------------------------------------------------------------------------------------------------------

# MPD

![PayMyBuddy.jpg](Pictures%2FPayMyBuddy.jpg)

----------------------------------------------------------------------------------------------------------------------------------------

# Stack technique

- Java 21
- Spring-boot 3.4.2
- MySQL 9.1.0
- Maven 3.0.0
- Junit 5.11.3
- Jacoco 0.8.12

----------------------------------------------------------------------------------------------------------------------------------------

# Lancer l'application

Pour commencer lancer le script SQL que vous pouvais retrouver dans le chemin src/main/resources/paymybuddy.sql
Une fois le script executer placer vous sur le fichier src/main/java/com/payMyBuddy/PayMyBuddyApplication.java
Lancer l'application avec la commande : 
- mvn spring-boot:run

Une fois sur l'application vous pouver : 
- Vous connnecter avec cette utilisateur enregistrer
  - Identifiant : michel@gmail.com
  - Mot de passe : michel
- Créer votre propre utilisateur en cliquant sur le bouton Pay my buddy qui sert de switch entre la page de login et création de compte.


----------------------------------------------------------------------------------------------------------------------------------------

# Cas de test

Pour le lancement des tests unitaires il faut ce placer sur la BDD de test que vous retrouverais dans le chemin :

- src/test/resources/paymybuddyTest.sql

