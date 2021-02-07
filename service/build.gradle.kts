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

tasks.withType<Test> {
    systemProperty("yatspec.output.dir", "$buildDir/reports/yatspec")
    useJUnitPlatform()
}


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

    componentTestImplementation("com.github.nickmcdowall:yatspec:223")
    componentTestImplementation("com.github.nickmcdowall:yatspec-lsd-interceptors:0.3.21")

    // JUnit 5
    componentTestImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0") {
        because("we want to use JUnit 5")
    }
    componentTestImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0") {
        because("Cucumber relies on jupiter-engine to resolve tests")
    }
    componentTestImplementation("org.junit.jupiter:junit-jupiter-params:5.7.0") {
        because("we want to run parameterised tests")
    }

    // Cucumber
    componentTestImplementation("io.cucumber:cucumber-java8:6.9.1") {
        because("we want to use Cucumber JVM")
    }
    componentTestImplementation("io.cucumber:cucumber-junit-platform-engine:6.9.1") {
        because("we want to use Cucumber with JUnit 5")
    }
    componentTestImplementation("io.cucumber:cucumber-spring:6.9.1") {
        because("we want to use dependency injection in our Cucumber tests")
    }
    componentTestImplementation("de.monochromata.cucumber:reporting-plugin:4.0.89") {
        because("we want to see useful Cucumber reports")
    }
}

