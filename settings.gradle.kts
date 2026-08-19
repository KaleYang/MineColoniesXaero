pluginManagement {
  repositories {
    maven {
      name = "NeoForge"
      url = uri("https://maven.neoforged.net/releases/")
    }

    gradlePluginPortal()
    mavenCentral()
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()

    maven {
      name = "NeoForge"
      url = uri("https://maven.neoforged.net/releases/")
    }
  }
}

rootProject.name = "MineColoniesXaeroProbe"