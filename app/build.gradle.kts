plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.hilt.android)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.compose)
}

android {
  namespace = "com.oadultradeepfield.skymatch"
  compileSdk { version = release(36) }

  defaultConfig {
    applicationId = "com.oadultradeepfield.skymatch"
    minSdk = 26
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildFeatures {
    buildConfig = true
    compose = true
  }

  buildTypes {
    debug { buildConfigField("Boolean", "IS_DEBUG", "true") }
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      buildConfigField("Boolean", "IS_DEBUG", "false")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

dependencies {
  val composeBom = platform("androidx.compose:compose-bom:2026.01.00")
  implementation(composeBom)
  androidTestImplementation(composeBom)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.hilt.android)
  implementation(libs.material3)
  implementation(libs.androidx.ui.tooling.preview)
  debugImplementation(libs.androidx.ui.tooling)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.ui.text.google.fonts)

  ksp(libs.hilt.compiler)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}
