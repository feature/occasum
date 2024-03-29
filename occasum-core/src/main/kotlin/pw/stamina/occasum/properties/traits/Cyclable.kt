package pw.stamina.occasum.properties.traits

interface Cyclable {

    /**
     * Cycles to the next available element. If there are no more available
     * elements to cycle to, calling the method does nothing.
     */
    fun cycleForward()

    /**
     * Cycles to the previous available element. If there are no previous
     * elements available to cycle to, calling the method does nothing.
     */
    fun cycleBackward()
}
