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
    double[][] BubbleSave;
    double[][] MergeSave;
    double[][] InsertSave;
    int stepos;

    // Konstruktor
    public Grafik()
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

        BubbleSave = new double[1200][1200];
        MergeSave = new double[1200][1200];
        InsertSave = new double[1200][1200];

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

    public void do_Test(int x, double y, int algo, int steps,int current,int speed)
    {
        stepos = steps;
        switch(algo)
        {
                case(1):
                BubbleSave = Average(x,stepos,y,1,current,speed);
                pen.setzeFarbe(0);
                pen.bewegeBis(100+(x-stepos),-BubbleSave[x-stepos][0]+810);
                pen.runter();
                pen.bewegeBis(100+x,-BubbleSave[x][0]+810);
                pen.hoch();
                break;
                case(2):

                pen.setzeFarbe(1);
                InsertSave = Average(x,stepos,y,2,current,speed);
                pen.bewegeBis(100+(x-stepos),-InsertSave[x-stepos][0]+810);
                pen.runter();
                pen.bewegeBis(100+x,-InsertSave[x][0]+810);
                pen.hoch();
                break;
                case(3):

                pen.setzeFarbe(7);
                MergeSave = Average(x,stepos,y,3,current,speed);
                pen.bewegeBis(100+(x-stepos),-MergeSave[x-stepos][0]+810);
                pen.runter();
                pen.bewegeBis(100+x,-MergeSave[x][0]+810);
                pen.hoch();
                break;
        }

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

    public void Do(int x,double y, int algo,int steps)
    {
        stepos = steps;
        switch(algo)
        {
                case(1):
                BubbleSave[x][0] = y;
                pen.setzeFarbe(0);
                pen.bewegeBis(100+(x-stepos),-BubbleSave[x-stepos][0]+810);
                pen.runter();
                pen.bewegeBis(100+x,-y+810);
                pen.hoch();
                break;
                case(2):
                InsertSave[x][0] = y;
                pen.setzeFarbe(1);
                pen.bewegeBis(100+(x-stepos),-InsertSave[x-stepos][0]+810);
                pen.runter();
                pen.bewegeBis(100+x,-y+810);
                pen.hoch();
                break;
                case(3):
                MergeSave[x][0] = y;
                pen.setzeFarbe(2);
                pen.bewegeBis(100+(x-stepos),-MergeSave[x-stepos][0]+810);
                pen.runter();
                pen.bewegeBis(100+x,-y+810);
                pen.hoch();
                break;
        }

    }

    public void run()
    {
        for(int i = 1; i < 1200; i++)
        {
            if(BubbleSave[i][0] != 0)Do(i,BubbleSave[i][0],1,stepos);
        }

        for(int i = 1; i < 1200; i++)
        {
            if(InsertSave[i][0] != 0)Do(i,InsertSave[i][0],2,stepos);
        }

        for(int i = 1; i < 1200; i++)
        {
            if(MergeSave[i][0] != 0)Do(i,MergeSave[i][0],3,stepos);
        }
    }

    public void Reload_Mit_Messergebnisse()
    {
        Reload_Ohne_Messergebnisse();
        run();
    }

    public void Write_Array()
    {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("Bubblesort.txt"));
            writer.write(Integer.toString(stepos));
            writer.newLine();
            for(int i = 0; i < BubbleSave.length; i += stepos)
            {                
                writer.write(Integer.toString(i));
                writer.newLine();
                writer.write(Double.toString(BubbleSave[i][0]));
                writer.newLine();   
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("Insertsort.txt"));
            writer.write(Integer.toString(stepos));
            writer.newLine();
            for(int i = 0; i < InsertSave.length; i += stepos)
            {                
                writer.write(Integer.toString(i));
                writer.newLine();
                writer.write(Double.toString(InsertSave[i][0]));               
                writer.newLine();   
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("Mergesort.txt"));
            writer.write(Integer.toString(stepos));
            writer.newLine();
            for(int i = 0; i < MergeSave.length; i += stepos)
            {                
                writer.write(Integer.toString(i));
                writer.newLine();
                writer.write(Double.toString(MergeSave[i][0]));
                writer.newLine();   
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void Read_Array() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Bubblesort.txt"));

            stepos = Integer.parseInt(reader.readLine());

            // Initialisiere BubbleSave mit einer passenden Größe (Beispielwerte!)
            BubbleSave = new double[1200][1200]; // Falls nötig, Größe anpassen
            String line;

            while ((line = reader.readLine()) != null)
            {
                int x = Integer.parseInt(line);
                BubbleSave[x][0] = Double.parseDouble(reader.readLine());
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Fehler beim Parsen einer Zahl.");
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader("InsertSort.txt"));

            stepos = Integer.parseInt(reader.readLine());

            // Initialisiere BubbleSave mit einer passenden Größe (Beispielwerte!)
            InsertSave = new double[1200][1200]; // Falls nötig, Größe anpassen
            String line;

            while ((line = reader.readLine()) != null)
            {
                int x = Integer.parseInt(line);
                InsertSave[x][0] = Double.parseDouble(reader.readLine());
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Fehler beim Parsen einer Zahl.");
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader("MergeSort.txt"));

            stepos = Integer.parseInt(reader.readLine());

            // Initialisiere BubbleSave mit einer passenden Größe (Beispielwerte!)
            MergeSave = new double[1200][1200]; // Falls nötig, Größe anpassen
            String line;

            while ((line = reader.readLine()) != null)
            {
                int x = Integer.parseInt(line);
                MergeSave[x][0] = Double.parseDouble(reader.readLine());
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Fehler beim Parsen einer Zahl.");
            e.printStackTrace();
        }
    }
}



