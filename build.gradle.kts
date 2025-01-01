plugins {
    id("java")
}

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
tasks.withType(JavaCompile::class.java) {
    options.encoding = "UTF-8"
}
