plugins {
    kotlin("jvm") version "2.2.21"
    alias(libs.plugins.kotlin.serialization)
    application
}

group = "com.sergiocarrilho"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.sergiocarrilho.AgentKt")
}

repositories {
    mavenCentral()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.koog.agents)
    implementation(libs.koog.tools)
    implementation(libs.koog.executor.openai.client)
    implementation(libs.koog.features.event.handler)
    implementation(libs.okio)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
