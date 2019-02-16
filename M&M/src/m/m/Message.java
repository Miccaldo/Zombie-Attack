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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author HP
 */
public class Message extends MiniPanel{
       
    public JLabel lText;
    public JLabel lScore;
    private JTextField textBox = new JTextField();
    
    
    public String getUserName(){      
        return textBox.getText();
    }
    
    public void setScore(int value){
        lScore.setText("Wynik: "+value);
    }
    
    @Override
    public void visibility(boolean value){
        super.visibility(value);
        lText.setVisible(value);
        lScore.setVisible(value);
        textBox.setVisible(value);
    }
    
    @Override
    public void add(MM mm){
        mm.add(lText);
        mm.add(lScore);
        mm.add(textBox);
        super.add(mm);
    }
    
    public Message(int x, int y){
        
        super(x,y);
        
        width = 350;
        height = 240;
        image = scaleImage(file, width, height);
        
        lScore = new JLabel("Wynik: 0");
        lScore.setFont(getFont(25));
        lScore.setForeground(new Color(180,180,255));
        lScore.setBounds(x + 45,y - 0,width,height); 
        
        lText = new JLabel("Gracz:");
        lText.setFont(getFont(30));
        lText.setForeground(new Color(180,180,255));
        lText.setBounds(x + 43,y - 50,width,height); 
        
        
        textBox.setBounds(x + 150,y + 55,150,35); 
        textBox.setFont(getFont(20));
        
        submit.setFont(getFont(18));
        submit.setBounds(x + 100,y + 145,150,40);
              
        lGrid.setBounds(x,y,width,height);
        lGrid.setIcon(image);   
        
        visibility(false);
    }
}
