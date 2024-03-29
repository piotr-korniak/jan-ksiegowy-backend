package pl.janksiegowy.backend.entity;

public enum EntityType {
    /**
     * Bank
     */
    B {
        @Override public <T> T accept( EntityTypeVisitor<T> visitor ) {
            return visitor.visitBank();
        }
    },
    /**
     * Contact
     */
    C {
        @Override public <T> T accept( EntityTypeVisitor<T> visitor) {
            return visitor.visitContact();
        }
    },
    R { // Revenue
        @Override public <T> T accept( EntityTypeVisitor<T> visitor) {
            return visitor.visitRevenue();
        }
    },
    S { // Shareholders
        @Override public <T> T accept( EntityTypeVisitor<T> visitor) {
            return visitor.visitShareholders();
        }
    },
    E { // Employee
        @Override public <T> T accept( EntityTypeVisitor<T> visitor) {
            return visitor.visitEmployee();
        }
    };

    public abstract <T> T accept( EntityTypeVisitor<T> visitor);

    interface EntityTypeVisitor<T> {
        T visitContact();
        T visitRevenue();
        T visitShareholders();
        T visitEmployee();
        T visitBank();
    }
}
