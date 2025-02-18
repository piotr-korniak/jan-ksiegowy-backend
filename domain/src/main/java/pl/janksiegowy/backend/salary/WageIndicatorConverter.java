package pl.janksiegowy.backend.salary;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class WageIndicatorConverter implements AttributeConverter<WageIndicatorCode, String> {

    @Override
    public String convertToDatabaseColumn(WageIndicatorCode attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public WageIndicatorCode convertToEntityAttribute(String dbData) {
        return dbData == null ? null : WageIndicatorCode.valueOf(dbData);
    }
}