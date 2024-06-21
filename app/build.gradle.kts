plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.lotrwiki"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lotrwiki"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    val navVersion = "2.7.0"

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //glide
    implementation("com.github.bumptech.glide:glide:4.15.1")

    //firebase database
    implementation("com.google.firebase:firebase-database:21.0.0")

    //gson
    implementation("com.google.code.gson:gson:2.10.1")

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //pagination
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")

    //zoomlayout
    implementation("com.otaliastudios:zoomlayout:1.9.0")

    //dotindicator
    implementation("com.tbuonomo:dotsindicator:5.0")

    //youtube
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    //expandablelayout
    implementation("com.github.cachapa:ExpandableLayout:2.9.2")



    //swiperefresh
//    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

}