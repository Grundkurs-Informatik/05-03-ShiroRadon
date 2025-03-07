> Willkommen zu Ihrem ersten Github-Projekt. Bitte folgen Sie den aufgelisteten Schritten mit dem Ziel, am Ende einen Kartenmanager zu veröffentlichen, der unterschiedliche Sortierverfahren mit Listen zur Auswahl bietet.

### Schritt 0: Github-Zugangsdaten für BlueJ vorbereiten
- Notieren Sie die URL dieser Internetseite. In BlueJ gilt sie als die Adresse zu Ihrem Repository.
- Wählen Sie in den Einstellungen zu Ihrem Profil: 
  `Developer Settings -> Personal access tokens -> Token (classic) -> Generate new token (classic) -> Name und Zeitraum des Schlüssels angeben -> alles bei repo auswählen -> Generate Token`. 
- Notieren Sie das Token. In BlueJ gilt es als das Passwort zu Ihrem Repository.

### Schritt 1: BlueJ-Projekt aus dem Github-Repository öffnen
- Starten Sie BlueJ und wählen Sie in der Menüleiste `Werkzeuge → Teamarbeit → Arbeitskopie erstellen...`
- Geben Sie die Adresse und das Passwort des Repositories sowie Ihre Account-Daten ein.
- Gerne können Sie zuvor durch `Test Verbindung...` überprüfen, ob alles richtig angegeben ist.
- Nach dem Bestätigen erhalten Sie ein Dialogfenster mit der Anfrage zum Speicherort des Projekts - hier bitte **das lokale C-Verzeichnis auswählen**, da BlueJ mit Git (noch) keinen Zugriff auf Netzverzeichnisse (G oder H) hat.

### Schritt 2: Code aktualisieren und ins Repository übertragen
- Wenn alles korrekt abgelaufen ist, erhalten Sie ein neues BlueJ-Projekt als lokale Kopie, die aus Ihrem Github-Repository erstellt wurde und damit weiterhin verbunden bleibt.
- Der dort vorhandene Code ist allerdings veraltet. Ersetzen Sie ihn komplett durch Ihren eigenen Code aus dem letzten BlueJ-Projekt mit Listensortierverfahren, das Sie in der letzten Woche erstellt haben.
- Klicken Sie in der Seitenleiste auf `Teamwork -> Commit/Push...` und geben Sie eine aussagekräftige Commit-Nachricht ein (z.B. *"Eigenen Code implementiert"*). Bestätigen Sie es anschließend mit `Commit` und dann `Push`.

### Schritt 3: Auf Code aus älteren Versionen zugreifen
- Es gibt ein Problem: Die ursprüngliche Version enthält die Bubble-Sort-Methode, die Sie auch für den Endprototyp benötigen. Ihre Version hat sie aber wahrscheinlich nicht.
- Sie können auf die ursprüngliche Version in Ihrem Github-Repository zugreifen. Gehen Sie dafür im Browser zurück, öffnen Sie `Kartenmanager_SuS.java` und klicken Sie auf `History` (rechts oben). Öffnen Sie dann die Version mit "*initial commit"*.

### Schritt 4: Code direkt im Github-Repository bearbeiten
- Wenn alles korrekt abgelaufen ist, sollten Sie ein grünes Feld mit dem Code vor sich haben. Finden Sie dort die Bubble-Sort-Methode und kopieren Sie sie.
- Gehen Sie nun zurück zur aktuellen Version von `Kartenmanager_SuS.java` und klicken Sie auf das **Stift-Symbol** (`Edit in place`). 
- Fügen Sie anschließend die kopierte Methode an der entsprechenden Stelle dieser Version hinzu.
- Bestätigen Sie die Änderung mit `Commit changes...` -> *"Bubble-Sort aus älterer Version wiederhergestellt"* -> `Commit changes`.

### Schritt 5: Lokales BlueJ-Projekt aus dem Repository aktualisieren
- Kehren Sie zu Ihrem BlueJ-Projekt zurück.
- Überprüfen Sie, ob BlueJ die Veränderungen erkennt, indem Sie in der Seitenleiste auf `Teamwork -> Status` klicken.
- Wenn alles korrekt abgelaufen ist, sollte bei `Kartenmanager_SuS.java` der Remote-Status *"needs update"* anzeigen.
- Aktualisieren Sie den Code, indem Sie in der Seitenleiste auf `Teamwork → Aktualisieren...` klicken und dann `Kartenmanager_SuS.java` zum Aktualisieren auswählen.
- Überprüfen Sie, ob die Bubble-Sort-Methode jetzt in Ihrem lokalen Projekt verfügbar ist.

### Schritt 6: Git-Begriffe definieren
- Wenn Sie dies noch nicht getan haben, leiten Sie passende Definitionen für **Git-Repository**, **Push-** bzw. **Pull-Anfragen** aus Ihrer aktuellen Arbeitserfahrung ab.
- Wenn Sie damit fertig sind und die Arbeitsphase noch nicht abgelaufen ist, erledigen Sie Zusatzaufgaben auf der Rückseite. Andernfalls kehren Sie zu Ihren analogen Arbeitsplätzen zurück.

### Schritt 7: Einen neuen Branch im Repository anlegen
- Nun ist es Zeit, unterschiedliche Sortiermethoden in einem Prototyp zusammenzustellen. Sie müssen zunächst eine separate Stelle in Ihrem Repository für andere Mitschüler:innen anlegen, wo diese Ihre Sortiermethode leicht finden können. 
- Gehen Sie dafür auf Ihr Github-Repository im Browser zurück und bearbeiten Sie `Kartenmanager_SuS.java` im Web-Editor so, dass nur Ihre eigentliche Sortiermethode (*Insert-*, *Selection-* oder *Mergesort*) dort verbleibt.
- Gehen Sie zu `Commit changes...` und wählen Sie unten statt `Commit directly to the main branch` die Option `Create a new branch for this commit...` und nennen diesen Branch "**sort**". Klicken Sie anschließend auf `Propose changes`.

### Schritt 8: Andere Repositories besuchen
- Sie müssen nun andere Sortiermethoden von Ihren Mitschülern sammeln, damit Ihr Kartenmanager-Prototyp jeweils ein Insertsort, ein Selectsort und ein Mergesort-Verfahren zusätzlich zum Bubblesort enthält.
- Klicken Sie dafür im Browser auf `Grundkurs-Informatik`, das rechts oben neben dem Namen Ihres Repositories stehen sollte. Dies ist unsere "Organisation" auf Github, die alle Repositories aus unserem Classroom enthält.
- Die Liste aller verfügbaren Repositories finden Sie unten oder unter dem Tab `Repository`. Besuchen Sie die Repositories mit den Sortierverfahren, die Ihnen noch fehlen. 
- Unten finden Sie Hinweise, bei wem welche Verfahren zu finden sind. Beachten Sie dabei, dass andere Repositories **ebenso wie Ihres pseudonymisiert** sind. Die Zuordnungstabelle von Namen und Pseudonymen finden Sie am Smart-Board.
    - _Emil, Silas, Simon_: **Merge-Sort**
    - _Zoe, Justus, Nico, Mats, Ago, Filip_: **Select-Sort**
    - _Dorian, Annabelle, Evan, Christian, Mohamad, Jannes_: **Insert-Sort**
- Nachdem Sie ein anderes Repository geöffnet haben, wechseln Sie den aktuellen Branch, indem Sie auf `main` klicken und aus der darunter erscheinenden Liste `sort` auswählen.
- Kopieren Sie die benötigte Methode aus `Kartenmanager_SuS.java` und fügen Sie sie an der entsprechenden Stelle in Ihrem BlueJ-Projekt ein. Speichern Sie Ihre Änderungen jedes Mal durch `Commit/Push`.

### Schritt 9: Eine Release im Repository erstellen
- Fügen Sie zu Ihrem BlueJ-Projekt entsprechende GUI-Elemente zum Auswählen von Sortiermethoden hinzu. Vergessen Sie nicht, die Änderungen regelmäßig durch Push-Anfragen an Ihr Github-Repository zu übertragen, damit die Versionsgeschichte später nachvollziehbarer ist.
- Nachdem Sie einen funktionierenden Prototyp in BlueJ erstellt haben, ist es Zeit, ihn zu veröffentlichen. Gehen Sie dafür zurück zu Ihrem Repository im Browser und klicken Sie auf `Create a new release` im Abschnitt Releases auf der rechten Seite.
- Überlegen Sie sich einen Titel, Tags und eine kurze Beschreibung für Ihren Prototyp. Danach können Sie auf `Publish release` klicken und den Prototyp damit veröffentlichen.
