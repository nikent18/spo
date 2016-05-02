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
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikita
 */
public class Obfuscator {

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) throws IOException {
        FindFunction ff = new FindFunction();
        GenerateFalseCode gfc = new GenerateFalseCode();
        Utils ut = new Utils();
        ut.DeleteComments("E:\\programming\\Obfuscator\\tmp.java", "E:\\programming\\Obfuscator\\out.java");
     //   System.out.println(gfc.createWrongCode());
      /*
        WriteAllInOneFile writefile = new WriteAllInOneFile();
        writefile.writeImports("E:\\programming\\Obfuscator\\src\\obfuscator", "E:\\programming\\Obfuscator\\out.java");
        writefile.writeMainFile("E:\\programming\\Obfuscator\\src\\obfuscator\\Obfuscator.java", "E:\\programming\\Obfuscator\\out.java");
        writefile.findClass("E:\\programming\\Obfuscator\\src\\obfuscator", "E:\\programming\\Obfuscator\\out.java");
       
        try {
            ff.writeChangeFunctionsName("E:\\programming\\Obfuscator\\out.java", "E:\\programming\\Obfuscator\\tmp.java");
        } catch (IOException ex) {
            Logger.getLogger(Obfuscator.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }

}
