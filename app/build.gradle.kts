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

val junitBomVersion = "5.10.0"
val assertjVersion = "3.27.3"
val mockwebserverVersion = "4.12.0"
val h2Version = "2.4.240"
val unirestBomVersion = "4.7.1"
val unirestCoreVersion = "4.7.1"
val unirestJacksonVersion = "4.7.1"
val javalinVersion = "6.6.0"
val hikariCPVersion = "7.0.2"
val postgresqlVersion = "42.7.9"
val jteVersion = "3.2.1"
val jsoupVersion = "1.22.1"
val okhttpVersion = "4.12.0"
val lombokVersion = "1.18.30"

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitBomVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation("com.squareup.okhttp3:mockwebserver:$mockwebserverVersion")
    testImplementation("com.h2database:h2:$h2Version")

    implementation(platform("com.konghq:unirest-java-bom:$unirestBomVersion"))
    implementation("com.konghq:unirest-java-core:$unirestCoreVersion")
    implementation("com.konghq:unirest-modules-jackson:$unirestJacksonVersion")

    implementation("io.javalin:javalin:$javalinVersion")
    implementation("io.javalin:javalin-bundle:$javalinVersion")
    implementation("io.javalin:javalin-rendering:$javalinVersion")

    implementation("com.zaxxer:HikariCP:$hikariCPVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("gg.jte:jte:$jteVersion")
    implementation("org.jsoup:jsoup:$jsoupVersion")
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")

    compileOnly ("org.projectlombok:lombok:$lombokVersion")
    runtimeOnly("com.h2database:h2:$h2Version")
    annotationProcessor ("org.projectlombok:lombok:$lombokVersion")
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