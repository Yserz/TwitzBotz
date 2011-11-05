==========
TwitzBotz
==========

Beschreibung:

TwitzBotz ist ein automatischer Twitter-Client.
Sie können TwitzBotz Twitter-User beobachten lassen. 
Wenn der User eine Frage stellt, die TwitzBotz kennt und an ihn gerichtet ist (@TwitBot2), wird TwitzBotz antworten.

Installation(mit der Downloadsektion):

1. ZIP-Archiv entpacken.
2. Überprüfen ob Ordner "languages" mit Propertie-Datein vorhanden ist -> ggf. vom Repository nachladen.
3. Überprüfen ob Datei "twitzbotz.properties" vorhanden ist -> ggf. vom Repository nachladen.

Installation(mit dem ant-script):

1. Clone das Repository mit: git clone git://github.com/Yserz/TwitzBotz.git
2. Navigiere zur build.xml
3. Gib "ant" in die Konsole ein.
4. Überprüfen ob Ordner "dist/languages" mit Propertie-Dateien vorhanden ist -> ggf. vom Repository nachladen bzw. kopieren.
5. Überprüfen ob Datei "dist/twitzbotz.properties" vorhanden ist -> ggf. vom Repository nachladen bzw. kopieren.
6. Überprüfen ob Ordner "dist/log" vorhanden ist -> ggf. Ordner anlegen
7. Ihr ausführbares Programm liegt jetzt im Ordner "dist".


Programmstart:

1. Console öffnen
2. Zu TwitzBotz.jar navigieren(cd <path>)
3. Programm starten mit: java -jar "TwitzBotz.jar" <userToListen>(optional)

Programm beenden:

ctrl + c (Mac OS X)

Programm anpassen:

Mit der "languages/DE.properties" können Sie den TwitzBotz auf Ihre Bedürfnisse anpassen.
Tragen Sie einfach die Frage und die Antwort in folgender Form in das Dokument ein:
(Bitte beachten Sie, dass Sie Leerzeichen mit _ ersetzen und klein schreiben müssen.
Des Weiteren dürfen Antworten, inklusive "@<userToListen> ", nicht länger als 140 Zeichen sein. Zudem müssen Sie Umlaute mit ae, ue und oe ersetzen)

<frage>=<antwort>(enter)
<frage>=<antwort>(enter)
...usw}