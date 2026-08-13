package com.ayub.assessment.api.utility;

import java.math.BigDecimal;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptedBigDecimalConverter
        implements AttributeConverter<BigDecimal, String> {

    @Override
    public String convertToDatabaseColumn(BigDecimal value) {
        if (value == null) {
            return null;
        }

        return TripleDesUtil.encrypt(
                value.toPlainString());
    }

    @Override
    public BigDecimal convertToEntityAttribute(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        return new BigDecimal(
                TripleDesUtil.decrypt(value));
    }
}