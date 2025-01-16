plugins {
    id("com.android.application") // Android Application Plugin
    id("com.google.gms.google-services") // Google Services Plugin
}

android {
    namespace = "com.example.malaysiasafe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.malaysiasafe"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation(libs.constraintlayout.v214)

    // Firebase Authentication
    implementation(platform(libs.firebase.bom)) // Firebase BoM
    implementation(libs.google.firebase.auth) // Firebase Auth Dependency
    implementation (libs.google.firebase.database)
    implementation(libs.firebase.storage)

    implementation (libs.osmdroid.android)


    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
