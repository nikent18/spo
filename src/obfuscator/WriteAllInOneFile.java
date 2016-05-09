/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static obfuscator.FindFunction.generateRandomString;

/**
 *
 * @author nikita
 */
public class WriteAllInOneFile {

    public String imports[];

    public void findClass(String path, String fileOutput) throws IOException {
        Files.walk(Paths.get(path)).forEach(filePath -> {
            System.out.println(filePath.toString());
            if (!filePath.toString().contains("Obfuscator.java")) {
                FileInputStream fis;

                try {
                    fis = new FileInputStream(filePath.toString());
                    boolean isWrite = false;
                    FileOutputStream fos = new FileOutputStream(fileOutput, true);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                    String line = null;
                    int inFunc = 0;
                    while ((line = br.readLine()) != null) {
                        if (line.contains("class") && line.contains("{") 
                            && (line.contains("public") || line.contains("private")|| line.contains("protected"))
                            &&!line.contains("(")) {
                                line = line.substring(line.indexOf("class") );
                                isWrite = true;
                        }
                        if ((line.contains("public") || line.contains("private")|| line.contains("protected")) 
                                && ((line.contains("void") || line.contains("int")|| line.contains("String")|| line.contains("Double")|| line.contains("Float")))) {
                            inFunc = 1;
                        }
                        if (isWrite) {
                            bw.write(line);
                            bw.newLine();
                            Random rnd = new Random();
                            if (inFunc ==1 && rnd.nextInt(10)==7) {
                                GenerateFalseCode fc = new GenerateFalseCode();
                                bw.write(fc.createIfStatement());
                                bw.newLine();
                                inFunc = 0;
                            }
                        }
                    }
                    bw.close();
                    br.close();
                    fis.close();
                    fos.close();
                } catch (Exception ex) {
                    Logger.getLogger(WriteAllInOneFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void writeImports(String path, String fileOutput) throws IOException {
        Files.walk(Paths.get(path)).forEach(filePath -> {
            FileInputStream fis;
            try {
                fis = new FileInputStream(filePath.toString());
                boolean isWrite = false;
                FileOutputStream fos = new FileOutputStream(fileOutput, true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.contains("import ") && !line.contains("(")) {
                        bw.write(line);
                        bw.newLine();
                    }
                }
                bw.close();
                br.close();
                fis.close();
                fos.close();
            } catch (Exception e) {

            }

        });
    }

    public void writeMainFile(String fileInput, String fileOutput) throws IOException {
        FileInputStream fis;

        fis = new FileInputStream(fileInput);
        boolean isWrite = false;
        FileOutputStream fos = new FileOutputStream(fileOutput, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        while ((line = br.readLine()) != null) {
            if (!line.contains("package")) {
                bw.write(line);
                bw.newLine();
            }
        }
        bw.close();
        br.close();
        fis.close();
        fos.close();
    }
}
