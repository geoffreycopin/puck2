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

2. Importer le projet:  
  1.Selectionner dans le menu File ▸ Import… ▸ Gradle ▸ Existing Gradle Project.     
  2.Sur la fenêtre qui s'ouvre, spécifier l'emplacement du projet puis cliqué sur le bouton Finish. 

(Une fois le projet importé, accéder a ses propriétés (clic-droit sur le projet, sélectionner Properties) puis dans Java build path ▸ source dans la zone "Default output folder", s'assurer que le répertoire de compilation soit celui par défaut). 

3. Utilisation des Gradle tasks: Dans Window ▸ Show view ▸ Gradle tasks  
   Pour générer une archive JAR exécutable : Sélectionner "Build" puis clic-droit sur "build" et choisir "Run gradle task".  
   Pour générer un projet eclipse: Sélectionner "Ide" puis clic-droit sur "eclipse" et choisir "Run gradle task"  .  

(Eclipse ne met pas à jour le classpath automatiquement, si le fichier build.gradle est mis à jour: clic-droit sur le projet,
Gradle ▸ Refresh Gradle Project).  

Usage 
-----

Le lancement du programme sans arguments (provoque l'ouverture de l'interface graphique):   

1. clic-droit sur le projet.  
2. Run As ▸ Java Application.  
3. Sélectionner Puck2Main.java.  

 
Le lancement du programme avec les deux arguments suivants : programDir outputFile  ` 

1. clic-droit sur le projet.  
2. Run As ▸ Run Configurations.  
3. Dans Arguments : indiquer la valeur des deux arguments.  
4. Sélectionner ensuite "Apply" puis "Run".  

Si l'agument outputFile n'est pas fourni, le graphe sera affiché sur la console au lieu d'être sauvegardé dans un fichier.  

L'argument programDir peut être un nom de fichier ou un nom de dossier. Dans le 
second cas, tous les fichier .java contenus dans le dossier et ses sous-dossiers
serton ajoutés au projet.


Tests
-----
Le dossier testdir contient les fichiers nécessaires aux tests des Readers.
Chaque dossier correspond à un test.
Chaque test contient:
- un projet Java (tous les fichiers .java contenus dans le dossier de test et ses sous-dossiers)
- des cibles de test au format xml

Les cibles de tests doivent respecter la convention de nomage suivante: 
\<nom de la cible\>_\<quantificateur\>.result

Il existe 2 quantificateurs possibles: all et some.

all =\> Le graphe de dépendances du projet doit correspondre **exactement** au graphe défini dans 
la cible de test.

some =\> Le graphe de dépendances doit contenir tous les éléments définis dans la cible de test.







