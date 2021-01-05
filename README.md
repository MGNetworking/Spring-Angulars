# Projet Spring security et angular v10

Ce projet et un exercice qui pour but d'apprendre la gestion des Json Web Token
dans le context d'application Spring Boot et Angulars.

Dans le context Spring, le version de Java 1.8 et utiliser avec le 
gestion de dependance Maven.
Dans le context d'angular la version 10 de celui-ci est utiliser. 

## Spring Boot project

### Voici la list des dependances Maven utiliser 
* spring-boot-starter-data-jpa
* spring-boot-starter-security
* spring-boot-starter-web
* spring-boot-devtools
* com.h2database
* org.projectlombok
* io.jsonwebtoken
* spring-boot-starter-test
* spring-security-test

Pour plus de précision sur les dépendances, Aller jester un oeil dans le
pom.xml de Spring.

## Angulars V10

### liste des dépendances 
* bootstrap@3.3.7
* @auth0/angular-jwt

### commande et utilisation

Voici la commande pour l'installation de bootstrap :

```bash
npm install bootstrap@3.3.7
```

Pour rappel pour avoir Bootstrap disponible dans tout l'application le
chemin vers sa dépendance doit étre dans les styles du fichier angular.json

```json
{
    "styles": [
              "src/styles.css",
              "node_modules/bootstrap/dist/css/bootstrap.css"
            ]
}
```

Voici la commande pour l'installation de angulars jwt

```bash
npm install @auth0/angular-jwt
```

Cette dépendance et utiliser pour decomposer la parti PayLoad qui 
contient les roles utilisateur. 
Elle est indispensable dans la recupération des droits utilisateur.

Pour voir sont utilisation ce rendre dansle projet angular, aller dans la
classe de service : ````diff AuthentificationService ``` 
Puis voir la méthode : ```diff  saveToken(jwt: string) ``` 



### le projet contient les modules suivant
* login
* new-tasks
* registration
* tasks

Le module registration n'a pas d'implentation pour le moment.

Fin .

