import org.jetbrains.dokka.DokkaConfiguration

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.dokka")
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

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        named("main") {
            documentedVisibilities.set(
                setOf(
                    DokkaConfiguration.Visibility.PUBLIC,
                    DokkaConfiguration.Visibility.PROTECTED,
                    DokkaConfiguration.Visibility.INTERNAL,
                    DokkaConfiguration.Visibility.PRIVATE
                )
            )
            includeNonPublic.set(true)
            reportUndocumented.set(false)
            skipDeprecated.set(false)
            platform.set(org.jetbrains.dokka.Platform.jvm)
            sourceRoots.setFrom(file("src/main/java")) 
        }
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

    //Dokka
    implementation(kotlin("script-runtime"))
}