package pw.stamina.occasum.properties

import pw.stamina.occasum.AbstractNamed

abstract class AbstractProperty protected constructor(name: String) : AbstractNamed(name), Property
