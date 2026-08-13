package com.ayub.assessment.api.utility;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptedStringConverter
        implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String value) {
        return TripleDesUtil.encrypt(value);
    }

    @Override
    public String convertToEntityAttribute(String value) {
        return TripleDesUtil.decrypt(value);
    }
}