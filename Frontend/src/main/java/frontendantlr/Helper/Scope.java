package frontendantlr.Helper;

import java.util.HashMap;
import java.util.Stack;

public class Scope {
    Scope enclosing_scope;
    HashMap<String, Symbol> symbols = new HashMap<>();
    public Stack<Symbol> symbolStack = new Stack<>();
    public HashMap<String, String> referencesInScope = new HashMap<>();
    public Scope(Scope enclosing){
        enclosing_scope = enclosing;
    }
    public Scope(){
        enclosing_scope = null;
    }
    public Symbol resolve(String name){
        if(symbols.containsKey(name)){
            return symbols.get(name);
        }

        if (enclosing_scope != null){
            return enclosing_scope.resolve(name);
        }

        else{
            return null;
        }
    }
    public void bind(Symbol symbol){
        symbols.put(symbol.name, symbol);
        symbol.scope = this;
    }

    public Scope getEnclosing_scope() {
        return enclosing_scope;
    }

    public HashMap<String, Symbol> getSymbols() {
        return symbols;
    }

    public void printSymbols(){
        for(Symbol symbol: symbols.values()){
            System.out.println("Scope : " + symbol.scope + " Name : " + symbol.name);
        }
    }

    public void printVariables(){
        for(Symbol symbol: symbols.values()){
            if(symbol instanceof Variable){
                System.out.println("Scope : " + symbol.getScope() + " Name : " + symbol.getName() + " Value : " + ((Variable<?>) symbol).getValue());
            }
        }
    }
}
