package pw.stamina.occasum

import java.util.*

interface Named {

    val name: String

    val id: String

    companion object {

        //TODO Doc
        fun validateName(name: String): String {
            require(name.isNotBlank()) { "name cannot be blank" }
            require(!name.contains(".")) { "name may not contain periods ('.')" }

            return name
        }

        //TODO Doc
        fun createIdFromName(name: String): String {
            return name
                    .toLowerCase(Locale.ROOT)
                    .replace("(\\s|_)+".toRegex(), "_")
        }
    }
}
