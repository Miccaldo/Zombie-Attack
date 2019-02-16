/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author HP
 */
public class ButtonControl implements ActionListener, KeyListener{
    
    protected MM mm;
    protected JButton bNewGame, bAuthors, bScores, bExit, bPlay, bReplay, bMenu;
    private int cnt = 1;
    private Score score = new Score();
    
    public ButtonControl(MM mm){
        
        this.mm = mm;
        buttonInitalize();
        mm.message.submit.addActionListener(this);
        mm.scoreGrid.submit.addActionListener(this);
        mm.author.submit.addActionListener(this);
    }
    
    public void buttonInitalize(){
        
        bNewGame = new JButton();
        bAuthors = new JButton();
        bScores = new JButton();
        bExit = new JButton();
        
        bPlay = new JButton();
        bMenu = new JButton();
        bReplay = new JButton();
        
        bNewGame.setBounds(360, 120, 280, 80);
        bAuthors.setBounds(380, 205, 230, 80);
        bScores.setBounds(380, 295, 215, 80);
        bExit.setBounds(390, 380, 195, 80);
        
        bPlay.setBounds(580, 205, 128, 128);
        bReplay.setBounds(450, 205, 128, 128);
        bMenu.setBounds(320, 205, 128, 128);
        
            
        buttonSetup(bNewGame, new ImageIcon("resources/img/menu/newGame.png"), new ImageIcon("resources/img/menu/_newGame.png"));
        buttonSetup(bAuthors, new ImageIcon("resources/img/menu/authors.png"), new ImageIcon("resources/img/menu/_authors.png"));
        buttonSetup(bScores, new ImageIcon("resources/img/menu/scores.png"), new ImageIcon("resources/img/menu/_scores.png"));
        buttonSetup(bExit, new ImageIcon("resources/img/menu/exit.png"), new ImageIcon("resources/img/menu/_exit.png"));
        
        buttonSetup(bPlay, new ImageIcon("resources/img/menu/play.png"), new ImageIcon("resources/img/menu/play2.png"));
        buttonSetup(bMenu, new ImageIcon("resources/img/menu/menu.png"), new ImageIcon("resources/img/menu/menu2.png"));
        buttonSetup(bReplay, new ImageIcon("resources/img/menu/replay.png"), new ImageIcon("resources/img/menu/replay2.png"));
                  
        bPlay.setVisible(false);
        bMenu.setVisible(false);
        bReplay.setVisible(false);                        
    }
    
    private void buttonSetup(JButton button, ImageIcon exitImage, ImageIcon enterImage){
        
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setIcon(exitImage);
        
        button.addActionListener(this);
        
        button.addMouseListener(new MouseAdapter() {
            
            public void mouseEntered(MouseEvent evt)
            { 
                 button.setIcon(enterImage);
            }
            public void mouseExited(MouseEvent evt)
            {
                button.setIcon(exitImage); 
            }
        });
        
        mm.add(button);
        
        button.setFocusable(false);
    }
    
    private void visibilityMenuButtons(boolean value){
        bNewGame.setVisible(value);
        bAuthors.setVisible(value);
        bScores.setVisible(value);
        bExit.setVisible(value); 
    }
    
    public void actionPerformed(ActionEvent e) {
           
        JButton source = (JButton)e.getSource();

        if(source == bNewGame){
            visibilityMenuButtons(false);          

            mm.setCustomCursor(false);                
            mm.g1 = new Gun(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y); 

            mm.add(mm.g1.lGrid);
            mm.add(mm.lScores);
            
            mm.diffucultCnt = 0;
            
            mm.removeAll(); 
            mm.addLives();

            mm.hideOrShowAll(true);
            mm.start = true;   
     
        } 
        else if(source == bAuthors){
            mm.author.visibility(true);
            visibilityMenuButtons(false);  
        } 
        else if(source == bScores){   
            mm.scoreGrid.set();
            mm.scoreGrid.visibility(true);
            visibilityMenuButtons(false);   
        }else if(source == bExit){
            System.exit(0);
        }
        else if(source == bMenu){
            bPlay.setVisible(false);
            bMenu.setVisible(false);
            bReplay.setVisible(false);  

            visibilityMenuButtons(true);    
            
            mm.setCustomCursor(true);
            cnt = 1;
        }else if(source == bReplay){
            
            bPlay.setVisible(false);
            bMenu.setVisible(false);
            bReplay.setVisible(false);  
            
            mm.removeAll();                    
            mm.addLives();
            mm.setCustomCursor(false);
            
            mm.hideOrShowAll(true);
            mm.start = true;
            mm.diffucultCnt = 0;
            cnt = 1;
               
        }else if(source == bPlay){    
            Play();
            cnt = 1;
        }
        else if(source == mm.message.submit){ 
            
            String result = mm.message.getUserName()+" "+Integer.toString(mm.getMainScore());
            
            mm.hideOrShowAll(false);           
            mm.message.visibility(false);
            
            score.Set(result);
                      
            visibilityMenuButtons(true);    
            
            mm.removeAll();
            mm.diffucultCnt = 0;
            cnt = 1;
        } else if(source == mm.scoreGrid.submit){ 
            visibilityMenuButtons(true);
            mm.scoreGrid.visibility(false);                 
        }else if(source == mm.author.submit){
            visibilityMenuButtons(true);
            mm.author.visibility(false);
            
        }
    }
        
    public void keyPressed(KeyEvent e) {
        
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE && (mm.start == true || cnt >= 1)) {
              
            if(cnt >= 2){
                Play();
                cnt = 0;
            }else{  
                mm.hideOrShowAll(false);
                mm.setCustomCursor(true);

                bNewGame.setVisible(false);
                bAuthors.setVisible(false);
                bScores.setVisible(false);
                bExit.setVisible(false);  

                bPlay.setVisible(true);
                bMenu.setVisible(true);
                bReplay.setVisible(true);

                mm.start = false;
            }
            cnt++;
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    
    private void Play(){
        
        bPlay.setVisible(false);
        bMenu.setVisible(false);
        bReplay.setVisible(false);  

        mm.hideOrShowAll(true);
        mm.setCustomCursor(false);

        mm.repaint();
        mm.start = true;       
    }   
}
