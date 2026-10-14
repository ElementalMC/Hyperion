plugins {
    java
    id("io.github.goooler.shadow").version("8.1.8")
}

group = "me.moros"
version = "1.7.3"

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
    implementation("org.bstats", "bstats-bukkit", "3.0.2")
    compileOnly("io.papermc.paper:paper-api:26.2.build.133-stable")
    compileOnly("com.projectkorra:projectkorra:1.13.0")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        dependencies {
            relocate("org.bstats", "me.moros.hyperion.bstats")
        }
        minimize()
    }
    build {
        dependsOn(shadowJar)
    }
    withType<JavaCompile> {
        // Paper 26.x API needs a JDK 25 compiler, but the shadow plugin's minimize() can't read Java 25
        // class files yet, so Hyperion's own classes stay on Java 21 bytecode (runs fine on Java 25).
        options.release.set(21)
        options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
        options.encoding = "UTF-8"
    }
    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
    named<Copy>("processResources") {
        expand("pluginVersion" to project.version)
    }
}
