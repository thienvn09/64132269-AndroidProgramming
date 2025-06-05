plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "thien.com.final_kidedu"
    compileSdk = 35 // Firebase libraries are generally compatible, but ensure your other dependencies are too.

    defaultConfig {
        applicationId = "thien.com.final_kidedu"
        minSdk = 25
        targetSdk = 35
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
    // If you are using View Binding or Data Binding, ensure it's enabled here
    // buildFeatures {
    //     viewBinding = true
    // }
}

dependencies {
    val roomVersion = "2.6.1" // Kiểm tra phiên bản mới nhất
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



}
