package pw.stamina.occasum.properties.enums

/*
internal class EnumPropertyTests : ParameterizedPropertyTests<DummyEnum>() {

    @ParameterizedTest
    @CsvSource("foo, FOO", "bar, BAR", "foo_bar, FOO_BAR")
    fun of_givenValidNameAndEnumValue_shouldCreateEnumPropertyInstance(name: String, dummy: DummyEnum) {
        EnumProperty.from(name, dummy)
    }

    override fun provideProperty(): ParameterizedProperty<DummyEnum> {
        return EnumProperty.from("dummy", DummyEnum.FOO)
    }

    override fun shufflePropertyValue(property: ParameterizedProperty<DummyEnum>, random: Random) {
        val values = DummyEnum.values()
        property.set(values[random.nextInt(values.size)])
    }
}
*/