archivator
==========

Archiv-Software zur Verwaltung und Recherche von Archivalen.
(Archival System for administering and retrieving archival items)

Die Software wird im Rahmen der Ausbildung zum Medien-Informatiker 
am Lette-Verein (http://www.lette-verein.de) im Fach Softwareentwicklung
erstellt.

Aufsetzen einer Entwicklungsumgebung - Eclipse

1. Clonen des Repositories unter Zuhilfenahme des New Project Wizards.
   Es muss hier ein Dynamic Web Project angelegt werden.

2. JSF- und JPA-Facette hinzufügen mit den jeweils aktuellen Bibliotheken
   von MyFaces (2.1.10) und EclipseLink (2.4.1)
   
3. Einrichten eines Servlet-Containers (Servers). Als Runtime wird
   Tomcat in der aktuellen Version (zur Zeit 7.0.34) empfohlen.
   
4. Primefaces-Bibliothek in der aktuellen Version (aktuell 3.4.2)
   zum Build-Path und zum Deployment-Assembly hinzufügen.
   
5. Weld-Bibliothek in der aktuellen Version (aktuell 1.1.10)
   zum Build-Path und zum Deployment-Assembly hinzufügen.

6. Derby DB Bibliothek in der aktuellen Version (aktuell 10.9.1.0)
   zum Build-Path und zum Deployment-Assembly hinzufügen.

7. Compass Bibliothek zum Build-Path und zum Deployment-Assembly
   hinzufügen.
   (Bitte daran denken Compass14-*.jar NICHT in die Libary mit 
   auf zu nehmen)