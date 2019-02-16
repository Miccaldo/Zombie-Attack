/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author HP
 */
public abstract class Basic {
    
    protected ImageIcon image;
    protected File file;
    protected JLabel lGrid;
    protected int x, y;
    protected int width, height;
    protected int score;
    protected boolean scoreFlag;
    
    protected Player shot = new Player(new File("resources/sounds/shot.wav"));
   
    
    public int getScore(){
        scoreFlag = false;
        return score;
    }
    
    public void clean(MM mm){
        if(lGrid == null){
            mm.remove(lGrid);
        }
    }
    
    public void add(MM mm){    
        mm.add(lGrid);
    }
    
    public void delete(MM mm){      
        lGrid.setIcon(null);
        mm.remove(lGrid);
    }
    
    protected ImageIcon scaleImage(File file, int width, int height){
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
            
        Image dimg = img.getScaledInstance(width, height,
        Image.SCALE_SMOOTH);
       
        
        ImageIcon scaledImage = new ImageIcon(dimg);
        
        return scaledImage;
    }
    
    public void setPosition(int x, int y){
        
        this.x = x;
        this.y = y;
            
        lGrid.setBounds(x, y, width, height);
    }
    
    public Basic(int x, int y){
        
        this.x = x;
        this.y = y;
        
        lGrid = new JLabel();
        lGrid.setBounds(x, y, width, height);
    }
}
