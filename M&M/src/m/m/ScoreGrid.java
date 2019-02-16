/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author HP
 */
public class ScoreGrid extends MiniPanel {
       
    private JLabel lScore = new JLabel();
    private JScrollPane scroll = new JScrollPane(lScore);
    
    private final int widthScroll = 360;
    private final int heightScroll = 170;
    
    public void set(){
        Score score = new Score();
        lScore.setText(score.Get());
    }
    
    @Override
    public void visibility(boolean value){
        super.visibility(value);
        scroll.setVisible(value);
    }
    
    @Override
    public void add(MM mm){
        mm.add(scroll);
        super.add(mm);
    }
    
    public ScoreGrid(int x, int y){
        super(x,y);
        
        width = 540;
        height = 390;
        
        image = scaleImage(file, width, height);
        lGrid.setIcon(image);
        
        submit.setBounds(x+190,y+259,widthButton,heightButton);
        lGrid.setBounds(x,y,width,height);
        scroll.setBounds(x+87,y+80,widthScroll,heightScroll);
        
        lScore.setBackground(new Color(255,255,150));
        lScore.setForeground(new Color(180,180,255));
        lScore.setOpaque(true);
        lScore.setFont(getFont(30));  
        lScore.setVerticalAlignment(JLabel.TOP);
        
        visibility(false);
    }
}
