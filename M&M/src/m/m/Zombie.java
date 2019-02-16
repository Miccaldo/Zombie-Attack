/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author HP
 */
public class Zombie extends Basic {
    
    private final int throwChanse = 70;
    Bone bone = new Bone(x,y);
    public JLabel smashTab[] = new JLabel[15];
    Random generator = new Random();
    private boolean throwFlag;
    public boolean dead;
    public int boneGifCnt;
    private int cnt;
    
    protected int shiftBoneX = 150;
    protected int shiftBoneY = 90;  
    private MM mm;
    
        
    public void cleanSmash(MM mm){          // USUWA WSZYSTKIE ZBITE EKRANY 
        for(int i = 0; i < smashTab.length; i++){
            if(smashTab[i] != null){
                smashTab[i].setIcon(null);
                mm.remove(smashTab[i]);
            }
        }
    }
    
    public boolean getThrowFlag(){
        return throwFlag;
    }
    
    public void Throw(){            // GLOWNA METODA RYSUJACA LECACĄ KOŚĆ
        if(!bone.newThrow)
            bone.Draw(boneGifCnt);
    }
      
    public void checkIfThrow(MM mm){                // SPRAWDZA CZY MOZNA RZUCIC KOSCIĄ
        
        if(bone.newThrow == true && !dead){         // JESLI ZOMBIE NIE JEST MARTWY ORAZ DOSTEPNY NOWY RZUT (KOSC UPADLA)
            bone = new Bone(x + shiftBoneX , y + shiftBoneY);
            
            int randomValue = generator.nextInt(100);
            if(randomValue > throwChanse){
                mm.remove(this.lGrid);
                mm.add(bone.lGrid);
                smashTab[cnt] = bone.lSmash;
                mm.add(smashTab[cnt]);
                mm.add(this.lGrid);
                lGrid.setDoubleBuffered(true);
                
                throwFlag = true;
                bone.newThrow = false;
                
                cnt++;
            }
        }
    }
    
    public Zombie(int x, int y, int width, int height, MM mm){
        super(x, y);
        
         lGrid.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent e){         // JESLI ZOMBIE ZASTRZELONE, WYDAJ ODGLOS STRZALU, WYKASUJ ZOMBIE
                                                            // ORAZ USTAW FLAGE DO PRZYZNANIA PUNKTOW
                if(!dead){
                    lGrid.setIcon(null);
                    scoreFlag = true;
                    shot.play();
                    mm.diffucultCnt++;
                    mm.repaint();
                }
                  
                dead = true;
                }  
            });
         
        image = new ImageIcon("resources/img/zombie/zombie.gif");
        lGrid.setDoubleBuffered(true);
        lGrid.setIcon(image);
        lGrid.setBounds(x, y, width, height);
        this.mm = mm;
        
        score = 15;
    }
}
