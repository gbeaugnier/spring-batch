# Démonstration de spring batch avec un lanceur via kafka connect

## Fabrication de l'image docker

* Sous intelliJ, charger les deux configurations qui sont dans le dossier `.run`
* Afin de fabriquer le jar et l'image docker de l'applicatif, lancer la configuration `spring-batch [clean, package].run`

## Configuration de l'environnement de test

* Lancer la configuration `containers_ Test.run`
* Accéder au bucket S3 : https//localhost:9000 avec les identifiants admin|minio_password
  * créer un event Kafka :
    * identifier : data_event_id
    * brokers : broker:9092
    * topic : data_event
  * créer le bucket `demo`, activer le versioning
    * ajouter l\'event PUT avec l'ARN `arn:minio:sqs::data_event_id:kafka`
  * créer un access key, remplacer dans le docker-compose les valeurs des variables d'environnement MINIO_ACCESS_KEY et MINIO_SECRET_KEY par celle générée
    * relancer le container `spring-batch`
  
## Tester le fonctionnement

* Sur la page du brocker
  * aller dans le Object Brower / demo
  * upload le fichier de test dans le répertoire `containers`

* vérifier les logs
* vérifier la base de données
  * url : jdbc:postgresql://localhost:5432/postgres
  * username : postgres
  * password : my_password