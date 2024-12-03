plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.rickandmorty"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.rickandmorty"
        minSdk = 27
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Lombok
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly(libs.lombok)
    annotationProcessor (libs.lombok.v11824)

    // Glide
    implementation(libs.glide)

    //Mockito y core testing
    testImplementation (libs.mockito.core)
    testImplementation (libs.core.testing)
    testImplementation (libs.mockito.inline)
    testImplementation(libs.mockito.junit.jupiter)

    //Jupiter
    testImplementation (libs.junit.jupiter.api)
    testImplementation (libs.junit.jupiter.engine)



}