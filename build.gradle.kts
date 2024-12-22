import java.io.BufferedReader

plugins {
    id("java")
}

// Functions:
fun getGitTag(): String = executeCommand("git describe --tags --always")

private fun executeCommand(command: String): String {
    return try {
        val process = ProcessBuilder(command.split(" "))
            .redirectErrorStream(true)
            .start()
        process.inputStream.bufferedReader().use(BufferedReader::readText).trim()
    } catch (e: Exception) {
        "Error: ${e.message}"
    }
}

// Project properties:
project.version = (project.findProperty("version") as String)
    .replace("{gitCommitTag}", getGitTag())

// Toolchains:
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

// Dependencies:
repositories {
    mavenCentral()
}

val annotationProc: Configuration by configurations.creating {
    configurations.compileOnly.get().extendsFrom(this)
    configurations.annotationProcessor.get().extendsFrom(this)
    configurations.testCompileOnly.get().extendsFrom(this)
    configurations.testAnnotationProcessor.get().extendsFrom(this)
}

dependencies {
    annotationProc("org.projectlombok:lombok:1.18.34")
}

// Tasks:
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}