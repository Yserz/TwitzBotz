==========
##TwitzBotz
==========

#Beschreibung:

TwitzBotz ist ein automatischer Twitter-Client.
Sie können TwitzBotz Twitter-User beobachten lassen. 
Wenn der User eine Frage stellt, die TwitzBotz kennt und an ihn gerichtet ist (@TwitBot2), wird TwitzBotz antworten.

#System:
	IDE Version: NetBeans IDE 7.0.1 (Build 201107282000)
	Java: 1.6.0_26; Java HotSpot(TM) 64-Bit Server VM 20.1-b02-384
	System: Mac OS X version 10.6.8 running on x86_64; MacRoman; de_DE (nb)
	Ant: Apache Ant(TM) version 1.8.2 compiled on December 20 2010

#Installation mit der Downloadsektion:

	1. ZIP-Archiv entpacken.
	2. Überprüfen ob Ordner "languages" mit Propertie-Datein vorhanden ist -> ggf. vom Repository nachladen.
	3. Überprüfen ob Datei "twitzbotz.properties" vorhanden ist -> ggf. vom Repository nachladen.

#Installation mit dem Ant-Script:

	1. Clone das Repository mit: "git clone git://github.com/Yserz/TwitzBotz.git"
	2. Navigiere zur "build.xml".
	3. Gib "ant" in die Konsole ein.
	4. Überprüfen ob Ordner "dist/languages" mit Propertie-Dateien vorhanden ist -> ggf. vom Repository nachladen bzw. kopieren.
	5. Überprüfen ob Datei "dist/twitzbotz.properties" vorhanden ist -> ggf. vom Repository nachladen bzw. kopieren.
	6. Überprüfen ob Ordner "dist/log" vorhanden ist -> ggf. Ordner anlegen
	7. Ihr ausführbares Programm liegt jetzt im Ordner "dist".


#Programmstart:

	1. Console öffnen
	2. Zu "dist/TwitzBotz.jar" navigieren
	3. Programm starten mit: java -jar "TwitzBotz.jar" <userToListen>(optional)

#Programm beenden:

	ctrl + c (Mac OS X)

#Programm anpassen:

	Mit der "languages/DE.properties" können Sie den TwitzBotz auf Ihre Bedürfnisse anpassen.
	Tragen Sie einfach die Frage und die Antwort in folgender Form in das Dokument ein:
	(Bitte beachten Sie, dass Sie Leerzeichen mit _ ersetzen und klein schreiben müssen.
	Des Weiteren dürfen Antworten, inklusive "@<userToListen> ", nicht länger als 140 Zeichen sein. Zudem müssen Sie Umlaute mit ae, ue und oe ersetzen)

		<frage>=<antwort>(enter)
		<frage>=<antwort>(enter)
		...usw}