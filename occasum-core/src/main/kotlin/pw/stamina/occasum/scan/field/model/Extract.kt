package pw.stamina.occasum.scan.field.model

@Retention
@Target(AnnotationTarget.FIELD)
annotation class Extract(vararg val value: String)
