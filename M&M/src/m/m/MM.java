package m.m;

import com.sun.prism.j2d.J2DPipeline;
import java.awt.*;
import java.awt.Image;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.Scene;
import javax.sound.sampled.*;

public class MM extends JFrame{
      
    public final Dimension Resolution = Toolkit.getDefaultToolkit().getScreenSize();
    public final int Width = 1000;                                             // Szerokosc i wysokosc okna glownego
    public final int Height = 650;
    public final int zombieMAX = 5;
    public final int LivesCount = 5;
    public Live[] livesTab = new Live[5];
    private int boneTime = 50;
    private int mouseTime = 20;
    private int zombieTime = 3000;
    
    private int difficultyUP = 2;       // stopien trudnosci, po kazdym zabitym zombie licznik sie zwieksza i jesli bedzie >= tej wartosci to tworzy sie nowy rodzaj zombie
    public int diffucultCnt; 
    private int chanseToZombie = 60;   
    
    private Image Background;
    
    public JLabel lScores = new JLabel("Punkty: ");
    public JLabel smashesDeadZombie[] = new JLabel[15];
    List<Zombie> allZombies = new ArrayList<Zombie>();  
    
    public Gun g1;
    public boolean start;

    public Zombie[] zombies = new Zombie[zombieMAX];
    private Random zombieGenerator = new Random();
    
    public TimerTask1 timerTask1 = new TimerTask1();
    public TimerTask2 timerTask2 = new TimerTask2();
    public TimerTask3 timerTask3 = new TimerTask3();
    
    private int zombieCnt;
    private int throwCnt;
    private int emptyIndex;
    private Font font;
    private int mainScore;
    private int lives;
    
    private ButtonControl buttonControl;
    public Message message;
    
    public ScoreGrid scoreGrid = new ScoreGrid(230,100);
    public Authors author = new Authors(370, 200);
    private boolean roof;   
    
    public MM(){
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setContentPane(new JLabel(new ImageIcon("resources/img/background/Background.png")));
        setLayout(null);
        setFont();
        setCustomCursor(true);
             
        lScores.setBounds(20, 0, 300, 100);
        lScores.setFont(font);
        lScores.setForeground(new Color(250, 80, 60));
        
        message = new Message(320,150);    
        message.add(this);
        author.add(this);      
        scoreGrid.add(this);
            
        timerTask1.start();             // WLACZENIE TIMEROW
        timerTask2.start();
        timerTask3.start();
        
        buttonControl = new ButtonControl(this);            // USTAWIENIE WSZYSTKICH PRZYCISKOW
        
        pack();      
        setBounds((Resolution.width/2 - Width/2),(Resolution.height/2 - Height/2),Width,Height);     
        addKeyListener(buttonControl);   
        
           
        setVisible(true);     
    }
    
    public int getMainScore(){
        return mainScore;
    }
    
    private void addZombie(){
        
        int randomValue = zombieGenerator.nextInt(100);
        
        if(randomValue > chanseToZombie - diffucultCnt){
            
            int randomX = zombieGenerator.nextInt(Width);
            int randomY = zombieGenerator.nextInt(Height);
            
            do{
                randomX = zombieGenerator.nextInt(Width);
                randomY = zombieGenerator.nextInt(Height);
                
            }while(!(((randomX > -80 && randomX < 180)&&(randomY > 240 && randomY < 350)) ||
                    ((randomX > 180 && randomX < 350)&&(randomY > 240 && randomY < 460)) ||
                    ((randomX > 350 && randomX < 430)&&(randomY > 260 && randomY < 460)) ||
                    ((randomX > 500 && randomX < 750)&&(randomY > 200 && randomY < 460)) ||
                    ((randomX > 750 && randomX < 800)&&(randomY > 230 && randomY < 460)) ||
                    ((randomX > 150 && randomX < 280)&&(randomY > 0 && randomY < 100))));
            
            
            if((randomX > 150 && randomX < 280)&&(randomY > 0 && randomY < 100)) roof = true;
                      
            if(emptyIndex != 0){
                zombies[emptyIndex - 1] = getTypeZombie(randomX, randomY);
                allZombies.add(zombies[emptyIndex - 1]);
                if(zombies[emptyIndex - 1] != null)
                    add(zombies[emptyIndex - 1].lGrid);
                emptyIndex = 0;
            }
            roof = false;
        }      
    }
    
    private Zombie getTypeZombie(int x, int y){
        
        int randomValue = zombieGenerator.nextInt(100);
        
        if(((randomValue > 33) && (randomValue <= 66)) && (!roof) && (diffucultCnt >= difficultyUP)) return new WalkZombie(x, y, 180, 120, this);
        else if((randomValue > 66) && (!roof) && (diffucultCnt >= difficultyUP*2)) return new RunningZombie(x, y, 180, 120, this);
        else return new Zombie(x, y, 180, 180, this);
    }
    
    private void zombieControl(){           // KONTROLUJE CZYNNOSCI ZWIAZANE Z ZOMBIAKAMI ORAZ KOŚĆMI
         for(int i = 0; i < zombies.length; i++){
            if(zombies[i] != null){  
                addScores(zombies[i]);                                                                                 // Sytuacja kiedy zombiak jest martwy ale juz rzucil swoja kosc i zeby doleciala ona do konca a nie zatrzymala sie
                if(zombies[i].dead && zombies[i].bone.newThrow == true){        // JEŚLI ZOMBIE MARTWY ORAZ NIE LECI JEGO KOŚĆ
                    zombies[i].delete(this);
                    emptyIndex = i + 1;
                }               
                if(zombies[i].bone.takeLive){           
                    livesTab[lives].delete(this);
                    lives++;
                    zombies[i].bone.takeLive = false;  
                    
                    end();
                }
                if(zombies[i] instanceof WalkZombie){
                    WalkZombie zombie = (WalkZombie)zombies[i];
                    zombie.walk();
                }
            }else{
                emptyIndex = i + 1;
            }
            repaint();
        }
    }
      
    public void hideOrShowAll(boolean value){             // UKRYWANIE LUB UKAZYWANIE OBIEKTOW ROZGRYWKI W ZALEZNOSCI CZY FALSE CZY TRUE
        
        for(Live live: livesTab){
            live.lGrid.setVisible(value);
        }
            for(int i = 0; i < zombies.length; i++){
                if(zombies[i] != null){
                    for(int j = 0; j < zombies[i].smashTab.length; j++){
                        if(zombies[i].smashTab[j] != null)
                            zombies[i].smashTab[j].setVisible(value);
                    }
                    zombies[i].lGrid.setVisible(value);
                    zombies[i].bone.lGrid.setVisible(value);
                }
            }
        
        lScores.setVisible(value);
        g1.lGrid.setVisible(value);
    }
    
    private void addScores(Zombie zombie){          // DODAWANIE PUNKTOW ZA ZOMBIE ORAZ KOŚCI
                   
        if(zombie.dead && zombie.scoreFlag == true) mainScore += zombie.getScore();   
        if(zombie.bone.scoreFlag) mainScore += zombie.bone.getScore();
        
        lScores.setText("Punkty: "+ mainScore);
        repaint();
    }
     
     public void setCustomCursor(boolean type){         // ZMIANA KURSORA MYSZKI NA ZIELONY
         
         String stream;
         
         if(!type) stream = "resources/cursor/blankCursor.png";
         else stream = "resources/cursor/cur.png";
         
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(stream);
        Cursor c = toolkit.createCustomCursor(image , new Point(0, 
           0), "img");
        
         setCursor(c);           
     } 
     
     private void end(){                        // METODA UKAZUJE WIADOMOSC O PRZEGRANEJ
               
         if(lives >= LivesCount){
             start = false;
             g1.lGrid.setVisible(false);
             setCustomCursor(true);
                     
             message.setScore(mainScore);
             message.visibility(true);
         }
     }
     
     private void clean(){              // CZYSCI POZOSTALOSCI PO ZOMBIACH     
         
         if(zombies[0] != null){
            for(Zombie zombie: zombies){
                if(zombie.bone != null){
                    zombie.bone.clean(this);
                }
                zombie.clean(this);
            }
         }
     }
     
    public void addLives(){                             // DODAJE ZYCIA 
        
        for(int i = 0; i < livesTab.length; i++){                
            livesTab[i] = new Live(700 + i*50,530);
            add(livesTab[i].lGrid);
        }
    }
    
    public void removeAll(){                            // USUWA KAZDY OBIEKT 
         for(int i = 0; i < livesTab.length; i++){              
            livesTab[i] = null;
        }
        for(int i = 0; i < zombies.length; i++){
            if(zombies[i] != null){
                zombies[i].cleanSmash(this);
                zombies[i] = null;
            }        
        }
        allZombies.forEach((temp) -> {
            temp.cleanSmash(this);                  // POTRZEBNE DO USUNIECIA KOSCI ZABITYCH ZOMBIE
        });
        lScores.setText("Punkty: 0");
        lives = 0;
    }
    
    private void setFont(){         // USTAWIA NIESTANDARDOWĄ CZCIONKĘ  
        try {
             font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/font/Anja_Eliane.ttf"))).deriveFont(Font.ITALIC, 40);       
        }catch(Exception ex){
             ex.printStackTrace();
        }
    }
    
    public class TimerTask1 extends TimerTask{                // DODAJE NOWE ZOMBIE JESLI JEST NA NIE MIEJSCE ORAZ WYLOSOWANA ZMIENNA SPELNIA WYMAGANIA
        private Timer timer;   
        public void run(){         
            if(start){
                addZombie();
                repaint();
            }
        }      
        public void start(){           
            timer = new Timer();
            timer.schedule(this, 0, zombieTime);
        }
    } 
    
    public class TimerTask2 extends TimerTask{   // TIMER USTAWIA POLOZENIE KURSORA MYSZKI ORAZ KONTROLUJE CZYNNOSCI ZWIAZANE Z ZOMBIE
        private Timer timer;      
        public void run(){        
            if(start){
               g1.setPosition((MouseInfo.getPointerInfo().getLocation().x - (Resolution.width/2 - Width/2)), (MouseInfo.getPointerInfo().getLocation().y - (Resolution.height/2 - Height/2))); 
               zombieControl();
               clean(); 
            }
        }       
        public void start(){          
            timer = new Timer();
            timer.schedule(this, 0, mouseTime);
        }
    } 
    
    public class TimerTask3 extends TimerTask{      // TIMER ZAJMUJE SIE ANIMACJA LOTU KOŚCI
        private Timer timer;      
        public void run(){  
            
            if(start){               
                throwCnt++;
                if(throwCnt >= 50){

                    for(Zombie zombie: zombies){
                        if(zombie != null){
                            zombie.checkIfThrow(MM.this);
                        }
                    }
                    throwCnt = 0;
                }            
                for(Zombie zombie: zombies){
                    if(zombie != null){
                        if(zombie.getThrowFlag()){
                            zombie.Throw();
                            zombie.boneGifCnt++;
                            if(zombie.boneGifCnt > 25) zombie.boneGifCnt = 0;                
                        }        
                    } 
                }
            }
        }       
        public void start(){          
            timer = new Timer();
            timer.schedule(this, 0, boneTime);
        }
    }  
    
    private Font getFont(int size){
      
        Font font = null;
        try {
             font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/font/Anja_Eliane.ttf"))).deriveFont(Font.ITALIC, size);
        }catch(Exception ex){
             ex.printStackTrace();
        }
        return font;
    }
    
    public static void main(String[] args){
        new MM();     
    }
}


