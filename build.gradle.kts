plugins {
    kotlin("jvm") version "2.4.20" apply false
    kotlin("plugin.spring") version "2.4.20" apply false
    id("org.springframework.boot") version "4.1.1" apply false
    id("io.spring.dependency-management") version "1.1.7"
    id("java")
}

group = "io.github.lsd-consulting"
rootProject.version = System.getenv("CI_PIPELINE_ID") ?: "0.0.0-SNAPSHOT"
println("Build Version = ${project.version}")

allprojects {
    group = "io.github.lsd-consulting"
    version = rootProject.version

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    the<JavaPluginExtension>().toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    the<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension>().jvmToolchain(21)

    // Boot BOM pins older coroutines; lsd-core 9 needs 1.11+
    extra["kotlin-coroutines.version"] = "1.11.0"

    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2025.1.3")
            mavenBom("org.jetbrains.kotlin:kotlin-bom:2.4.20")
            mavenBom("org.junit:junit-bom:6.1.3")
        }
    }
}
