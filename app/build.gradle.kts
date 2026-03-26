plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "app.sprouttales"
    compileSdk = 35

    defaultConfig {
        applicationId = "app.sprouttales"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.4"
        resourceConfigurations += listOf("zh", "zh-rCN")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging { // replace deprecated packagingOptions
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    // Only create signing config when keystore props are provided
    val hasStore = listOf("storeFile", "storePassword", "keyAlias", "keyPassword")
        .all { (findProperty(it)?.toString()?.isNotBlank() == true) }

    if (hasStore) {
        signingConfigs {
            create("release") {
                storeFile = file(findProperty("storeFile")!!.toString())
                storePassword = findProperty("storePassword")!!.toString()
                keyAlias = findProperty("keyAlias")!!.toString()
                keyPassword = findProperty("keyPassword")!!.toString()
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            if (hasStore) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.12.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.navigation:navigation-compose:2.8.5")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // Lifecycle & runtime
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // Room (app uses DB via data-store module)
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Media3 for session/notification controls
    implementation("androidx.media3:media3-session:1.4.1")
    implementation("androidx.media3:media3-common:1.4.1")

    // JSON serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // Modules
    implementation(project(":core-model"))
    implementation(project(":data-store"))
    implementation(project(":tts-engine"))
    implementation(project(":media-playback"))
    implementation(project(":importers:txt-importer"))
    implementation(project(":importers:epub-importer"))
    implementation(project(":picturebook"))
}
