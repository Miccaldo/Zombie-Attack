/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author HP
 */
public class WalkZombie extends Zombie {
    
    private int downY = 480;
    private int upY = 270;
    protected int step = 2;
    protected int stepY = 2;
    
    protected int leftX = 0;
    protected int rightX = 900;
    
    private Random diractionGenerator = new Random();
    private Random diractionXGenerator = new Random();
    
    protected int walkCnt;
    private boolean imageFlag = true;
    protected boolean diractionX;
    protected int diractionY;           // -1 - dol, 0 - rowno, 1 - gora
    
    protected ImageIcon imageR = new ImageIcon("resources/img/zombie/walkZombieR.gif");
    protected ImageIcon imageL = new ImageIcon("resources/img/zombie/walkZombieL.gif");
  
    
    protected void change(){
        int randomValue = diractionXGenerator.nextInt(100);

        if((randomValue <= 33) && (y >= upY)) diractionY = -1;
        else if((randomValue > 33) && (randomValue <= 66)) diractionY = 0;
        else if((randomValue) > 66 && (y <= downY)) diractionY = 1;
    }
    
    protected void changeDiraction(){                 // true - prawa strona
        int randomValue = diractionGenerator.nextInt(100);
        
        if(randomValue <= 50) setDiraction(true, imageL);
        else setDiraction(false, imageR);
    }
    
  public void walk(){           // REALIZUJE RUCH ZOMBIE
        
        walkCnt++;                      // ABY ZAPOBIEC NAGMINNEGO ZMIENIANIA RUCHU, JESLI LICZNIK 
                                        // DOLICZY DO 50, WTEDY MOZLIWA JEST DOPIERO ZMIANA
        if(walkCnt % 50 == 0) change();
              
        if(!diractionX)x += step;       // OGRANICZENIA W OSI X
        else x -= step;
        
        if((diractionY == -1) && (y >= upY)) y -= stepY;    // OGRANICZENIA W OSI Y
        else if(diractionY == 0) y -= 0;
        else if(((diractionY == 1) && (y <= downY)) || (y < downY)) y += stepY;
        
       
        if(x <= leftX) setDiraction(false, imageR);
        else if(x >= rightX) setDiraction(true, imageL);
        
        lGrid.setLocation(x, y);
  }
  
    protected void setDiraction(boolean value, ImageIcon image){        // ZMIENIA GRAFIKE ODPOWIEDNIA DO ZWROTU ZOMBIE
        this.image = image;                                             // ORAZ USTAWIA WLASCIWY STAN FLAGI
        diractionX = value;
        lGrid.setIcon(this.image);
    }
  
    public WalkZombie(int x, int y, int width, int height, MM mm){
        super(x, y, width, height, mm);
             
        changeDiraction();

        lGrid.setDoubleBuffered(true);
        lGrid.setBounds(x, y, width, height);
        
        shiftBoneX = 50;
        shiftBoneY = 0;
        score = 25;
    }
}
