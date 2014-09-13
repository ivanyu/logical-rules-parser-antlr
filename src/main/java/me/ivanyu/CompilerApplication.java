package me.ivanyu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import me.ivanyu.compiler.Compiler;
import me.ivanyu.pojos.RuleSet;

public class CompilerApplication {
    public static void main(String[] args) {
        Compiler compiler = new Compiler();
        RuleSet ruleSet = compiler.compile("if -(A + 2) > 0.5 then be_careful;");

       // JSON serialization
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(ruleSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString);
    }
}
