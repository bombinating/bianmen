rootProject.name = "bianmen"

pluginManagement {
    val detektPluginVersion: String by settings
    val dokkaPluginVersion: String by settings
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        id("io.gitlab.arturbosch.detekt") version detektPluginVersion
        id("org.jetbrains.dokka") version dokkaPluginVersion
    }
}

include("bianmen-core", "bianmen-example")
