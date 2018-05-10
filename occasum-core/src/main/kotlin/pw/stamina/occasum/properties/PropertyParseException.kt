package pw.stamina.occasum.properties

class PropertyParseException : Exception {
    val input: String

    constructor(message: String, input: String) : super(message) {
        this.input = input
    }

    constructor(message: String, cause: Throwable, input: String) : super(message, cause) {
        this.input = input
    }

    constructor(cause: Throwable, input: String) : super(cause) {
        this.input = input
    }
}
