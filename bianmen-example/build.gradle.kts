plugins {
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot")
    war
}

dependencies {
    val wicketVersion: String by project
    val servlet4Version: String by project
    implementation(project(":bianmen-core"))
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.apache.wicket", name = "wicket-core", version = wicketVersion)
    implementation(group = "org.apache.wicket", name = "wicket-devutils", version = wicketVersion)
    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    providedRuntime(group = "javax.servlet", name = "javax.servlet-api", version = servlet4Version)
}
