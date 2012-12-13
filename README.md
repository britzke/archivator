archivator
==========

Archiv-Software zur Verwaltung und Recherche von Archivalen.
(Archival System for administering and retrieving archival items)

Die Software wird im Rahmen der Ausbildung zum Medien-Informatiker 
am Lette-Verein (http:/www.lette-verein.de) im Fach Softwareentwicklung
erstellt.

Aufsetzen einer Entwicklungsumgebung - Eclipse

1. Clonen des Repositories unter Zuhilfenahme des New Project Wizards.
   Es muss hier ein Dynamic Web Project angelegt werden. Gleichzeitig

2. JSF- und JPA-Facette hinzufügen mit den jeweils aktuellen Bibliotheken
   von MyFaces (2.1.10) und EclipseLink (2.4.1)
   
3. Einrichten eines Servlet-Containers (Servers). Als Runtime wird
   Tomcat in der aktuellen Version empfohlen.
   
4. Primefaces-Bibliothek in der aktuellen Version (aktuell 3.4.2)
   zum Build-Path und zum Deployment-Assembly hinzufügen.
   
5. Weld-Bibliothek in der aktuellen Version (aktuell 1.1.10)
   zum Build-Path und zum Deployment-Assembly hinzufügen.
