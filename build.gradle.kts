import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

allprojects {
    group = "me.sup2is.kotlinreactiveboard"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    extra["springCloudVersion"] = "2021.0.3"
}

java.sourceCompatibility = JavaVersion.VERSION_11

subprojects {

    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencies {
        implementation("io.github.microutils:kotlin-logging:1.12.5")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("org.springframework.cloud:spring-cloud-starter-config")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

project(":api") {
    dependencies {
        implementation(project(":domain"))

        implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("io.micrometer:micrometer-registry-prometheus")
    }
}

project(":config-server") {
    dependencies {
        implementation("org.springframework.cloud:spring-cloud-config-server")
    }
}

project(":domain") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
    }
}

tasks.bootJar {
    enabled = false
}
