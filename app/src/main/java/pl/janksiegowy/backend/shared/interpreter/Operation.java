package pl.janksiegowy.backend.shared.interpreter;


import java.util.Arrays;

public enum Operation {
    ADDITION ('+') {
        @Override public <T> T accept( OperationVisitor<T> visitor ) {
            return visitor.visitAddition();
        }
    },
    SUBTRACTION ('-') {
        @Override public <T> T accept( OperationVisitor<T> visitor ) {
            return visitor.visitSubtraction();
        }
    },
    MULTIPLICATION ('*') {
        @Override public <T> T accept( OperationVisitor<T> visitor ) {
            return visitor.visitMultiplication();
        }
    },
    MULTIPLICATION_INT ('@') {
        @Override public <T> T accept( OperationVisitor<T> visitor ) {
            return visitor.visitMultiplicationInt();
        }
    },
    DIVISION ('/') {
        @Override public <T> T accept( OperationVisitor<T> visitor ) {
            return visitor.visitDivision();
        }
    },
    MAXIMUM ( '^') {
        @Override public <T> T accept( OperationVisitor<T> visitor) {
            return visitor.visitMaximum();
        }
    };

    private final char symbol;

    private Operation( char symbol){
        this.symbol= symbol;
    }

    public static Operation fromSymbol( char symbol){
        for( Operation operation : values())
            if( operation.symbol == symbol)
                return operation;
        throw new IllegalArgumentException( "No operation with symbol: "+ symbol);
    }

    public abstract <T> T accept( OperationVisitor<T> visitor);
    public interface OperationVisitor<T> {
        T visitAddition();
        T visitSubtraction();
        T visitMultiplication();
        T visitMultiplicationInt();
        T visitDivision();
        T visitMaximum();
    }
}
