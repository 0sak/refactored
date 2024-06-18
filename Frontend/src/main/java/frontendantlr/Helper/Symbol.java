package frontendantlr.Helper;

public class Symbol {
    String name;
    Scope scope;
    Symbol(String n, Scope s){
        name = n;
        scope = s;
    }
    public String getName() {

        return name;
    }
    public Scope getScope() {
        return scope;
    }
}
