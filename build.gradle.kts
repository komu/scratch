plugins {
    kotlin("jvm") version "1.4.30"
    id("org.junit.platform.gradle.plugin") version "1.0.0"
    id("org.springframework.boot") version "2.0.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.5.RELEASE"
}

repositories {
    jcenter()
}

val junitVersion = "5.0.0"
val springSecurityVersion = "4.2.3.RELEASE"
val jacksonVersion = "2.11.2"

springBoot {
    mainClassName = "graphite.FeedGraphiteKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.dalesbred:dalesbred:1.2.5")
    implementation("org.postgresql:postgresql:42.1.4")
    implementation("org.hsqldb:hsqldb:2.4.0")

    implementation("org.jetbrains.lets-plot:lets-plot-common:2.0.0")
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:2.0.0")
    implementation("org.jetbrains.lets-plot-kotlin:lets-plot-kotlin-api:1.2.0")

    implementation("org.eclipse.jgit:org.eclipse.jgit:5.10.0.202012080955-r")

    implementation("org.apache.sis.core:sis-referencing:1.0")
    runtimeOnly("org.apache.sis.non-free:sis-embedded-data:1.0")

    implementation("io.jsonwebtoken:jjwt:0.8.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("org.apache.httpcomponents:httpclient")

    runtimeOnly("org.glassfish.jaxb:jaxb-runtime:2.3.2")

    implementation("org.springframework.security:spring-security-crypto:$springSecurityVersion")
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-starter-logging")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}
