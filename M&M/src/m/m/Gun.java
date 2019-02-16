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
public class Gun extends Basic {
     
    @Override
    public void setPosition(int x, int y){
        super.setPosition(x, y);
        lGrid.setBounds(x - 45, y - 65, width, height);
    }
    
    public Gun(int x, int y){
        super(x,y);
        
        image = new ImageIcon("resources/img/crosshair/crosshair.png");
        lGrid.setIcon(image);
        lGrid.setDoubleBuffered(true);
        width = 80;
        height = 80;
    }
}
