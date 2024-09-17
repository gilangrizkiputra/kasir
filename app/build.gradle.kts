plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.project.kasirku"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.kasirku"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Navigation and Animation
    implementation("androidx.navigation:navigation-compose:2.7.3")
    implementation("androidx.compose.animation:animation:1.5.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Date Picker
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")

    // Desugar for Java 8+ APIs
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Icons Extended
    implementation("androidx.compose.material:material-icons-extended")


//    //read and write excel pdf
//    implementation("org.apache.poi:poi:5.2.3")
//    implementation("org.apache.poi:poi-ooxml:5.2.3")
//    implementation("androidx.activity:activity-compose:1.7.0")
}