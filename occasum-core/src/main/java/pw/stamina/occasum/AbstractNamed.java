package pw.stamina.occasum;

public abstract class AbstractNamed implements Named {
    protected final String name;
    protected final String id;

    protected AbstractNamed(String name) {
        this.name = Named.validateName(name);
        this.id = Named.createIdFromName(name);
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final String getId() {
        return id;
    }
}
