# Building Hyperion

Hyperion is compiled against **ProjectKorra 1.13.2** and **Paper 26.2** (Minecraft 26.2+)
and targets **Java 25**. The Gradle wrapper (9.7.1) runs on JDK 25, so a single JDK 25
install is all you need.

ProjectKorra `1.13.2` is not yet published to Maven Central (Central currently
only has up to `1.12.x`). Until it is released there, you must build it from the
`wip` branch of the ProjectKorra fork once and install it into your local Maven
repository. Hyperion's `build.gradle.kts` already includes `mavenLocal()`, so
Gradle picks it up automatically.

## Prerequisites

- JDK 25
- Maven 3.8+ (only for step 1)

## 1. Install ProjectKorra 1.13.2 into your local Maven repo (one time)

```bash
git clone https://github.com/ElementalMC/ProjectKorra.git
cd ProjectKorra
git checkout wip
mvn install -DskipTests -Dgpg.skip=true -Dmaven.javadoc.skip=true -Dmaven.source.skip=true
```

This installs `com.projectkorra:projectkorra:1.13.2` into `~/.m2`. The
`-Dgpg.skip` / `-Dmaven.javadoc.skip` / `-Dmaven.source.skip` flags skip the
release-signing/publishing steps that would otherwise fail on a normal dev
machine.

> Already have the fork cloned? Just `git checkout wip && git pull` in it and run
> the `mvn install ...` line above.

## 2. Build Hyperion

```bash
./gradlew shadowJar
```

Output: `build/libs/Hyperion-1.8.1.jar`

Once ProjectKorra 1.13.2 is published to Maven Central, step 1 will no longer be
necessary.
