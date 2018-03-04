puck2
=====

Configuration avec IntelliJ
---------------------------
1. Créer un nouveau projet depuis les sources.
2. Activer l'option **Delegate IDE build/run actions to gradle** dans  
`Preferences -> Buid, Execution, Deployment -> Build tools -> Gradle -> Runner`

Compilation
-----------
`./gradlew`

Usage
-----
`java -jar puck2.jar programDir ?outputFile`  
Le lancement du programme sans arguments provoque l'ouverture de l'interface graphique.  

Si l'agument outputFile n'est pas fourni, le graphe sera affiché sur la sortie
standard au lieu d'être sauvegardé dans un fichier.  

L'argument programDir peut être un nom de fichier ou un nom de dossier. Dans le 
second cas, tous les fichier .java contenus dans le dossier et ses sous-dossiers
serton ajoutés au projet.



Configuration avec Eclipse 
---------------------------

1. Plugins nécessaires : dans le menu Help ▸ Eclipse Marketplace

Gradle : Buildship Gradle Integration.
JavaFX : e(fx)clipe.

2.Importer le projet:
  1.Selectionner dans le menu File ▸ Import… ▸ Gradle ▸ Gradle Project.
  2.Sur la fenêtre qui s'ouvre, spécifier l'emplacement du projet puis cliqué sur le bouton Finish.

3. Utilisation des Gradle tasks:

Build ▸ build : génère une archive JAR exécutable.
Ide ▸ eclipse : génère un projet eclipse.

(Eclipse ne met pas à jour le classpath automatiquement, si le fichier build.gradle est mis à jour: clic-droit sur le projet,
Gradle ▸ Refresh Gradle Project).

Usage 
-----

1. clic-droit sur le projet
2. Run As ▸ Java Application
3. Sélectionner Puck2Main.java








