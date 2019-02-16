/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

/**
 *
 * @author HP
 */
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.Random;
import sun.nio.cs.Surrogate;

/**
 *
 * @author HP
 */
public class Bone extends Basic {
    
    private final int smashMAX = 60;
    private final int sizeSmash = 300;
    
    private File iSmash[];
    private File bone[];
    private int value;
    private int fall;
    private int smashCnt;
    private Random generator;
    public JLabel lSmash;
    public boolean smashFlag;
    public boolean newThrow = true;
    public boolean takeLive;  
    public boolean shoot;
    private Player sound = new Player(new File("resources/sounds/brake.wav"));
   
      
    @Override
    public void delete(MM mm){      // KASUJE WSZYSTKIE FLAGI DO WARTOSCI POCZATKOWEJ
        super.delete(mm);
        smashCnt = 0;
        smashFlag = false;
        takeLive = false;
        newThrow = true;
        value = 0;
        fall = 0;
        smashCnt = 0;
    }

    public void Draw(int cnt){              // RYSUJE KOSC
        
        smashCnt++;         // LICZNIK LOTU KOSCI
            
        if(!smashFlag && !shoot){       // JESLI KOSC NIE ZOSTALA STRZELONA ORAZ NIE UDERZYLA EKRANU
            if(y - value >= 100){       //  TO MA LECIEC DO PRZODU
                fall += 4;
                value += 4;
            }
            else{
                fall -= 2;
                value += 8;
            }
            
            lGrid.setIcon(scaleImage(bone[cnt], width + value, height + value));
            lGrid.setBounds(x - value/2, y - value/2 - fall, width + value, height + value);   
            
            getSmashImg();
        }else{                  // W PRZECIWNYM WYPADKU KOSC MA UPASC NA ZIEMIE
            fall -= 5;
            DropBone();
    
            if(fall <= -550){           // JESLI KOSC UPADNIE, WYZERUJ STAN FLAG I WYKASUJ KOSC Z PAMIECI
                lGrid.setIcon(null);
                newThrow = true;
                smashFlag = false;
                value = 0;
                fall = 0;
                smashCnt = 0;
            }
        }
    }
    
    private boolean Smash(){        // FLAGA USTAWIA TRUE JESLI LICZNIK LOTU KOSCI ZLICZY ODPOWIEDNIA WARTOSC
        if(smashCnt >= smashMAX){
            sound.play();
            return true;
        }
        else return false;
    }
    
    public int getX(){ return x - value/2;}   
    public int getY(){ return y - value/2 - fall; }
    
    private void getSmashImg(){             // JESLI KOSC DOLECI DO EKRANU, FUNKCJA LOSUJE GRAFIKE STLUCZNIA SPOSROD 3
                                            // ORAZ USTAWIA JA W ODPOWIEDNIM MIEJSCU
        if(Smash() == true){
            int randomValue = generator.nextInt(100);
            ImageIcon smashImage;

            if(randomValue <= 33) smashImage = scaleImage(iSmash[0],300,300);
            else if(randomValue <= 66)smashImage = scaleImage(iSmash[1],300,300);
            else smashImage = scaleImage(iSmash[2],300,300);   
            
            lSmash.setIcon(smashImage);
            lSmash.setBounds(x - value/2, y - value/2 - fall + 100, sizeSmash, sizeSmash);
            
            smashFlag = true;
            takeLive = true;
        }
    }
    
    private void DropBone(){            // PO UDERZENIU, KOSC LECI W DOL 
        
        lGrid.setBounds(x - value/2, y - value/2 - fall, width + value, height + value); 
    }
    
    public Bone(int x, int y){
        super(x, y);
        
        width = 30;
        height = 50;
        score = 10;
        
        bone = new File[30];
        iSmash = new File[3];
        
        lGrid = new JLabel();
        lSmash = new JLabel();
        lGrid.setBounds(this.x, this.y, width, height);
        
        generator = new Random();
        
        for(int i = 1; i < 27;i++){
            bone[i-1] = new File("resources/img/bone/"+i+".png");
        } 
        
        for(int i = 1; i < 4;i++){
            iSmash[i-1] = new File("resources/img/smash/"+i+".png");
        }
        
        lGrid.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){      
                
                if(!shoot){
                    scoreFlag = true;
                    shot.play();
                }
                shoot = true;
            }  
        });
    }
}
