pluginManagement {
    repositories {
        google {
            content {
                // Incluye grupos específicos relacionados con Android y Google
                includeGroupByRegex("com\\.android.*") // Plugins y dependencias de Android
                includeGroupByRegex("com\\.google.*") // Dependencias de Google
                includeGroupByRegex("androidx.*")     // Dependencias de AndroidX
            }
        }
        mavenCentral()       // Repositorio Maven Central
        gradlePluginPortal() // Portal de plugins de Gradle
    }
}

dependencyResolutionManagement {
    // Configuración para evitar repositorios definidos en subproyectos
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()       // Repositorio de Google
        mavenCentral() // Repositorio Maven Central
    }
}

// Nombre del proyecto raíz
rootProject.name = "My Application_Phoenix_Journey"

// Incluye el módulo de la aplicación
include(":app")
