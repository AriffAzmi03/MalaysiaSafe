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

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase Authentication
    implementation(platform("com.google.firebase:firebase-bom:33.7.0")) // Firebase BoM
    implementation("com.google.firebase:firebase-auth") // Firebase Auth Dependency
    implementation ("com.google.firebase:firebase-database")
    implementation(libs.firebase.storage)

    implementation ("org.osmdroid:osmdroid-android:6.1.10")


    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
