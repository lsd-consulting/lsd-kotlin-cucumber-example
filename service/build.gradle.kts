plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot") version "2.5.6"
    id("jacoco")
}

//////////////////////////
// componentTest source set
//////////////////////////

sourceSets.create("componentTest") {
    withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
        kotlin.srcDir("src/componentTest/kotlin")
        resources.srcDir("src/componentTest/resources")
        compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
        runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
    }
}

val componentTest = task<Test>("componentTest") {
    description = "Runs the component tests"
    group = "verification"
    testClassesDirs = sourceSets["componentTest"].output.classesDirs
    classpath = sourceSets["componentTest"].runtimeClasspath
    testLogging.showStandardStreams = true
    systemProperty("lsd.core.report.outputDir", "$buildDir/reports/lsd")
    useJUnitPlatform()
    mustRunAfter(tasks["test"])
    finalizedBy(tasks.jacocoTestReport)
}

val componentTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}
val componentTestRuntimeOnly: Configuration by configurations.getting {
    extendsFrom(configurations.testRuntimeOnly.get())
}

configurations["componentTestImplementation"].extendsFrom(configurations.runtimeOnly.get())

tasks.check { dependsOn(componentTest) }

//////////////////////////
// dependencies
//////////////////////////

dependencies {
    implementation(project(":api"))
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.hamcrest:hamcrest-core:2.2")
    testImplementation("org.junit.platform:junit-platform-commons:1.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    //////////////////////////////////
    // Component test dependencies
    componentTestImplementation("org.springframework.boot:spring-boot-starter-test")

    componentTestImplementation("io.github.lsd-consulting:lsd-cucumber:2.+") {
        because("we want to include the Cucumber scenarios in the LSDs")
    }

    componentTestImplementation("io.github.lsd-consulting:lsd-interceptors:2.+") {
        because("we want to include the Cucumber scenarios in the LSDs")
    }

    // JUnit 5
    componentTestImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2") {
        because("we want to use JUnit 5")
    }
    componentTestImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2") {
        because("we want to run parameterised tests")
    }

    // Cucumber
    componentTestImplementation("io.cucumber:cucumber-java8:6.11.0") {
        because("we want to use Cucumber JVM")
    }
    componentTestImplementation("io.cucumber:cucumber-junit-platform-engine:6.11.0") {
        because("we want to use Cucumber with JUnit 5")
    }
    componentTestImplementation("io.cucumber:cucumber-spring:6.11.0") {
        because("we want to use dependency injection in our Cucumber tests")
    }
    componentTestImplementation("de.monochromata.cucumber:reporting-plugin:4.0.103") {
        because("we want to see useful Cucumber reports")
    }
}


tasks.test {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    executionData(
        file("${project.buildDir}/jacoco/componentTest.exec")
    )
    reports {
        xml.isEnabled = true
        html.isEnabled = true
        html.setDestination(project.provider { File("${project.buildDir}/reports/coverage") })
    }
}
