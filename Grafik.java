// HAllo neu lol 
// SIlas ??
import sum.ereignis.*;
import sum.komponenten.*;
import sum.werkzeuge.*;
import sum.strukturen.*;
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
    double[][] BubbleSave;
    double[][] MergeSave;
    double[][] InsertSave;
    int stepos;

    // Konstruktor
    public Grafik()
    {
        pen = new Buntstift();
        bildschirm().setSize(2000, 1000);
        reload = new Knopf(20,20,100,30,"Reload");
        reload2 = new Knopf(20,60,100,30,"Reload2");
        reload.setzeBearbeiterGeklickt("Reload_Ohne_Messergebnisse");
        reload2.setzeBearbeiterGeklickt("Reload_Mit_Messergebnisse");

        BubbleSave = new double[1200][1200];
        MergeSave = new double[1200][1200];
        InsertSave = new double[1200][1200];

        BubbleSave[0][0] = 0;
        MergeSave[0][0] = 0;
        InsertSave[0][0] = 0;
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

                pen.setzeFarbe(2);
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
}
