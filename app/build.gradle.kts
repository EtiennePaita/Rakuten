plugins {
    id("kotlin-kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
}
apply(plugin = "dagger.hilt.android.plugin")
apply(plugin = "kotlin-kapt")

android {
    namespace = "fr.paita.rakutenproto"
    compileSdk = 35

    defaultConfig {
        applicationId = "fr.paita.rakutenproto"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    flavorDimensions.add("api")
    productFlavors{
        /*create("dev") {
            dimension = "api"
            buildConfigField("String", "API_BASE_URL", "\"https://4206121f-64a1-4256-a73d-2ac541b3efe4.mock.pstmn.io\"")
            buildConfigField("String", "API_TOKEN", apiToken)
            buildConfigField("String", "SEARCH_TOKEN", searchToken)
        }
        create("preprod") {
            dimension = "api"
            buildConfigField("String", "API_BASE_URL", "\"https://4206121f-64a1-4256-a73d-2ac541b3efe4.mock.pstmn.io\"")
            buildConfigField("String", "API_TOKEN", apiToken)
            buildConfigField("String", "SEARCH_TOKEN", searchToken)
        }*/
        create("prod") {
            dimension = "api"
            buildConfigField("String", "API_BASE_URL", "\"https://4206121f-64a1-4256-a73d-2ac541b3efe4.mock.pstmn.io\"")
            //buildConfigField("String", "API_TOKEN", apiToken)
            //buildConfigField("String", "SEARCH_TOKEN", searchToken)
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlin.stdlib.jdk7)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //OkHTTP
    implementation(libs.logging.interceptor)
    testImplementation(libs.mockwebserver)

    // Glide
    implementation (libs.glide)
}

kapt {
    correctErrorTypes = true
}