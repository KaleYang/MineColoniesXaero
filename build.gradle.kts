plugins {
  id("net.neoforged.moddev") version "2.0.107"
}

group = providers.gradleProperty("mod_group_id").get()
version = providers.gradleProperty("mod_version").get()

base {
  archivesName.set(
    providers.gradleProperty("mod_id").get()
  )
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
  }
}

neoForge {
  version = providers.gradleProperty("neo_version").get()

  runs {
    create("client") {
      client()

      gameDirectory = project.file("run")

      systemProperty(
        "neoforge.enabledGameTestNamespaces",
        providers.gradleProperty("mod_id").get()
      )
    }
  }

  mods {
    create(providers.gradleProperty("mod_id").get()) {
      sourceSet(sourceSets.main.get())
    }
  }
}

repositories {
  mavenCentral()

  maven {
    name = "NeoForge"
    url = uri("https://maven.neoforged.net/releases/")
  }
}

dependencies {

  implementation(
    files(
      "libs/minecolonies-1.1.1294-1.21.1-snapshot.jar"
    )
  )

  implementation(
    files(
      "libs/xaeroworldmap-neoforge-1.21.1-1.44.2.jar"
    )
  )

  implementation(
    files(
      "libs/xaerominimap-neoforge-1.21.1-26.4.2.jar"
    )
  )
}