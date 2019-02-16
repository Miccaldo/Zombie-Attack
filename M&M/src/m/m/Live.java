/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author HP
 */
public class Live extends Basic {
    
    public Live(int x, int y){
        super(x,y);
        
        width = 80;
        height = 80; 
        
        image = scaleImage(new File("resources/img/live/live.png"), width, height);  
        lGrid.setBounds(x, y, width, height);
        lGrid.setIcon(image);      
    }
}
