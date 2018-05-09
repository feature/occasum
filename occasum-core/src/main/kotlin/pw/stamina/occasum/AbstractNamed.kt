package pw.stamina.occasum

abstract class AbstractNamed protected constructor(name: String) : Named {

    final override val name: String = Named.validateName(name)
    final override val id: String = Named.createIdFromName(name)
}
