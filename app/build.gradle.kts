plugins {
    kotlin("android")
    id("com.android.application")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

android {
    namespace = "com.app.myfincontrol"
    compileSdk = 34

    defaultConfig {
        applicationId ="com.app.myfincontrol"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            applicationIdSuffix = ".release"
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            isEmbedMicroApp = false
            isProfileable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            multiDexEnabled = true
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            isEmbedMicroApp = false
            isProfileable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

    androidResources {
        generateLocaleConfig = true
    }

//    packagingOptions {
//        resources {
//            excludes += '/META-INF/{AL2.0,LGPL2.1}'
//        }
//    }

    kotlinOptions {
        jvmTarget = "18"
    }
}

dependencies {
    val room_version = "2.5.1"
    val composeVerison = "1.6.0-alpha01"

    // Paging
    implementation("androidx.paging:paging-compose:3.2.0-rc01")
    implementation("androidx.paging:paging-common-ktx:3.1.1")
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")

    //Data story
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.5-beta")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // Dagger Hilt
    implementation ("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-compiler:2.46.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.46.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Charts
    implementation("com.patrykandpatrick.vico:core:1.6.5")
    implementation("com.patrykandpatrick.vico:compose:1.6.5")
    implementation("com.patrykandpatrick.vico:compose-m3:1.6.5")


    //implementation("com.himanshoe:charty:1.0.1")

//    // optional - Test helpers
//    testImplementation("androidx.room:room-testing:$room_version")
//
//    // optional - Paging 3 Integration
//    implementation("androidx.room:room-paging:$room_version")

//    // alternatively - without Android dependencies for tests
//    testImplementation("androidx.paging:paging-common:$paging_version")

    // For instrumentation tests
//    androidTestImplementation  'com.google.dagger:hilt-android-testing:2.46.1'
//    androidTestAnnotationProcessor 'com.google.dagger:hilt-compiler:2.46.1'

    // For local unit tests
//    testImplementation 'com.google.dagger:hilt-android-testing:2.46.1'
//    testAnnotationProcessor 'com.google.dagger:hilt-compiler:2.46.1'

    // Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")



    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui:${composeVerison}")
    implementation("androidx.compose.ui:ui-graphics:${composeVerison}")
    implementation("androidx.compose.ui:ui-tooling-preview:${composeVerison}")

    implementation("androidx.compose.material:material:${composeVerison}")
    implementation("androidx.compose.material3:material3:1.2.0-alpha03")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}