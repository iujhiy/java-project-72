plugins {
    id ("org.sonarqube") version "7.0.1.6134"
    id("com.github.ben-manes.versions") version "0.52.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
    checkstyle
    jacoco
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.11.0")
        }
    }
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "hexlet.code.App"
}

sonar {
    properties {
        property("sonar.projectKey", "iujhiy_java-project-72")
        property("sonar.organization", "iujhiy")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("com.h2database:h2:2.2.224")
    implementation(platform("com.konghq:unirest-java-bom:4.5.1"))
    implementation("com.konghq:unirest-java-core")
    implementation("com.konghq:unirest-modules-jackson")
    implementation("io.javalin:javalin:6.6.0")
    implementation("io.javalin:javalin-bundle:6.6.0")
    implementation("io.javalin:javalin-rendering:6.6.0")
    implementation("com.zaxxer:HikariCP:7.0.2")
    implementation("org.postgresql:postgresql:42.7.7")
    implementation("gg.jte:jte:3.2.1")
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    compileOnly ("org.projectlombok:lombok:1.18.30")
    runtimeOnly("com.h2database:h2:2.2.224")
    annotationProcessor ("org.projectlombok:lombok:1.18.30")
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}

jacoco {
    toolVersion = "0.8.13"
    reportsDirectory = layout.buildDirectory.dir("customJacocoReportDir")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}