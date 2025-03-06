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
    double[] BubbleSave;
    double[] MergeSave;
    double[] InsertSave;
    int stepos;

    // Konstruktor
    public Grafik()
    {
        pen = new Buntstift();
        bildschirm().setSize(2000, 2000);
        reload = new Knopf(20,20,100,30,"Reload");
        reload2 = new Knopf(20,60,100,30,"Reload2");
        reload.setzeBearbeiterGeklickt("Reload_Ohne_Messergebnisse");
        reload2.setzeBearbeiterGeklickt("Reload_Mit_Messergebnisse");

        BubbleSave = new double[1200];
        MergeSave = new double[1200];
        InsertSave = new double[1200];

        BubbleSave[0] = 0;
        MergeSave[0] = 0;
        InsertSave[0] = 0;
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

    public void do_Test(int x, double y, int algo, int steps)
    {
        stepos = steps;
        switch(algo)
        {
                case(1):
                BubbleSave[x] = (BubbleSave[x]+y)/2;
                pen.setzeFarbe(0);
                pen.bewegeBis(100+(x-stepos),-BubbleSave[x-stepos]+810);
                pen.runter();
                pen.bewegeBis(100+x,-BubbleSave[x]+810);
                pen.hoch();
                break;
                case(2):
                InsertSave[x] = (InsertSave[x]+y)/2;
                pen.setzeFarbe(1);
                pen.bewegeBis(100+(x-stepos),-InsertSave[x-stepos]+810);
                pen.runter();
                pen.bewegeBis(100+x,-InsertSave[x]+810);
                pen.hoch();
                break;
                case(3):
                MergeSave[x] = (MergeSave[x]+y)/2;
                pen.setzeFarbe(2);
                pen.bewegeBis(100+(x-stepos),-MergeSave[x-stepos]+810);
                pen.runter();
                pen.bewegeBis(100+x,-MergeSave[x]+810);
                pen.hoch();
                break;
        }

    }

    public void Do(int x,double y, int algo,int steps)
    {
        stepos = steps;
        switch(algo)
        {
                case(1):
                BubbleSave[x] = y;
                pen.setzeFarbe(0);
                pen.bewegeBis(100+(x-stepos),-BubbleSave[x-stepos]+810);
                pen.runter();
                pen.bewegeBis(100+x,-y+810);
                pen.hoch();
                break;
                case(2):
                InsertSave[x] = y;
                pen.setzeFarbe(1);
                pen.bewegeBis(100+(x-stepos),-InsertSave[x-stepos]+810);
                pen.runter();
                pen.bewegeBis(100+x,-y+810);
                pen.hoch();
                break;
                case(3):
                MergeSave[x] = y;
                pen.setzeFarbe(2);
                pen.bewegeBis(100+(x-stepos),-MergeSave[x-stepos]+810);
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
            if(BubbleSave[i] != 0)Do(i,BubbleSave[i],1,stepos);
        }

        for(int i = 1; i < 1200; i++)
        {
            if(InsertSave[i] != 0)Do(i,InsertSave[i],2,stepos);
        }

        for(int i = 1; i < 1200; i++)
        {
            if(MergeSave[i] != 0)Do(i,MergeSave[i],3,stepos);
        }
    }

    public void Reload_Mit_Messergebnisse()
    {
        Reload_Ohne_Messergebnisse();
        run();
    }
}
