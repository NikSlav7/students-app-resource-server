package com.example.ResourceServer.domains;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.AttributeConverter;
import java.lang.reflect.Type;
import java.util.*;
public class MarksConverter implements AttributeConverter<List<Double>,String> {
    @Override
    public String convertToDatabaseColumn(List<Double> integers) {
        return new Gson().toJson(integers);
    }

    @Override
    public List<Double> convertToEntityAttribute(String s) {
        Type listType = new TypeToken<List<Double>>() {}.getType();
        return new Gson().fromJson(s, listType);
    }
}
