package me.ivanyu.pojos.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.ivanyu.pojos.Conclusion;

import java.io.IOException;

public class ConclusionSerializer extends JsonSerializer<Conclusion> {
    @Override
    public void serialize(Conclusion conclusion,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeString(conclusion.getName());
    }
}
