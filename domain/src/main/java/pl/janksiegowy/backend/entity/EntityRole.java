package pl.janksiegowy.backend.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityRole {

    NONE( 0),

    SUPPLIER( 1) {
        @Override public boolean isSupplier() {
            return true;
        }
    },

    CUSTOMER( 2) {
        @Override public boolean isCustomer() {
            return true;
        }
    },

    BOTH( 3) {
        @Override public boolean isSupplier() {
            return true;
        }

        @Override public boolean isCustomer() {
            return true;
        }
    };

    private final int value;

    EntityRole(int value) {
        this.value = value;
    }

    public boolean isSupplier() {
        return false;
    };

    public boolean isCustomer() {
        return false;
    };

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static EntityRole fromValue( int value) {
        for( EntityRole role: EntityRole.values()) {
            if( role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException( "Invalid EntityRole value: "+ value);
    }
}
