plugins {
    java
    `java-library`
    id("org.springframework.boot")
    id("io.freefair.lombok")
}

val springBootVersion: String by project
val lombokVersion: String by project
val jupiterVersion: String by project
val postgresqlVersion: String by project
val loadtestVersion: String by project
val assertJVersion: String by project
val loadtestJmeterVersion: String by project

repositories {
    mavenCentral()
}

configurations.all {
    /* настройка логирования log4j[2] -> slf4j: начало */
    exclude(group = "ch.qos.logback")
    exclude(group = "org.slf4j", module = "slf4j-log4j12") // бридж в обратную сторону не нужен
    exclude(group = "org.slf4j", module = "log4j-over-slf4j") // потому что пользуемся бриджем log4j-to-slf4j
    exclude(group = "org.apache.logging.log4j", module = "log4j-slf4j-impl") // реализация log4j2 не нужна
    exclude(group = "log4j", module = "log4j") // / реализация log4j не нужна
    /* настройка логирования log4j[2] -> slf4j: конец */
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:$springBootVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testImplementation("org.loadtest4j:loadtest4j:$loadtestVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    testRuntimeOnly("org.loadtest4j.drivers:loadtest4j-jmeter:$loadtestJmeterVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}