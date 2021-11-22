plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot") version "2.3.7.RELEASE"
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

    //////////////////////////////////
    // Component test dependencies
    componentTestImplementation("org.springframework.boot:spring-boot-starter-test")

    componentTestImplementation("io.github.lsd-consulting:lsd-cucumber:0.1.6") {
        because("we want to include the Cucumber scenarios in the LSDs")
    }

    componentTestImplementation("io.github.lsd-consulting:lsd-interceptors:1.0.18") {
        because("we want to include the Cucumber scenarios in the LSDs")
    }

    // JUnit 5
    componentTestImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0") {
        because("we want to use JUnit 5")
    }
    componentTestImplementation("org.junit.jupiter:junit-jupiter-params:5.7.0") {
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

