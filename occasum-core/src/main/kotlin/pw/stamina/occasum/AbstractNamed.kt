package pw.stamina.occasum

abstract class AbstractNamed protected constructor(name: String) : Named {

    final override val name: String
    final override val id: String

    init {
        Named.validateName(name)

        this.name = name
        this.id = Named.createIdFromName(name)
    }
}
