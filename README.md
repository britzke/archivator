archivator
==========

Archiv-Software zur Verwaltung und Recherche von Archivalen.
(Archival System for administering and retrieving archival items)

Die Software wird für die Ausbildung zum Medien-Informatiker 
am [Lette-Verein](http://www.lette-verein.de) im Fach Softwareentwicklung
herangezogen.

Aufsetzen einer Entwicklungsumgebung - Eclipse

1. Clonen des Repositories unter Zuhilfenahme des _New Project Wizards_
   (es muss hier ein _Dynamic Web Project_ angelegt werden).

2. JSF- und JPA-Facette hinzufügen mit den jeweils aktuellen Bibliotheken
   von MyFaces (2.1.10) und EclipseLink (2.4.1)
   
3. Einrichten eines Servlet-Containers (Servers). Als Runtime wird
   Tomcat in der aktuellen Version (zur Zeit 7.0.35) empfohlen.
   
4. Primefaces-Bibliothek in der aktuellen Version (aktuell 3.4.2)
   zum Build-Path und zum Deployment-Assembly hinzufügen.

5. Primefaces-Extensions in der aktuellen Version (z. Zt. 0.6.2)
   zum Build-Path und zum Deployment-Assembly hinzufügen.
   Notwendig sind mindestens primefaces-extensions-0.6.2.jar
   sowie commons-lang3-3.1.jar. 
   
6. Weld-Bibliothek in der aktuellen Version (aktuell 1.1.10)
   zum Build-Path und zum Deployment-Assembly hinzufügen.

7. Derby DB Bibliothek in der aktuellen Version (aktuell 10.9.1.0)
   zum Build-Path und zum Deployment-Assembly hinzufügen.

8. Compass Bibliothek (compass-2.2.0.jar und lucene-core.jar) 
   zum Build-Path und zum Deployment-Assembly hinzufügen.
   (Bitte daran denken Compass14-*.jar NICHT in die Libary mit 
   auf zu nehmen) [Compass-Downloadlink 2.2.0](http://sourceforge.net/projects/compass/files/compass/2.2.0/compass-2.2.0.zip/download)

9. JUnit in der aktuellen Version zum Build-Path hinzufügen (aktuell 4.11)
   [JUnit-Projektseite](https://github.com/KentBeck/junit/wiki)
  
10. Mockito Bibliothek zum Build-Path in der aktuellen Version (aktuell 1.9.5)
    hinzufügen - [Mockito-Projektseite](http://code.google.com/p/mockito/).

11. PowerMock-Mockito zum Build-Path in der aktuellen Version (aktuell 1.5)
    hinzufügen - [PowerMock-Projektseite](http://code.google.com/p/powermock/).
