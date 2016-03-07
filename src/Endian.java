
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author czarinarae
 */
public class Endian {
    
    public int getLines(ArrayList<String> inputs){
        int lines = 0;

        for (String s:inputs){
            if (!isNumber(s)){
                lines+=s.length()/4;
                if(s.length()%4>0)
                    lines++;
            }
            else{
                lines+=s.length()/8;
                if(s.length()%8>0)
                    lines++;
            }
        }
        return lines;
    }
    
    public static void main(String[] args) {
        try {
            Endian mp = new Endian();
            Scanner sc = new Scanner(new File("MP1.in"));
            ArrayList<String> inputs = new ArrayList();
            
            while (sc.hasNext()){ //retrieve all lines
                inputs.add(sc.nextLine());
            }
            
            int lines = mp.getLines(inputs);
            
            String[][] bigEndian = new String[lines][4];
            String[][] littleEndian = new String[lines][4];
            int currLine = 0; //currentline
            
            for (String s:inputs){
                if (!mp.isNumber(s)){
                    for (int j = 0, i = 0; i < s.length(); j++){
                        System.out.println(i +"  " + currLine + " " + j + " " + s.charAt(i));
                        bigEndian[currLine][j] = s.charAt(i++) + "";
                        if (j == 3 && i < s.length()){ 
                            j = -1;
                            currLine++;
                        }
                    }
                    currLine++;
                }
                else{
                    int value = Integer.valueOf(s);
                    int mod = 100;
                    for (int j = 3, i = 0; value > 0; j--){
                        bigEndian[currLine][j] = value%mod + "";
                        value = value/mod;
                        if (j == 0 && value>0){
                            j = 4;
                            currLine++;
                        }
                    }
                    currLine++;
                }
            }
            
            currLine = 0;
            
            for (String s:inputs){
                if (!mp.isNumber(s)){
                    for (int j = 3, i = 0; i < s.length(); j--){
                        System.out.println(i +"  " + currLine + " " + j + " " + s.charAt(i));
                        littleEndian[currLine][j] = s.charAt(i++) + "";
                        if (j == 0 && i < s.length()){
                            j = 4;
                            currLine++;
                        }
                    }
                    currLine++;
                }
                else{
                    int l = s.length()/8;
                    if(s.length()%8>0)
                        l++;
                    for (int i = 0; i < l; i++){
                        littleEndian[currLine] = bigEndian[currLine];
                    }
                    currLine++;
                }
            }
            
            System.out.println("");
            System.out.println("BIG ENDIAN");
            System.out.println("\t------------------------------------------------------------------");
            for (int i = 0; i < bigEndian.length; i++){
                //System.out.print("||\t");
                for (int j = 0; j < bigEndian[0].length; j++){
                    if (bigEndian[i][j]==null)
                        System.out.print("\t||\t0");
                    else
                        System.out.print("\t||\t" + bigEndian[i][j]);
                }
                System.out.print("\t||");
                System.out.println("");
                System.out.println("\t------------------------------------------------------------------");
            }
            System.out.println("");
            System.out.println("LITTLE ENDIAN");
            System.out.println("\t------------------------------------------------------------------");
            for (int i = 0; i < littleEndian.length; i++){
                //System.out.print("||\t");
                for (int j = 0; j < littleEndian[0].length; j++){
                    if (littleEndian[i][j]==null)
                        System.out.print("\t||\t0");
                    else
                        System.out.print("\t||\t" + littleEndian[i][j]);
                }
                System.out.print("\t||");
                System.out.println("");
                System.out.println("\t------------------------------------------------------------------");
            }
                            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Endian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getLineCount(String a){
        int four = 4;
        int count = 1;
        while (four < a.length()){
            four+=4;
            count++;
        }
        return count;
    }
    
    public boolean isNumber(String s){
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) <= '9' && s.charAt(i) >= '0');
            else
                return false;
        }
        return true;
    }
    
}
