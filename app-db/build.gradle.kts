import org.liquibase.gradle.Activity

plugins {
    kotlin("jvm") version "1.4.31"
    id("org.liquibase.gradle") version "2.0.3"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.h2database:h2:1.4.200")
    liquibaseRuntime("ch.qos.logback:logback-core:1.2.3")
    liquibaseRuntime("ch.qos.logback:logback-classic:1.2.3")
}

liquibase {
    activities.register("main") {
        this.arguments =  mapOf(
            "changeLogFile" to "src/main/db/initial.xml",
            "url" to System.getenv("DB_URL")!!,
            "username" to System.getenv("DB_USERNAME")!!,
            "password" to System.getenv("DB_PASSWORD")!!
        )
    }
    runList = "main"
}
