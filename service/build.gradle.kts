plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("jacoco")
}

//////////////////////////
// componentTest source set (must exist before dependency configs)
//////////////////////////
val componentTestSourceSet = sourceSets.create("componentTest") {
    compileClasspath += sourceSets.main.get().output + configurations.testRuntimeClasspath.get()
    runtimeClasspath += output + compileClasspath + sourceSets.test.get().runtimeClasspath
}

configurations.named(componentTestSourceSet.implementationConfigurationName) {
    extendsFrom(configurations.testImplementation.get())
    extendsFrom(configurations.runtimeClasspath.get())
}
configurations.named(componentTestSourceSet.runtimeOnlyConfigurationName) {
    extendsFrom(configurations.testRuntimeOnly.get())
}

dependencies {
    implementation(project(":api"))
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework.boot:spring-boot-jackson2")
    implementation("org.springframework.boot:spring-boot-http-converter")
    implementation("org.springframework.boot:spring-boot-restclient")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    testImplementation("org.hamcrest:hamcrest")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    add("componentTestImplementation", "org.springframework.boot:spring-boot-starter-test-classic")
    add("componentTestImplementation", "org.springframework.boot:spring-boot-resttestclient")
    add("componentTestImplementation", "org.springframework.boot:spring-boot-restclient")
    add("componentTestImplementation", "org.springframework.boot:spring-boot-webmvc-test")
    add("componentTestImplementation", "org.springframework.boot:spring-boot-tomcat")
    add("componentTestImplementation", "org.springframework.boot:spring-boot-starter-tomcat")

    add("componentTestImplementation", "io.github.lsd-consulting:lsd-cucumber:9.0.1")
    add("componentTestImplementation", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
    add("componentTestImplementation", "io.github.lsd-consulting:lsd-interceptors:9.0.0")

    add("componentTestImplementation", "org.junit.jupiter:junit-jupiter")
    add("componentTestRuntimeOnly", "org.junit.platform:junit-platform-launcher")

    add("componentTestImplementation", "io.cucumber:cucumber-java8:7.34.9")
    add("componentTestImplementation", "io.cucumber:cucumber-junit-platform-engine:7.34.9")
    add("componentTestImplementation", "io.cucumber:cucumber-spring:7.34.9")
}

val componentTestTask = tasks.register<Test>("componentTest") {
    description = "Runs the component tests"
    group = "verification"
    testClassesDirs = componentTestSourceSet.output.classesDirs
    classpath = componentTestSourceSet.runtimeClasspath
    testLogging.showStandardStreams = true
    systemProperty("lsd.core.report.outputDir", layout.buildDirectory.dir("reports/lsd").get().asFile.absolutePath)
    useJUnitPlatform()
    mustRunAfter(tasks.test)
    finalizedBy(tasks.jacocoTestReport)
}

tasks.check { dependsOn(componentTestTask) }

tasks.test {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.13"
}

tasks.jacocoTestReport {
    executionData.setFrom(fileTree(layout.buildDirectory.dir("jacoco").get().asFile).include("componentTest.exec", "test.exec"))
    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/coverage"))
    }
}
