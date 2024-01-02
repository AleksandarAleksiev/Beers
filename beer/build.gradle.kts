@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aaleksiev.beers"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    val composeBoM = platform(libs.composeBoM)
    val kotlinBoM = platform(libs.kotlinBoM)
    debugImplementation(libs.composeTooling)

    implementation(composeBoM)
    implementation(kotlinBoM)
    implementation(libs.material3)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.accompanist)

    implementation(libs.androidXCore)
    implementation(libs.lifecycleRuntime)

    implementation(libs.bundles.ktor)

    implementation(libs.hilt)
    implementation(libs.bundles.navigation)

    ksp(libs.hiltCompiler)

    testImplementation(kotlinBoM)
    testImplementation(libs.bundles.unitTest)

    androidTestImplementation(kotlinBoM)
    androidTestImplementation(composeBoM)
    androidTestImplementation(libs.bundles.androidTest)
    debugImplementation(libs.androidCompioseUiTestManifest)
}