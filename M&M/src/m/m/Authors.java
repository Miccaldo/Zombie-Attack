/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

import java.awt.Color;
import java.io.File;
import javax.swing.JLabel;

/**
 *
 * @author Dell
 */
public class Authors extends MiniPanel{
    
    public JLabel lText;
    
     @Override
    public void add(MM mm){
        mm.add(lText);
        super.add(mm);
    }
    
    @Override
    public void visibility(boolean value){
        super.visibility(value);
        lText.setVisible(value);
    }
    
    public Authors(int x, int y){
        super(x,y);
        
        width = 300;
        height = 150;
        file = new File("resources/img/background/backgroundPanel.png");
        image = scaleImage(file, width, height);
        lText = new JLabel("Michal Janowski");
        lText.setForeground(new Color(180,180,255));
        lText.setBounds(x + 40,y - 25,width,height); 
        lText.setFont(getFont(18));
        
        submit.setFont(getFont(15));
        submit.setBounds(x + 100,y + 90,80,20);
        
        lGrid.setBounds(x,y,width,height);
        lGrid.setIcon(image); 
       
        
        visibility(false);
    }
}
