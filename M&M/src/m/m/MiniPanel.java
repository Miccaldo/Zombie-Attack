/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author HP
 */
public abstract class MiniPanel extends Basic {
    
    public JButton submit = new JButton("OK");
    protected final int widthButton = 150;
    protected final int heightButton = 50;
    
    public void visibility(boolean value){
        
        lGrid.setVisible(value);
        submit.setVisible(value);
    }
    
    @Override
    public void add(MM mm){
        mm.add(submit);
        super.add(mm);
    }
    
    protected Font getFont(int size){
      
        Font font = null;
        try {
             font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/font/Anja_Eliane.ttf"))).deriveFont(Font.ITALIC, size);
        }catch(Exception ex){
             ex.printStackTrace();
        }
        return font;
    }
    
    public MiniPanel(int x, int y){
        super(x,y);
        
        file = new File("resources/img/background/backgroundPanel.png");
        
        submit.setForeground(new Color(80,80,80));
        submit.setFont(getFont(30));
    }
}
