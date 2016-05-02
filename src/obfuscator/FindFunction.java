/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikita Class to find and rename function
 */
public class FindFunction {

    private String types[] = {"String", "void", "int", "double", "float", "char", "boolean"};
    private String type;
    private int functionNameLength = 15;
    Map<String, String> hashmap = new HashMap<String, String>();
    public void find(String fileInput, String fileOutput) throws IOException {
        FileInputStream fis;

        try {
            fis = new FileInputStream(fileInput);
            
            FileOutputStream fos = new FileOutputStream(fileOutput, true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            String replace = null;
            while ((line = br.readLine()) != null) {
                replace = line;
                if (isLineWithType(line)) {
                    if (isLineWithFunction(line)) {                       
                        //replace = line.replaceAll(getFunctionName(line, this.type),
                        //        generateRandomString(this.functionNameLength));
                        if (!getFunctionName(line, this.type).equals(" main ")) {
                            hashmap.put( getFunctionName (line, this.type), 
                                    generateRandomString(this.functionNameLength));
                        }
                        
                    }
                } 
            }
            bw.close();
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FindFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeChangeFunctionsName(String fileInput,String fileOutput) throws IOException {
        find("E:\\programming\\Obfuscator\\out.java", "E:\\programming\\Obfuscator\\tmp.java");
        
        FileInputStream fis;

        try {
            fis = new FileInputStream(fileInput);
            
            FileOutputStream fos = new FileOutputStream(fileOutput, true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            
            
            while ((line = br.readLine()) != null) {
                int max = 0;
                String replace = null;
                for (Map.Entry entry : hashmap.entrySet()) {
                    if (line.contains(entry.getKey().toString())) {
                        if (entry.getKey().toString().length() >= max) {
                            max = entry.getKey().toString().length();
                            replace = line.replaceAll(entry.getKey().toString(), entry.getValue().toString());
                        }
                    } 
                        //line = line.replaceAll(entry.getKey().toString(), entry.getValue().toString());
                    }
                if (replace == null) {
                    replace = line;
                }
                bw.write(replace);
                bw.newLine();
            }
            bw.close();
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FindFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean isLineWithType(String str) {
        int min = str.indexOf(types[0]);
        int k = 0;
        //int index = str.indexOf(types[0]);
        for (int i = 0; i < types.length; i++) {
            Boolean found = Arrays.asList(str.split(" ")).contains(types[i]);
            if (found) {
                //setType(types[i]);
                for (int j = 1; j< types.length; j++) {
                    int index = str.indexOf(types[j]);
                    if (index > -1 && index < min)  {
                        min = index;
                        k = j;
                    }
                }
                setType(types[k]);
                return true;
            }
        }
        return false;
    }

    private boolean isLineWithFunction(String line) {
        if (line.contains("(") && line.contains(")") && !line.contains("=")) {
            return true;
        }
        return false;
    }

    private String getFunctionName(String line, String type) {
        int ind1 = line.indexOf(type) + type.length() +1;
        int ind2 = line.lastIndexOf(" (");

        if (ind2 == -1) {
            ind2 = line.lastIndexOf("(");
        }
        System.out.println(line.substring(ind1,ind2 ));
        return line.substring(ind1,ind2 );
    }

    private void setType(String type) {
        this.type = type;
    }

    public static String generateRandomString(int len) {
        String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        sb.append(" ");
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        sb.append(" ");
        return sb.toString();
    }
}
