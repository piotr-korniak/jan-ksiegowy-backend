package pl.janksiegowy.backend.declaration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.janksiegowy.backend.salary.WageIndicatorCode;

@Converter
public class DeclarationElementConverter implements AttributeConverter<DeclarationElementCode, String> {

    @Override
    public String convertToDatabaseColumn( DeclarationElementCode attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public DeclarationElementCode convertToEntityAttribute( String dbData) {
        return dbData == null ? null : DeclarationElementCode.valueOf( dbData);
    }
}