/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m.m;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author HP
 */
public class Score {
    
    private File file = new File("resources/scores/scores.txt");  
    private FileWriter fileWriter;
    private BufferedReader fileReader;
    private List<String> scoreList = new ArrayList<String>();
    private List<String> sortedList = new ArrayList<String>();
    
    
    private int a, b;
    private int index;
    private String replace = null;
    
    
    private void sort(){
              
        sortedList.add("<html><p>");
        
        int size = scoreList.size();
        
        for(int i = 0; i < size; i++){           
            for(int j = 0; j < scoreList.size(); j++){             
                replace = scoreList.get(j).replaceAll("[^-?0-9]+", ""); 
                a = Integer.parseInt(replace);
                
                if((a > b)){
                    b = a;
                    index = j;
                }
            }
            sortedList.add(scoreList.get(index));
            scoreList.remove(index);
            b = 0;
        }             
        sortedList.add("<p><html>");
    }
    
    
    public void Set(String text){
          try{
            fileWriter = new FileWriter(file.getAbsoluteFile(),true);
            fileWriter.write(text+System.lineSeparator());

        }catch(IOException e){}     
        finally{
            try{
                if(fileWriter != null)
                    fileWriter.close();
            }catch(IOException e){}
        }
    }
      
    public String Get(){
        
        StringBuffer loadedText = new StringBuffer();
        int cnt = 0;
        String flag = null;
        
        try{
            InputStream fis=new FileInputStream(file);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                scoreList.add(line + "<br/>");
            }                   
            
            sort();
            
            loadedText.append(sortedList.get(0));
            
            for(int i = 1; i < sortedList.size()-1; i ++){
                loadedText.append(i+". ").append(sortedList.get(i));
            }      
            
            loadedText.append(sortedList.get(sortedList.size()-1));
            br.close();
        }
        catch(Exception e){
            System.err.println("Error: Target File Cannot Be Read");
        }     
        return loadedText.toString();
    }
    
    public Score(){
        
    }
}
