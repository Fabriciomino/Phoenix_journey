plugins {
    id("com.android.application") version "8.5.0" apply true // Actualiza a la versión 8.5.0
    kotlin("android") version "1.9.10" apply true // Plugin Kotlin Android
}

android {
    namespace = "com.example.myapplication_phoenix_journey"
    compileSdk = 34 // Versión SDK para compilar

    defaultConfig {
        applicationId = "com.example.myapplication_phoenix_journey"
        minSdk = 24 // Mínima versión SDK soportada
        targetSdk = 34 // Versión SDK objetivo
        versionCode = 1 // Código de versión para actualizaciones
        versionName = "1.0" // Nombre de versión legible

        // Configuración para pruebas
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Desactiva la minimización en release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true // Habilita depuración para esta variante
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Soporte para Java 8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8" // Versión de Java para Kotlin
    }

    buildFeatures {
        viewBinding = true // Habilita ViewBinding
    }
}

dependencies {
    // Dependencias de la aplicación
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

        implementation ("androidx.room:room-runtime:2.5.0")
    implementation(libs.room.common)
    annotationProcessor ("androidx.room:room-compiler:2.5.0")




    // Dependencias para pruebas locales y de instrumentación
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Dependencias opcionales sugeridas (comenta si no las necesitas)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2") // Para LiveData y ViewModel
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3") // Navegación
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3") // Navegación UI

    // Otras dependencias opcionales si las necesitas
    // implementation("com.squareup.retrofit2:retrofit:2.9.0")  // Si usas Retrofit
    // implementation("androidx.room:room-runtime:2.4.2")  // Si usas Room para base de datos
}
