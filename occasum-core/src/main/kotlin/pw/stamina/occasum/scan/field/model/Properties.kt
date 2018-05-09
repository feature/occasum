package pw.stamina.occasum.scan.field.model

@Retention
@Target(AnnotationTarget.FIELD)
annotation class Properties(val value: String = "")
