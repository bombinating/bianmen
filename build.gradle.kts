plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("io.gitlab.arturbosch.detekt")
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

subprojects {

    apply {
        plugin("jacoco")
        plugin("kotlin")
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jetbrains.dokka")
    }

    sourceSets {
        main {
            resources {
                srcDir("src/main/kotlin")
                exclude("**/*.kt")
            }
        }
        test {
            resources {
                srcDir("src/test/kotlin")
                exclude("**/*.kt")
            }
        }
    }

    dependencies {
        val junitVersion: String by project
        val kotlinVersion: String by project
        val kotlinxHtmlVersion: String by project
        val logbackVersion: String by project
        val servlet31Version: String by project
        val wicketVersion: String by project
        /*
         * Kotlin
         */
        implementation(kotlin("stdlib"))
        /*
         * Wicket
         */
        implementation(group = "org.apache.wicket", name = "wicket-core", version = wicketVersion)
        implementation(group = "org.apache.wicket", name = "wicket-extensions", version = wicketVersion)
        implementation(group = "javax.servlet", name = "javax.servlet-api", version = servlet31Version)
        implementation(group = "org.wicketstuff", name = "wicketstuff-minis", version = wicketVersion)
        /*
         * Kotlin HTML DSL
         */
        implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-html-jvm", version = kotlinxHtmlVersion)
        /*
         * Testing
         */
        testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test-common", version = kotlinVersion)
        testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test-junit5", version = kotlinVersion)
        testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-params", version = junitVersion)
        testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = junitVersion)
        testRuntimeOnly(group = "ch.qos.logback", name = "logback-classic", version = logbackVersion)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

}
