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
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream (input)));
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
    public void deleteLineBreaks (String input, String output) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream (input)));
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
}
