plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.1.20"
}

android {
    namespace = "com.sequenia.movies"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sequenia.movies"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.sequenia.movies.utils.InstrumentationTestRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Swipe to refresh
    implementation(libs.androidx.swiperefreshlayout)

    // Glide
    implementation(libs.glide)

    // Koin
    implementation(libs.insert.koin.koin.core)
    implementation(libs.koin.android)
    implementation(libs.androidx.espresso.contrib)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    testImplementation(libs.mockwebserver)


    // Kotlinx Serialization Json
    implementation(libs.kotlinx.serialization.json)

    // Mockk
    testImplementation(libs.mockk)

    // Coroutines Test
    testImplementation(libs.kotlinx.coroutines.test)

    // Espresso
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}