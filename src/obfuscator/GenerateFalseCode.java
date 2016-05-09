/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscator;

import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
            str += " " + rand.nextInt() + ";\n";

        }
        str += "}";
        return str;
    }
    
    public String number(){
        Random r = new Random();
        return ""+(r.nextInt(40-1)+1);  
    }
    public String operator(){
        String signs[] = {"+","-","%","*"};
        return ""+(signs[new Random().nextInt(signs.length)]); 
    }
    public String level(int n){
        if(n==1) return number() + 
                        operator() + 
                        number();
        else return level(n-1) + 
                    operator() + 
                    number();
    }
    
    public String createIfStatement() {
        String str = "";
        Random rand = new Random();
        double num = 0;
        String exp = level(rand.nextInt(5)+1);
        str = "if( "+ exp;
        try {
            num = countMathExpression(exp)-rand.nextInt(100)-1;
        } catch (ScriptException ex) {
            System.out.println(ex.getMessage());
        }
        str+=" > "+num+" )";
        str+=createWrongCode();
        System.out.println(str);
        return str;
    }
    public double countMathExpression(String exp) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
       
        return  Double.parseDouble(engine.eval(exp).toString());        
    }
    
}
