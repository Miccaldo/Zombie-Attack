/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

import javax.swing.ImageIcon;

/**
 *
 * @author HP
 */
public class RunningZombie extends WalkZombie{
         
    @Override
    protected void change(){        // DODANA ZMIANA RUCHU W OSI Y
        changeDiraction();
        super.change();
    }
    
    public RunningZombie(int x, int y, int width, int height, MM mm){
        super(x, y, width, height, mm);
        
        imageR = new ImageIcon("resources/img/zombie/R1.gif");
        imageL = new ImageIcon("resources/img/zombie/L1.gif");
        
        changeDiraction();
             
        lGrid.setDoubleBuffered(true);
        lGrid.setBounds(x, y, width, height);
        
        shiftBoneX = 100;
        shiftBoneY = 0;
        score = 50;
        step = 10;
        walkCnt = 5;
        
        rightX = 800;
        leftX = 0;
    }
}
