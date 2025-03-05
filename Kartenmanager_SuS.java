import sum.ereignis.*;
import sum.komponenten.*;
import sum.werkzeuge.*;
import sum.strukturen.*;

/**
 * Kartenmanager_SuS:
 * Verwendung einer Liste<Karte> (aus sum.strukturen.Liste) 
 * anstelle eines Arrays. 
 * Die GUI wurde beibehalten (Stapelgr., Wert, Farbe, Position usw.).
 * - Einfügen/Entfernen mit Liste
 * - Sortieren/Erneuern (Platzhalter) 
 * - Zeichnen ab y=150
 */
public class Kartenmanager_SuS extends Ereignisanwendung {

    // -----------------------------
    // 1) GUI-Objekte
    // -----------------------------
    private Knopf startKnopf;   // "Sortieren"
    private Knopf updateKnopf;  // "Erneuern"
    private Knopf einfKnopf;    // "Einfügen"
    private Knopf entfKnopf;    // "Entfernen"
    private Knopf WARNUNG;

    private Etikett infoEtikett;    
    private Etikett labelStapelsize; 
    private Etikett labelWert;  
    private Etikett labelFarbe; 
    private Etikett labelPos;   

    private Auswahl auswahlWert;   
    private Auswahl auswahlFarbe;
    private Auswahl auswahlSort;

    private Textfeld tfUmfang;  // neue Stapelgröße
    private Textfeld tfPos;     // Position für Einfügen/Entfernen
    private Textfeld tfDurchgänge;

    private Buntstift stift;

    Grafik Grafik;

    // -----------------------------
    // 2) Listen-Datenstruktur
    // -----------------------------
    // Karte => Hilfsklasse für (wert, farbe)
    private class Karte {
        int wert;   // 1..13 => "2..Ass"
        int farbe;  // 0..3  => "Kreuz..Pik"

        public Karte(int w, int f) {
            this.wert = w;
            this.farbe= f;
        }
    }

    // Unsere Liste<Karte> anstelle von int[][]
    private Liste<Karte> karten;
    private int kartenAnzahl = 52;  // "logische" Stapelgröße

    // Namen für Werte und Farben (Index 1..13 => "2..Ass")
    private String[] werteNamen = {
            "", "2", "3", "4", "5", "6", 
            "7", "8", "9", "10", "Bube", "Dame", "König", "Ass"
        };
    private String[] farbenNamen = {
            "Kreuz", "Karo", "Herz", "Pik"
        };

    // Zeitmessung
    private long startZeit;
    private long endZeit;

    public Kartenmanager_SuS() {
        super();

        stift = new Buntstift();

        // 3) GUI: 
        //    - InfoEtikett + Stapelgröße/Erneuern/Sortieren (links)
        //    - Wert/Farbe/Position + Einfügen/Entfernen (rechts)
        //    - Karten ab y=150

        infoEtikett    = new Etikett( 50,  20, 400, 30, 
            "Karten mit Liste (Einfügen/Entfernen).");

        labelStapelsize= new Etikett( 50,  60, 80, 25, "Stapelgr.:");
        tfUmfang       = new Textfeld(130, 60, 60, 25, "52"); // Standard=52
        updateKnopf    = new Knopf(200, 60, 100, 30, "Erneuern");
        startKnopf     = new Knopf(310, 60, 100, 30, "Sortieren");
        tfDurchgänge   = new Textfeld(310, 100, 100, 30, "1000");
        auswahlSort    = new Auswahl(310, 20, 100, 30);

        auswahlSort.haengeAn("Bubblesort");
        auswahlSort.haengeAn("Instertsort");
        auswahlSort.haengeAn("Mergesort");

        // Wert/Farbe/Position 
        labelWert   = new Etikett(450,  20, 40, 25, "Wert:");
        auswahlWert = new Auswahl(500, 20, 80, 25);

        labelFarbe  = new Etikett(600, 20, 50, 25, "Farbe:");
        auswahlFarbe= new Auswahl(660,20, 80, 25);

        labelPos    = new Etikett(450, 60, 60, 25, "Position:");
        tfPos       = new Textfeld(520, 60, 60, 25, "0");

        einfKnopf   = new Knopf(600, 60, 100, 30, "Einfügen");
        entfKnopf   = new Knopf(710, 60, 100, 30, "Entfernen");

        WARNUNG     = new Knopf(820, 20, 200, 70,"NICHT DRÜCKEN!!!!!!!!");

        // Auswahllisten befüllen: Index 0..12 => "2..Ass"
        for(int w=1; w<=13; w++){
            auswahlWert.haengeAn(werteNamen[w]);
        }
        // Index 0..3 => "Kreuz..Pik"
        for(int f=0; f<4; f++){
            auswahlFarbe.haengeAn(farbenNamen[f]);
        }

        // 4) Ereignismethoden
        startKnopf.setzeBearbeiterGeklickt("sort");
        updateKnopf.setzeBearbeiterGeklickt("Update_Klick");
        einfKnopf.setzeBearbeiterGeklickt("Einfuegen_Klick");
        entfKnopf.setzeBearbeiterGeklickt("Entfernen_Klick");
        WARNUNG.setzeBearbeiterGeklickt("GrafikSort");

        // 5) Listen-Datenstruktur anlegen
        karten = new Liste<Karte>(); 
        // => "kartenAnzahl" als "logische" Größe, 
        //    wir initialisieren so viele Einträge

        initialisiereKarten(0); // füllt die Liste mit 52 Karten

        // 6) Zeichnen ab y=150
        zeichneKarten(0, 50, 150);

        this.fuehreAus();
    }

    // ----------------------------------------------------------
    // Initialisieren & Zeichnen
    // ----------------------------------------------------------

    /**
     * initialisiereKarten(i):
     *  Legt kartenAnzahl Karten an.
     *  Jede Karte zufällig => wert in [1..13], farbe in [0..3]
     */
    void initialisiereKarten(int i) {
        if(i>=kartenAnzahl) return;

        // Erzeuge eine neue Karte
        int w = (int)(Math.random()*13)+1;
        int f = (int)(Math.random()*4);

        // Zur Sicherheit: Erst zumEnde(), dann fuegeDahinterEin
        karten.zumEnde();
        karten.fuegeDahinterEin(new Karte(w, f));
        //System.out.println("Pos " + karten.aktuellePosition() + ": "+ (karten.aktuellesElement() != null ? karten.aktuellesElement().wert : "null"));

        initialisiereKarten(i+1);
    }

    /**
     * zeichneKarten(index, x, y):
     *  Durchläuft die Liste per gehZuPosition(index),
     *  holt das aktuelleElement() => (wert, farbe),
     *  zeichnet die Karte.
     */
    void zeichneKarten(int index, int x, int y) {
        if(index >= karten.laenge())return; 

        // Zur Position index gehen
        karten.geheZuPosition(index+1);
        Karte c = karten.aktuellesElement(); // => (wert, farbe)

        String name = farbenNamen[c.farbe] + " " + werteNamen[c.wert];
        zeichneEineKarte(x, y, name);

        x += 90;
        if ((index+1)%13 == 0) {
            x = 50;
            y += 130;
        }
        zeichneKarten(index+1,x,y);
    }

    void zeichneEineKarte(int x, int y, String name) {
        stift.bewegeBis(x,y);
        stift.zeichneRechteck(80,120);
        stift.bewegeBis(x+5, y+50);
        stift.schreibeText(name);
    }

    /**
     * Vorgabe:
     * stift.radiere();
     * zeichneKarten(0,50,150);
     * stift.normal();
     */
    void loescheAnzeige() {
        stift.radiere();
        zeichneKarten(0, 50, 150);
        stift.normal();
    }

    public void GrafikSort()
    {
        Grafik = new Grafik();
        double d = 0;
        double startZeit;
        double endZeit;
        int durchläufe = 100;
        int steps = 2;
        for(int i = steps; i < 1199; i = i+steps)
        {
            startZeit = System.nanoTime();
            kartenAnzahl = i;
            for(int j = 0; j < durchläufe; j++)
            {
                karten = new Liste<Karte>(); 
                initialisiereKarten(0);
                Blasensortierung();
            }
            endZeit = System.nanoTime();
            d =(endZeit - startZeit);
            d = d/1000000;
            double avg = d/durchläufe;            
            if(avg < 4)Grafik.Do(i,avg*200,1,steps);
            if(avg > 4)i = 1199;
        }
        
        for(int i = steps; i < 1199; i = i+steps)
        {
            startZeit = System.nanoTime();
            kartenAnzahl = i;
            for(int j = 0; j < durchläufe; j++)
            {
                karten = new Liste<Karte>(); 
                initialisiereKarten(0);
                InsertSort();
            }
            endZeit = System.nanoTime();
            d =(endZeit - startZeit);
            d = d/1000000;
            double avg = d/durchläufe;            
            Grafik.Do(i,avg*200,2,steps);
            if(avg > 4)i = 1199;
        }
        
        for(int i = steps; i < 1199; i = i+steps)
        {
            startZeit = System.nanoTime();
            kartenAnzahl = i;
            for(int j = 0; j < durchläufe; j++)
            {
                karten = new Liste<Karte>(); 
                initialisiereKarten(0);
                mergesort();
            }
            endZeit = System.nanoTime();
            d =(endZeit - startZeit);
            d = d/1000000;
            double avg = d/durchläufe;            
            Grafik.Do(i,avg*200,3,steps);
            if(avg > 4)i = 1199;
        }
    }

    void sort() {
        loescheAnzeige();
        double d = 0;
        startZeit = System.nanoTime();
        int durchgänge = tfDurchgänge.inhaltAlsGanzeZahl();
        for(int i = 0; i < durchgänge; i++)
        {
            karten = new Liste<Karte>(); 
            initialisiereKarten(0);
            switch(auswahlSort.index())
            {
                case(1): Blasensortierung(); break;
                case(2): InsertSort(); break;
                case(3): mergesort(); break;
            }
        }
        endZeit = System.nanoTime();
        d =(endZeit - startZeit);
        d = d/1000000;
        double avg = d/durchgänge;
        infoEtikett.setzeInhalt("Sortiert in " + d + " ms. Avg: "+avg);
        zeichneKarten(0, 50, 150);
    }

    public void Blasensortierung() {
        boolean sorted = false;
        do {
            sorted = true;
            karten.zumAnfang();
            for (int i = 1; i < karten.laenge(); i++) {                
                Karte Karte_a = karten.aktuellesElement();
                karten.geheZuPosition(karten.aktuellePosition()+1);
                Karte Karte_b = karten.aktuellesElement();
                if (Karte_a.wert > Karte_b.wert||Karte_a.wert == Karte_b.wert && Karte_a.farbe < Karte_b.farbe){
                    karten.ersetzeAktuelles(Karte_a);
                    karten.geheZuPosition(karten.aktuellePosition()-1);
                    karten.ersetzeAktuelles(Karte_b);
                    sorted = false;
                }
            }
        } while (!sorted);

    }

    void mergesort() {
        int[][] KartenArray = new int[karten.laenge()][2]; // falls karten eine eigene Liste ist, dann karten.laenge()
        Karte a;

        for (int i = 0; i < KartenArray.length; i++) { 
            karten.geheZuPosition(i + 1);
            a = karten.aktuellesElement();
            KartenArray[i][0] = a.wert;
            KartenArray[i][1] = a.farbe;
        }

        KartenArray = split(KartenArray, KartenArray.length);

        karten = new Liste<Karte>(); 

        for (int i = 0; i < KartenArray.length; i++) {
            int w = KartenArray[i][0];
            int f = KartenArray[i][1];
            karten.zumEnde();
            karten.fuegeDahinterEin(new Karte(w, f));
        }
    }

    int[][] split(int[][] arr, int length) {
        if (length <= 1) return arr;

        int mitte = length / 2;
        int[][] l = new int[mitte][2];
        int[][] r = new int[length - mitte][2];

        for (int i = 0; i < length; i++) {
            if (i < mitte) l[i] = arr[i];
            else r[i - mitte] = arr[i];
        }

        l = split(l, l.length);
        r = split(r, r.length);

        return merge(l, r);
    }

    int[][] merge(int[][] arr1, int[][] arr2) {        
        int totalLength = arr1.length + arr2.length;
        int[][] merged = new int[totalLength][2];

        int i = 0, j = 0, k = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i][0] < arr2[j][0] || (arr1[i][0] == arr2[j][0] && arr1[i][1] < arr2[j][1])) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }

        while (i < arr1.length) merged[k++] = arr1[i++];
        while (j < arr2.length) merged[k++] = arr2[j++];

        return merged;
    }

    // QuickSort-Methode
    void quickSort(int low, int high) {
        if (low < high) {
            // Pivot-Element wählen
            int pivotIndex = partition(low, high);

            // Rekursive Aufrufe für die beiden Hälften
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }

    // Partition-Methode: Teilt das Array in zwei Hälften
    int partition(int low, int high) {
        int pivot = high;

        int i = low - 1;  

        for (int j = low; j < high; j++) {
            // Wenn das aktuelle Element kleiner als das Pivot ist

            if (vergleich(j, pivot) <= 0) {
                i++;
                // Tausche die Elemente
                swap(i,j);
            }
        }

        // Tausche das Pivot-Element an die richtige Stelle
        swap(i + 1,high);
        return i + 1;
    }

    // Vergleichsmethode: Vergleicht zwei Karten basierend auf Wert und Farbe
    int vergleich(int a, int b) {
        karten.geheZuPosition(a);
        Karte karte1 = karten.aktuellesElement();
        karten.geheZuPosition(b);
        Karte karte2 = karten.aktuellesElement();
        if (karte1.wert != karte2.wert) {
            return karte1.wert - karte2.wert; // Vergleiche die Werte
        } else {
            return karte1.farbe - karte2.farbe; // Wenn Werte gleich sind, vergleiche die Farben
        }
    }

    public void swap(int a, int b){
        karten.geheZuPosition(a);
        Karte Karte_a = karten.aktuellesElement();
        karten.geheZuPosition(b);
        Karte Karte_b = karten.aktuellesElement();
        karten.ersetzeAktuelles(Karte_a);
        karten.geheZuPosition(a);
        karten.ersetzeAktuelles(Karte_b);
    }

    public void InsertSort()
    {
        for(int i = 2; i <= karten.laenge(); i++)
        {
            karten.geheZuPosition(i);
            Karte a = karten.aktuellesElement();
            karten.loescheAktuelles();
            boolean eingefuegt = false;
            int j = i-1;
            while(j >= 1 && !eingefuegt)
            {
                karten.geheZuPosition(j);
                Karte b = karten.aktuellesElement();
                if(a.wert < b.wert || a.wert == b.wert && a.farbe < b.wert)
                {
                    j--;
                }else
                {
                    eingefuegt = true;

                }

            }
            karten.geheZuPosition(j+1);
            karten.fuegeDavorEin(a);
        }
    }

    public void Update_Klick() {
        String s = tfUmfang.inhaltAlsText().trim();
        int neuAnz;
        try {
            neuAnz = Integer.parseInt(s);
            if(neuAnz<=0) neuAnz=52;
        } catch(NumberFormatException ex) {
            neuAnz=52;
        }
        loescheAnzeige();

        // Alte Liste verwerfen => Neue Liste
        karten = new Liste<Karte>();
        kartenAnzahl= neuAnz;

        initialisiereKarten(0);
        zeichneKarten(0, 50, 150);

        infoEtikett.setzeInhalt("Neuer Stapel: "+kartenAnzahl+" Karten (Liste).");
    }

    public void Einfuegen_Klick() {
        try{
            loescheAnzeige();
            karten.geheZuPosition(tfPos.inhaltAlsGanzeZahl());
            karten.fuegeDavorEin(new Karte(auswahlWert.index(),auswahlFarbe.index()-1));
            tfPos.setzeInhalt((int)(Math.random()*karten.laenge()));
            zeichneKarten(0, 50, 150);
        }catch(ArithmeticException inhaltAlsGanzeZahl){
            infoEtikett.setzeInhalt("MACH RICHTIG!");
            zeichneKarten(0, 50, 150);
            tfPos.setzeInhalt((int)(Math.random()*karten.laenge()));
        }
    }

    public void Entfernen_Klick() {
        try{
            loescheAnzeige();
            karten.geheZuPosition(tfPos.inhaltAlsGanzeZahl()+1);
            karten.entferneAktuelles();
            zeichneKarten(0, 50, 150);
        }catch(ArithmeticException inhaltAlsGanzeZahl){
            infoEtikett.setzeInhalt("MACH RICHTIG!");
            zeichneKarten(0, 50, 150);
            tfPos.setzeInhalt((int)(Math.random()*karten.laenge()));
        }
    }
}
