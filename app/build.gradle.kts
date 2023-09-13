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
        applicationId = "com.app.myfincontrol"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.app.myfincontrol.TestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    testOptions {
        unitTests.all {
            unitTests.isReturnDefaultValues = true
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

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            )
        )
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
    buildToolsVersion = "34.0.0 rc1"
}

dependencies {

    val roomVersion = "2.5.2"
    val composeVersion = "1.5.1"
    val junit5Version = "5.10.0"
    val mockkVersion = "1.13.7"

    // Paging
    implementation("androidx.paging:paging-compose:3.2.1")
    implementation("androidx.paging:paging-common-ktx:3.2.1")
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    //Data story
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.7.2")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.appcompat:appcompat:1.6.1")
    annotationProcessor("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Charts
    implementation("com.patrykandpatrick.vico:core:1.11.3")
    implementation("com.patrykandpatrick.vico:compose:1.11.3")
    implementation("com.patrykandpatrick.vico:compose-m3:1.11.3")

    // Tests
    testImplementation("org.junit.jupiter:junit-jupiter:${junit5Version}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junit5Version}")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:${junit5Version}")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.test:runner:1.5.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")



    // MockK
    testImplementation("io.mockk:mockk:${mockkVersion}")

    testImplementation("io.mockk:mockk-android:${mockkVersion}")
    testImplementation("io.mockk:mockk-agent:${mockkVersion}")

    androidTestImplementation("io.mockk:mockk-android:${mockkVersion}")
    androidTestImplementation("io.mockk:mockk-agent:${mockkVersion}")


    /*    testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")*/

    // Hilt: local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:2.48")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:2.48")

    // Hilt: instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    androidTestAnnotationProcessor("com.google.dagger:hilt-compiler:2.48")

    //implementation("com.himanshoe:charty:1.0.1")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$roomVersion")

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$roomVersion")

//    // alternatively - without Android dependencies for tests
//    testImplementation("androidx.paging:paging-common:$paging_version")


    // Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.09.00"))
    implementation("androidx.compose.ui:ui:${composeVersion}")
    implementation("androidx.compose.ui:ui-graphics:${composeVersion}")
    implementation("androidx.compose.ui:ui-tooling-preview:${composeVersion}")

    implementation("androidx.compose.material:material:${composeVersion}")
    implementation("androidx.compose.material3:material3:1.2.0-alpha07")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}