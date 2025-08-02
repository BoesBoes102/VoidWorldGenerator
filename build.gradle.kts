plugins {
    java
}

group = "com.boes.voidworld"
version = "1.1"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21)) // Paper requires Java 17+
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
}
