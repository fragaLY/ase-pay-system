plugins {
    java
    application
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("com.google.cloud.tools.jib") version "3.4.0"
    id("org.flywaydb.flyway") version "10.0.0"
}

group = "by.vk"
version = "1.0.0-RC1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

springBoot {
    buildInfo()
}

repositories {
    mavenCentral()
}

dependencies {
    //region implementation
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework:spring-context-indexer")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jta-atomikos:2.7.17")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
    implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
    implementation("org.flywaydb:flyway-core")
    //endregion
    //region lombok
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.projectlombok:lombok")
    //endregion
    //region postgres
    runtimeOnly("org.postgresql:postgresql")
    //endregion
}

tasks.withType<Test> {
    useJUnitPlatform()
}

object JVMProps {
    const val XMX = "512m"
    const val XMS = "256m"
    const val XSS = "256k"
    const val MAX_METASPACE_SIZE = "128m"
    const val MAX_DIRECT_MEMORY_SIZE = "128m"
    const val HEAPDUMP_PATH = "/opt/tmp/heapdump.bin"
    const val MAX_RAM_PERCENTAGE = "80"
    const val INITIAL_RAM_PERCENTAGE = "50"
}

jib {
    setAllowInsecureRegistries(false)
    to {
        image = "fragaly/asepay-system"
        tags = setOf("$version", "latest")
    }
    from {
        image = "gcr.io/distroless/java21-debian12"
    }
    container {
        jvmFlags = listOf(
                "-Xss${JVMProps.XSS}",
                "-Xmx${JVMProps.XMX}",
                "-Xms${JVMProps.XMS}",
                "-XX:MaxMetaspaceSize=${JVMProps.MAX_METASPACE_SIZE}",
                "-XX:MaxDirectMemorySize=${JVMProps.MAX_DIRECT_MEMORY_SIZE}",
                "-XX:MaxRAMPercentage=${JVMProps.MAX_RAM_PERCENTAGE}",
                "-XX:InitialRAMPercentage=${JVMProps.INITIAL_RAM_PERCENTAGE}",
                "-XX:+HeapDumpOnOutOfMemoryError",
                "-XX:HeapDumpPath=${JVMProps.HEAPDUMP_PATH}",
                "-XX:+UseContainerSupport",
                "-XX:+OptimizeStringConcat",
                "-XX:+UseStringDeduplication",
                "-XX:+ExitOnOutOfMemoryError",
                "-XX:+AlwaysActAsServerClassMachine")
        ports = listOf("8080")
        labels.set(mapOf("appname" to application.applicationName, "version" to version.toString(), "maintainer" to "Vadzim Kavalkou <vadzim.kavalkou@gmail.com>"))
        creationTime.set("USE_CURRENT_TIMESTAMP")
    }
}