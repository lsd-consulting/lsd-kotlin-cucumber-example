plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly("org.springframework.cloud:spring-cloud-starter-openfeign")
    compileOnly(kotlin("stdlib"))
}
