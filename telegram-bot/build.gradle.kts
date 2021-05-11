import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.20"
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("plugin.spring") version "1.4.10"

}

repositories {
    // Use Maven central for resolving dependencies.
    mavenCentral()
    jcenter() // deprecated jcenter for koin
}

val jacksonVersion = "2.12.1"
val xCoroutinesVersion = "1.4.2"
val logbackVersion = "1.2.3"
val slf4jVersion = "1.7.30"
val junitVersion = "5.7.1"
val tgbotapiVersion = "0.32.5"
val selenideVersion = "5.18.1"
val flexmarkVersion = "0.62.2"
val h2r2dbcVersion = "0.8.4.RELEASE"

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation(platform("com.fasterxml.jackson:jackson-bom:$jacksonVersion"))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")


    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$xCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$xCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")


    implementation("ch.qos.logback:logback-core:$logbackVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")

    implementation("com.google.guava:guava:29.0-jre")
    implementation("dev.inmo:tgbotapi:$tgbotapiVersion")
    implementation("com.codeborne:selenide:$selenideVersion")
    implementation("com.vladsch.flexmark:flexmark-all:$flexmarkVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.r2dbc:r2dbc-h2:$h2r2dbcVersion")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testRuntimeOnly(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
    test {
        useJUnitPlatform()
    }
}
