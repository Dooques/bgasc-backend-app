plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "4.0.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.dooques"
version = "0.0.1-SNAPSHOT"
description = "Backend for BGASC app"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-restclient")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:0.7.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.21.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Google APIs
    implementation("com.google.api-client:google-api-client:2.8.0")
    implementation("com.google.auth:google-auth-library-bom:1.30.1")
    implementation("com.google.auth:google-auth-library-oauth2-http")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.39.0")
    implementation("com.google.apis:google-api-services-sheets:v4-rev20250106-2.0.0")
    implementation("com.google.firebase:firebase-admin:9.7.0")

	testImplementation("org.springframework.boot:spring-boot-starter-restclient-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit6")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
