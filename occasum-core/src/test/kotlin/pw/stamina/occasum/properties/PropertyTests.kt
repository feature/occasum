package pw.stamina.occasum.properties

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import java.util.*

abstract class PropertyTests<T : Property<*>> {
    protected lateinit var property: T

    @BeforeEach
    internal fun setupPropertyFromProvider() {
        property = provideProperty()
    }

    @RepeatedTest(10)
    @DisplayName("reset() -> isDefault() returns true")
    internal fun isDefault_afterResetHasBeenInvoked_shouldReturnTrue() {
        shufflePropertyValue(property, RANDOM)
        property.reset()
        assertTrue(property.isDefault)
    }

    protected abstract fun provideProperty(): T

    protected abstract fun shufflePropertyValue(property: T, random: Random)

    companion object {
        private val RANDOM = Random()
    }
}
