package frontendantlr.Helper;

public class Variable<T> extends Symbol{
    T value;
    String description = "";
    public Variable(String n, Scope s, T v) {
        super(n, s);
        value = v;
    }

    public Variable(String n, Scope s, T v, String op){
        super(n, s);
        value = v;
        description = op;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T nVal){
        value = nVal;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String nVal){
        this.description = nVal;
    }
}
