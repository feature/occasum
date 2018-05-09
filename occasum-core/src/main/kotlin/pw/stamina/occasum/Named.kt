package pw.stamina.occasum

import java.util.*

interface Named {

    val name: String

    val id: String

    companion object {

        //TODO Doc
        fun validateName(name: String): String {
            if (name.isEmpty()) {
                throw IllegalArgumentException("name cannot be empty")
            } else if (name.contains(".")) {
                throw IllegalArgumentException("name may not contain periods ('.')")
            }

            return name
        }

        //TODO Doc
        fun createIdFromName(name: String): String {
            var id = name

            id = id.toLowerCase(Locale.ROOT)
            id = id.replace("(\\s|_)+".toRegex(), "_")

            return id
        }
    }
}
