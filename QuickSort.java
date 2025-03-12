
public class QuickSort{

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

    private void quickSortListe(Liste list, int low, int high) {
        // Überprüfen, ob der zu sortierende Bereich mehr als ein Element enthält
        if (low < high) {
            // Teile die Liste in zwei Teile auf und finde den Pivot-Punkt
            int p = partition(list, low, high);

            // Sortiere den linken Teil der Liste rekursiv
            quickSortListe(list, low, p - 1);

            // Sortiere den rechten Teil der Liste rekursiv
            quickSortListe(list, p + 1, high);
        }
    }

    private int partition(Liste list, int low, int high) {
        // Wähle das letzte Element als Pivot-Element
        int pivot = high;

        // Initialisiere den Index des kleineren Elements
        int i = low - 1;

        // Durchlaufe die Liste von low bis high-1
        for (int j = low; j < high; j++) {
            // Wenn das aktuelle Element kleiner oder gleich dem Pivot-Element ist
            if (vergleich(list, j, pivot) <= 0) {
                // Erhöhe den Index des kleineren Elements
                i++;

                // Vertausche das aktuelle Element mit dem Element an Position i
                swap(list, i, j);
            }
        }
        // Vertausche das Pivot-Element mit dem Element an Position i+1
        swap(list, i + 1, high);

        // Gib den Index des Pivot-Elements zurück
        return i + 1;
    }

    private int vergleich(Liste list, int posA, int posB) {
        // Gehe zur Position posA in der Liste
        list.geheZuPosition(posA);

        // Hole die Karte an Position posA
        Karte ka = list.aktuellesElement();

        // Gehe zur Position posB in der Liste
        list.geheZuPosition(posB);

        // Hole die Karte an Position posB
        Karte kb = list.aktuellesElement();

        // Vergleiche die Werte der Karten
        if (ka.wert != kb.wert) {
            return ka.wert - kb.wert;
        }

        // Wenn die Werte gleich sind, vergleiche die Farben der Karten
        return ka.farbe - kb.farbe;
    }

    private void swap(Liste list, int posA, int posB) {
        // Wenn die Positionen gleich sind, tue nichts
        if (posA == posB) {
            return;
        }
        // Gehe zur Position posA in der Liste
        list.geheZuPosition(posA);

        // Hole die Karte an Position posA
        Karte ka = list.aktuellesElement();

        // Gehe zur Position posB in der Liste
        list.geheZuPosition(posB);

        // Hole die Karte an Position posB
        Karte kb = list.aktuellesElement();

        // Ersetze das Element an Position posB durch das Element an Position posA
        list.ersetzeAktuelles(ka);

        // Gehe zur Position posA in der Liste
        list.geheZuPosition(posA);

        // Ersetze das Element an Position posA durch das Element an Position posB
        list.ersetzeAktuelles(kb);
    }
}