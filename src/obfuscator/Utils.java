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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikita
 */
public class Utils {

    public void DeleteComments(String input, String output) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
        boolean inBlockComment = false;
        boolean inSlashSlashComment = false;
        int char1 = reader.read();
        if (char1 != -1) {
            int char2;
            while (char1 != -1) {
                if ((char2 = reader.read()) == -1) {
                    writer.write(char1);
                    break;
                }
                if (char1 == '/' && char2 == '*') {
                    inBlockComment = true;
                    char1 = reader.read();
                    continue;
                } else if (char1 == '*' && char2 == '/') {
                    inBlockComment = false;
                    char1 = reader.read();
                    continue;
                } else if (char1 == '/' && char2 == '/' && !inBlockComment) {
                    inSlashSlashComment = true;
                    char1 = reader.read();
                    continue;
                }
                if (inBlockComment) {
                    char1 = char2;
                    continue;
                }
                if (inSlashSlashComment) {
                    if (char2 == '\n') {
                        inSlashSlashComment = false;
                        writer.write(char2);
                        char1 = reader.read();
                        continue;
                    } else if (char1 == '\n') {
                        inSlashSlashComment = false;
                        writer.write(char1);
                        char1 = char2;
                        continue;
                    } else {
                        char1 = reader.read();
                        continue;
                    }
                }
                writer.write(char1);
                char1 = char2;
            }
            writer.flush();
            writer.close();
        }
    }

    public void deleteLineBreaks(String input, String output) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
        String content = new Scanner(new File(input)).useDelimiter("\\Z").next();
        System.out.println(content);
        content = content.replaceAll("\n", "").replaceAll("\r", "");
        File file = new File(output);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }

    public void changeStandartBufferedReader(String input, String output) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.contains("import") && line.contains("BufferedReader")) {
                line = line.replaceAll(line, "import java.io.Writer;");
            }
            bw.write(line);
            bw.newLine();
        }
        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\programming\\Obfuscator\\resources\\BufferedWriter")));
        while ((line = br2.readLine()) != null) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
        br.close();
    }

    byte[] toByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }   
    
    void hideInteger (String input, String output, String variable) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.contains(variable)) {
                if (line.contains("int")) {
                    line = line.replaceAll("int", "byte[]");
                }
                if (line.contains("=")) {
                    int ind1 = line.indexOf("=");
                    int ind2 = line.indexOf(variable);
                    if (ind1 < ind2) {
                        line = line.replaceAll(variable, "ByteBuffer.wrap("+variable+").getInt()");
                    } else {
                        String substr = line.substring(line.indexOf("=")+1);
                        line = line.replaceAll(substr, "ByteBuffer.allocate(4).putInt("+substr+").array();");
                    }
                }
            }
            bw.write(line);
        }
        br.close();
        bw.close();
    }
}
