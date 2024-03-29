plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    //firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.cherry"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.cherry"
        minSdk = 21
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //카드스택뷰
    implementation ("com.yuyakaido.android:card-stack-view:2.3.4")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    //firebase-auth
    implementation("com.google.firebase:firebase-auth-ktx")

    //firebase-realtime
    implementation("com.google.firebase:firebase-database-ktx")

    //firebase-storage
    implementation("com.google.firebase:firebase-storage-ktx")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    // coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.3")

    //implementation ("com.android.databinding:dataBinder:1.0-rc4")

    // circle image view
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
}