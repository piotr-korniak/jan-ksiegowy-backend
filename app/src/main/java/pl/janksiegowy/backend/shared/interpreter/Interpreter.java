package pl.janksiegowy.backend.shared.interpreter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import pl.janksiegowy.backend.shared.interpreter.Operation.OperationVisitor;

public class Interpreter {
    private final Map<String, BigDecimal> context= new HashMap<>();
    private final Pattern pattern= Pattern.compile("\\d+|[+\\-*/]");

    public Interpreter setVariable( String key, BigDecimal value) {
        //System.err.println( "SetVariable ("+key+"): "+ value);
        context.put( key, value);
        return this;
    }

    public BigDecimal getVariable( String key) {
        return context.get( key);
    }

    public Interpreter interpret( String key, String expression) {
        return setVariable( key, interpret( expression));
    }

    public BigDecimal interpret( String expression) {
        final var result= new Object(){ Expression value;};
        final var operator= new Object(){ Operation value;};

        pattern.matcher( expression).results()
            .map( MatchResult::group )
            .forEach( x-> {
                if( x.matches("[+\\-*/]")) {
                    operator.value= Operation.fromSymbol( x.charAt( 0));
                } else {
                    result.value= Optional.ofNullable( result.value)
                        .map( leftValue-> operator.value.accept( new OperationVisitor<Expression>() {
                            @Override public Expression visitAddition() {
                                return new Addition( leftValue, new Variable( x));
                            }
                            @Override public Expression visitSubtraction() {
                                return null;
                            }
                            @Override public Expression visitMultiplication() {
                                return null;
                            }
                            @Override public Expression visitDivision() {
                                return null;
                            }
                        })).orElseGet(()-> new Variable( x));
                }
            });

        return result.value.interpret( context);
    }

}
