# Application COVID-API

## Prérequis
1. Docker
2. Gradle 8

## Exécutions en local

Pour commencer, clonez le projet sur votre pc en tapant la commande suivante dans un terminal

```
git clone https://github.com/Cacharel02/covid-api.git
```

Pour lancer les tests en local, lancer la commande : 
```
gradle test
```

Pour build le projet en local, lancer la commande : 
```
gradle build
```
Après cela, un fichier .jar est généré dans le répertoire build/libs le fichier a pour nom
```
covid-api-0.0.1-SNAPSHOT.jar
```
Ainsi, pour démarrer l'application en local, il suffit de lancer la commande : 
```
java -jar covid-api-0.0.1-SNAPSHOT.jar
```

## Exécutions depuis jenkins

Accédez à votre applications Jenkins. Ensuite, suivez les étapes suivantes :
1. Ajouter une installation Gradle
Rendez-vous sur "Administrer Jenkins" puis dans "Tools"
Déscendez jusqu'à trouver la partie réservée à Gradle et cliquez sur "Ajouter gradle" 
Donnez lui le nom (name) que vous souhaitez. Pour cet exemple on utilisera le nom "Gradle-8"
Ensuite, si ce n'est pas déjà le cas, cochez la case "Install automatically"
Enfin, dans la partie version, choisissez Gradle 8.3 et cliquez sur Appliquer et ensuite sur Enregistrer.

2. Création d'un nouveau projet
Cliquez sur "Nouveau item" disponible depuis votre tableau de bord
Sélectionnez le type de projet en occurrence "pipeline" et cliquez sur "OK"

3. Pipeline
Accésez à votre projet et cliquez sur "Configurer"
Dans la nouvelle fenêtre, allez sur pipeline 
Vous pouvez vous servir du script suivant pour définir le pipeline

```
pipeline{
    agent any
    tools{
        gradle "Gradle-8" //Vous pourrez remplacer Gradle-8 par le nom que vous avez donnée au moment de l'ajout de Gradle
    }
    environment {
        DOCKER_REGISTRY = 'docker.io/cacharel02'
        DOCKER_IMAGE_NAME = 'covid-api'
    }
    stages{
        stage('Clone repository'){
            steps{
                git branch: 'main', url: 'https://github.com/Cacharel02/covid-api.git'
            }
        }
        stage('Build the project'){
            steps{
                bat 'gradle build'
            }
        }
        stage('Docker Build'){
            steps{
                bat "docker build -t ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:latest ."
            }
        }
        stage('Deploy'){
            steps{
                bat 'docker compose up -d'
            }
        }
    }
}
```

4. Lancement du build
Après avoir crée le Pipeline retournez sur la page de votre projet et cliquez sur "Lancer un build"
(Avant cela, assurez vous que l'applcition Docker Desktop soit en cours d'exécution)
Grâce à la partie centrale de la fenêtre vous pourrez suivre l'avancement du buid. vous pourrez aussi sur le processus dans la partie en bas à gauche de l'écran et choisir "Console Outpout" et suivre l'avancement depuis un terminal.
A la fin du buil, l'application est démarrée et vous pourrez y avoir accès depuis un navigateur, à l'adresse http://localhost:8080




