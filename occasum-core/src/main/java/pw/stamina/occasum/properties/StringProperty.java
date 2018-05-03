package pw.stamina.occasum.properties;

public final class StringProperty extends ParameterizedProperty<String> {

    protected StringProperty(String name, String value) {
        super(name, value);
    }

    @Override
    public void set(String value) {

        // TODO: Process String

        super.set(value);
    }

    @Override
    public void parseAndSet(String input) {
        set(input);
    }
}
