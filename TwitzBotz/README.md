#TwitzBotz


#Beschreibung:

TwitzBotz ist ein automatischer Twitter-Client.
Sie können TwitzBotz Twitter-User beobachten lassen. 
Wenn der User eine Frage stellt, die TwitzBotz kennt und an ihn gerichtet ist (@TwitBot2), wird TwitzBotz antworten.

#System:
	* IDE Version: NetBeans IDE 7.0.1 (Build 201107282000)
	* Java: 1.6.0_26; Java HotSpot(TM) 64-Bit Server VM 20.1-b02-384
	* System: Mac OS X version 10.6.8 running on x86_64; MacRoman; de_DE (nb)
	* Ant: Apache Ant(TM) version 1.8.2 compiled on December 20 2010
	* Twitter4J: Twitter4J version 2.2.5

#Installation mit der Downloadsektion:

	1. ZIP-Archiv entpacken.
	2. Überprüfen ob Ordner "languages" mit Propertie-Datein vorhanden ist -> ggf. vom Repository nachladen.
	3. Überprüfen ob Datei "twitzbotz.properties" vorhanden ist -> ggf. vom Repository nachladen.
	4. Überprüfen ob Datei "user.txt" vorhanden ist -> ggf. vom Repository nachladen.

#Installation mit dem Ant-Script:

	1. Clone das Repository mit: "git clone git://github.com/Yserz/TwitzBotz.git"
	2. Navigiere zur "build.xml".
	3. Gib "ant" in die Konsole ein.
	4. Überprüfen ob Ordner "dist/languages" mit Propertie-Dateien vorhanden ist -> ggf. vom Repository nachladen bzw. kopieren.
	5. Überprüfen ob Datei "dist/twitzbotz.properties" vorhanden ist -> ggf. vom Repository nachladen bzw. kopieren.
	6. Überprüfen ob Ordner "dist/log" vorhanden ist -> ggf. Ordner anlegen
	7. Überprüfen ob Datei "user.txt" vorhanden ist -> ggf. vom Repository nachladen bzw. kopieren.
	8. Ihr ausführbares Programm liegt jetzt im Ordner "dist".


#Programmstart:

	1. Console öffnen
	2. Zu "dist/TwitzBotz.jar" navigieren
	3. Programm starten mit: java -jar "TwitzBotz.jar"

#Programm beenden:

	ctrl + c (Mac OS X)

#Programm anpassen:

	Fragen und Antworten:
	
		Mit der "languages/DE.properties" können Sie den TwitzBotz auf Ihre Bedürfnisse anpassen.
		Tragen Sie einfach die Frage und die Antwort in folgender Form in das Dokument ein:
	
			<frage>=<antwort>(enter)
			<frage>=<antwort>(enter)
			...usw
		
		Bitte beachten Sie: 
		* Leerzeichen mit _ ersetzen
		* Alle Fragen klein schreiben
		* Antworten dürfen, inklusive "@<userToListen> ", nicht länger als 140 Zeichen sein. 
		* Umlaute mit ae, ue und oe ersetzen
	
	Userliste:
		
		Mit der Userliste (user.txt) können Sie bestimmen welche User bei Twitter beobachtet werden sollen.
		Tragen Sie einfach die User untereinander in die Liste ein.
		
			<user>(enter)
			<user>(enter)
			...usw
		
		Bitte beachten Sie:
		* Schreiben Sie die User bitte ohne @-Zeichen in die Liste
		* Bei einer Anzahl von mehr als 6 Usern in der Liste kann es zu unvorhersehbaren Fehlern kommen.
		
		
		