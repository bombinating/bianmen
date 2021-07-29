import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

kotlin {
    explicitApi()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        kotlinOptions.allWarningsAsErrors = true
    }
}

dependencies {
    val commonsLang3Version: String by project
    testImplementation(group = "org.apache.commons", name = "commons-lang3", version = commonsLang3Version)
}
