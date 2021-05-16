val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project
val koin_version: String by project
val exposed_version: String by project
val hikari_version: String by project
val h2_version: String by project
val kotlinx_serialization_version: String by project
val junit5_version: String by project
val mockk_version: String by project
val truth_version: String by project
val coroutines_test_version: String by project
val firebase_admin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.4.30"
    kotlin("plugin.serialization") version "1.4.30"
    id("name.remal.check-dependency-updates") version "1.3.1"
    //gradle checkDependencyUpdates
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven {
        url = uri("https://kotlin.bintray.com/ktor")
        url = uri("https://kotlin.bintray.com/kotlinx")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-locations:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version")

    // Light-weight relation in-memory database
    implementation("com.h2database:h2:$h2_version")
    // ORM
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    // High performance JDBC connection pooling
    implementation("com.zaxxer:HikariCP:$hikari_version")

    // Service locator
    implementation("org.koin:koin-ktor:$koin_version")

    // Firebase
    implementation("com.google.firebase:firebase-admin:$firebase_admin_version")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("com.google.truth:truth:$truth_version")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3")//TODO WHY DOESN"T THE IMPORT WORK WITH $?
    testImplementation("org.junit.jupiter:junit-jupiter:$junit5_version")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junit5_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

tasks.create("stage") {
    dependsOn("installDist")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}