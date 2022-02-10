rootProject.name = "book-service"

pluginManagement {
    val springBootVersion: String by settings
    val lombokPluginVersion: String by settings
    plugins {
        id("java")
        id("org.springframework.boot") version springBootVersion
        id("io.freefair.lombok") version lombokPluginVersion
        `java-library`
    }
}