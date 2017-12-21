import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("jvm", "1.1.50")
    id("org.junit.platform.gradle.plugin") version "1.0.0"
}

repositories {
    jcenter()
}

val kotlinVersion = "1.1.50"
val junitVersion = "5.0.0"
val springSecurityVersion = "4.2.3.RELEASE"
val springBootVersion = "1.5.7.RELEASE"
val jacksonVersion = "2.9.3"

dependencies {
    implementation(kotlin("stdlib-jre8", kotlinVersion))

    implementation("org.dalesbred:dalesbred:1.2.5")
    implementation("org.postgresql:postgresql:42.1.4")
    implementation("org.hsqldb:hsqldb:2.4.0")

    implementation("io.jsonwebtoken:jjwt:0.8.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    implementation("org.springframework.security:spring-security-crypto:$springSecurityVersion")
    implementation("org.springframework.boot:spring-boot:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-logging:$springBootVersion")

    testImplementation(kotlin("test", kotlinVersion))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}
