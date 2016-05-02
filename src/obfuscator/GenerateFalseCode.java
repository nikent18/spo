/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscator;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author nikita
 */
public class GenerateFalseCode {

    public String potentialTypes[] = {"int", "float", "double"};
    public String arith_symbols[] = {"+", "-", "/", "*", "%"};

    public String createWrongCode() {
        FindFunction gencode_ffunc = new FindFunction();
        Random rand = new Random();
        String str;

        str = " {\n";
        for (int i = 0; i < 10; i++) {
            String randVar = gencode_ffunc.generateRandomString(5).toString();
            str += this.potentialTypes[rand.nextInt(2)].toString() + " " + randVar;
            str += "; \n";
            str += randVar + " = " + rand.nextInt() + this.arith_symbols[rand.nextInt(this.arith_symbols.length - 1)];
            str += " " + rand.nextInt() + "\n";

        }
        str += "}";
        return str;
    }
}
