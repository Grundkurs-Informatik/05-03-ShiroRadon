// HAllo neu lol 
// SIlas ??
import sum.ereignis.*;
import sum.komponenten.*;
import sum.werkzeuge.*;
import sum.strukturen.*;
import java.io.*;
/**
 * @author 
 * @version 
 */
public class Grafik extends Ereignisanwendung
{
    // Objekte
    Buntstift pen;
    Knopf reload;
    Knopf reload2;
    Knopf write;
    Knopf read;
    Etikett Info;
    double[][] BubbleSave;
    double[][] MergeSave;
    double[][] InsertSave;
    int stepos;

    // Konstruktor
    public Grafik(int durchläufe)
    {
        pen = new Buntstift();
        bildschirm().setSize(2000, 1000);
        reload = new Knopf(10,20,70,30,"Reload");
        reload2 = new Knopf(10,60,70,30,"Reload2");
        write = new Knopf(10,100,70,30,"Write");
        read = new Knopf (10,140, 70, 30,"Read");
        reload.setzeBearbeiterGeklickt("Reload_Ohne_Messergebnisse");
        reload2.setzeBearbeiterGeklickt("Reload_Mit_Messergebnisse");
        write.setzeBearbeiterGeklickt("Write_Array");
        read.setzeBearbeiterGeklickt("Read_Array");
        Info = new Etikett(1310,10,600,100,"Infos");

        BubbleSave = new double[1200][durchläufe+1];
        MergeSave = new double[1200][durchläufe+1];
        InsertSave = new double[1200][durchläufe+1];
        BubbleSave[0][0] = 0;
        MergeSave[0][0] = 0;
        InsertSave[0][0] = 0;

        Reload_Ohne_Messergebnisse();
    }

    public void Reload_Ohne_Messergebnisse()
    {             
        bildschirm().loescheAlles();
        pen.setzeFarbe(0);
        pen.bewegeBis(100,10);
        pen.zeichneRechteck(1200,800);
        pen.bewegeBis(90,820);
        pen.schreibeText("0");
        for (int i = 1; i <= 4; i++)
        {
            pen.bewegeBis(90,810-(200*i));
            pen.runter();
            pen.bewegeUm(20);
            pen.hoch();
            pen.bewegeUm(-20);
            pen.schreibeZahl(i);
        }
        for(int i = 1;i <= 12; i++)
        {
            pen.bewegeBis(100+(100*i),820);
            pen.runter();
            pen.dreheBis(90);
            pen.bewegeUm(20);
            pen.hoch();
            pen.bewegeUm(-20);
            pen.schreibeZahl(i*100);

        }
        pen.dreheBis(0);
    }

    public void Kurve_Zeichnen_Iterativ(int x, double y, int algo, int steps,int current,int speed)
    {
        stepos = steps;
        switch(algo)
        {
                case(1):               
                BubbleSave = Average(x,stepos,y,1,current,speed);
                Datenpunkt_Zeichnen(BubbleSave,x,0);
                break;
                case(2):

                InsertSave = Average(x,stepos,y,2,current,speed);
                Datenpunkt_Zeichnen(InsertSave,x,1);
                break;
                case(3):

                MergeSave = Average(x,stepos,y,3,current,speed);
                Datenpunkt_Zeichnen(MergeSave,x,7);
                break;
        }

    }

    public void Datenpunkt_Zeichnen(double[][] Array,int x,int Farbe)
    {
        pen.setzeFarbe(Farbe);
        pen.bewegeBis(100+(x-stepos),-Array[x-stepos][0]+810);
        pen.runter();
        pen.bewegeBis(100+x,-Array[x][0]+810);
        pen.hoch();
    }

    public double[][] Average(int x, int steps, double y, int algo, int current, int speed) {
        double[][] saveArray;

        // Wähle das richtige Array
        switch (algo) {
            case 1: saveArray = BubbleSave; break;
            case 2: saveArray = InsertSave; break;
            case 3: saveArray = MergeSave; break;
            default: return null; // Falls ein falscher Algorithmus übergeben wird
        }

        // Aktuellen Wert speichern
        saveArray[x][current] = y;
        int temp = 0;
        int count = 0;

        // Durchschnitt berechnen
        for (int i = 0; i <= current; i += speed) {
            temp += saveArray[x][i];
            count++;
        }

        if (count > 0) saveArray[x][0] = temp / count; // Durchschnitt speichern

        return saveArray; // Das aktualisierte Array zurückgeben
    }

    public void Kurve_Zeichnen(int x,double y, int algo,int steps)
    {
        stepos = steps;
        switch(algo)
        {
                case(1):
                BubbleSave[x][0] = y;
                Datenpunkt_Zeichnen(BubbleSave,x,0);
                break;
                case(2):
                InsertSave[x][0] = y;
                Datenpunkt_Zeichnen(InsertSave,x,1);
                break;
                case(3):
                MergeSave[x][0] = y;
                Datenpunkt_Zeichnen(MergeSave,x,7);
                break;
        }

    }
    
    public void Update_Infoetikett(double zeit, int Restliche_Durchläufe)
    {
        double Restzeit = zeit*Restliche_Durchläufe;
        double Stunden = Restzeit/3600000;
        Restzeit /= 60000;
        Restzeit *= -1;
        //Restzeit = Math.round(Restzeit);
        Info.setzeInhalt("Verbleibende Durchläufe: "+Restliche_Durchläufe + ". Verbleibende Zeit: " + Restzeit +"min");
    }

    public void run()
    {
        for(int i = 1; i < 1200; i++)
        {
            if(BubbleSave[i][0] != 0)Kurve_Zeichnen(i,BubbleSave[i][0],1,stepos);
        }

        for(int i = 1; i < 1200; i++)
        {
            if(InsertSave[i][0] != 0)Kurve_Zeichnen(i,InsertSave[i][0],2,stepos);
        }

        for(int i = 1; i < 1200; i++)
        {
            if(MergeSave[i][0] != 0)Kurve_Zeichnen(i,MergeSave[i][0],3,stepos);
        }
    }

    public void Reload_Mit_Messergebnisse()
    {
        Reload_Ohne_Messergebnisse();
        run();
    }

    public void Write_Array()
    {
        In_Datei_Schreiben(BubbleSave, "BubbleSort.txt");
        In_Datei_Schreiben(InsertSave, "InsertSort.txt");
        In_Datei_Schreiben(MergeSave, "MergeSort.txt");
    }

    public void In_Datei_Schreiben(double[][] Array,String Dateiname)
    {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(Dateiname));
            writer.write(Integer.toString(stepos));
            writer.newLine();
            for(int i = 0; i < Array.length; i += stepos)
            {                
                writer.write(Integer.toString(i));
                writer.newLine();
                writer.write(Double.toString(Array[i][0]));
                writer.newLine();   
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Read_Array() {
        BubbleSave = Aus_Datei_Ablesen("BubbleSort.txt");
        InsertSave = Aus_Datei_Ablesen("InsertSort.txt");
        MergeSave = Aus_Datei_Ablesen("MergeSort.txt");
    }

    public double[][] Aus_Datei_Ablesen(String Dateiname)
    {
        double[][] Array = new double[1200][1];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Dateiname));

            stepos = Integer.parseInt(reader.readLine());

            // Initialisiere BubbleSave mit einer passenden Größe (Beispielwerte!)
            String line;

            while ((line = reader.readLine()) != null)
            {
                int x = Integer.parseInt(line);
                Array[x][0] = Double.parseDouble(reader.readLine());
            }

            reader.close();
            return Array;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Fehler beim Parsen einer Zahl.");
            e.printStackTrace();
            return Array;
        }
        return Array;
    }
}





