rootProject.name = "bianmen"

pluginManagement {
    val dependencyManagementPluginVersion: String by settings
    val detektPluginVersion: String by settings
    val dokkaPluginVersion: String by settings
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        id("io.gitlab.arturbosch.detekt") version detektPluginVersion
        id("io.spring.dependency-management") version dependencyManagementPluginVersion
        id("org.jetbrains.dokka") version dokkaPluginVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
    }
}

include("bianmen-core", "bianmen-example")
