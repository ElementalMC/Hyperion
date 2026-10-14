plugins {
    java
    id("com.gradleup.shadow").version("9.6.1")
}

group = "me.moros"
version = "1.8.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.bstats:bstats-bukkit:3.0.2")
    compileOnly("io.papermc.paper:paper-api:26.2.build.133-stable")
    compileOnly("com.projectkorra:projectkorra:1.13.2")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        relocate("org.bstats", "me.moros.hyperion.bstats")
        minimize()
    }
    build {
        dependsOn(shadowJar)
    }
    withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
        options.encoding = "UTF-8"
    }
    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
    named<Copy>("processResources") {
        inputs.property("pluginVersion", project.version)
        expand("pluginVersion" to project.version)
    }
}
