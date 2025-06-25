package pl.janksiegowy.backend.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityRole {

    NONE( 0),       // 0 - brak ról
    SUPPLIER( 1),   // 1 - tylko dostawca
    CUSTOMER( 2),   // 2 - tylko klient
    BOTH( 3);       // 3 - dostawca i klient

    private final int value;

    EntityRole( int value) {
        this.value = value;
    }

    public boolean isSupplier() {
        return this == SUPPLIER || this == BOTH;
    }

    public boolean isCustomer() {
        return this == CUSTOMER || this == BOTH;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static EntityRole fromValue( int value) {
        System.out.println("Trying to convert: " + value); // DEBUG
        for( EntityRole role: EntityRole.values()) {
            if( role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException( "Invalid EntityRole value: "+ value);
    }
}
