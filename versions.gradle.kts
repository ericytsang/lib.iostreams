/**
 * Shared file between builds so that they can all use the same dependencies and
 * maven repositories.
 **/
ext.deps = [:]
def versions = [:]
versions.activity = '1.0.0-rc01'
versions.android_gradle_plugin = '3.5.0'
versions.apache_commons = "2.5"
versions.appcompat = "1.1.0-rc01"
versions.arch_core = "2.0.1"
versions.atsl_core = "1.2.0"
versions.atsl_junit = "1.1.1"
versions.atsl_rules = "1.2.0"
versions.atsl_runner = "1.2.0"
versions.atsl_uiautomator = "2.2.0"
versions.benchmark = "1.0.0-alpha04"
versions.constraint_layout = "2.0.0-alpha2"
versions.core_ktx = "1.0.0"
versions.crashlytics = "1.26.1" // todo: please check if this is the right version when adding dependencies to app modules
versions.dagger = "2.16"
versions.dexmaker = "2.2.0"
versions.espresso = "3.2.0"
versions.fragment = "1.2.0-alpha02"
versions.glide = "4.8.0"
versions.google_services = "4.2.0"
versions.hamcrest = "1.3"
versions.joda = "2.10.1"
versions.junit = "4.12"
versions.koin = "2.0.1"
versions.kotlin = "1.3.41"
versions.lifecycle = "2.2.0-alpha04"
versions.mockito = "2.25.0"
versions.mockito_all = "1.10.19"
versions.mockito_android = "2.25.0"
versions.mockwebserver = "3.8.1"
versions.navigation = "2.2.0-alpha01"
versions.okhttp_logging_interceptor = "3.9.0"
versions.paging = "2.1.0-rc01"
versions.retrofit = "2.3.0"
versions.robolectric = "4.2"
versions.robovm="2.3.7"
versions.room = "2.1.0-alpha06"
versions.rx_android = "2.0.1"
versions.rxjava2 = "2.2.12"
versions.support = "1.0.0"
versions.timber = "4.5.1"
versions.truth = "0.42"
versions.work = "2.1.0"
ext.versions = versions

// top-level deps
def deps = [:]
deps.android_gradle_plugin = "com.android.tools.build:gradle:$versions.android_gradle_plugin"
deps.benchmark = "androidx.benchmark:benchmark-junit4:$versions.benchmark"
deps.benchmark_gradle = "androidx.benchmark:benchmark-gradle-plugin:$versions.benchmark"
deps.constraint_layout = "androidx.constraintlayout:constraintlayout:$versions.constraint_layout"
deps.dexmaker = "com.linkedin.dexmaker:dexmaker-mockito:$versions.dexmaker"
deps.hamcrest = "org.hamcrest:hamcrest-all:$versions.hamcrest"
deps.junit = "junit:junit:$versions.junit"
deps.mock_web_server = "com.squareup.okhttp3:mockwebserver:$versions.mockwebserver"
deps.okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${versions.okhttp_logging_interceptor}"
deps.paging_ktx = "androidx.paging:paging-runtime-ktx:$versions.paging"
deps.robolectric = "org.robolectric:robolectric:$versions.robolectric"
deps.rxjava2 = "io.reactivex.rxjava2:rxjava:$versions.rxjava2"
deps.rx_android = "io.reactivex.rxjava2:rxandroid:$versions.rx_android"
deps.timber = "com.jakewharton.timber:timber:$versions.timber"
deps.truth = "com.google.truth:truth:$versions.truth"
ext.deps = deps

// android sdk build versions
def build_versions = [:]
build_versions.min_sdk = 14
build_versions.target_sdk = 28
build_versions.build_tools = "28.0.3"
ext.build_versions = build_versions

//////////////////
// dependencies //
//////////////////

def support = [:]
support.annotations = "androidx.annotation:annotation:$versions.support"
support.app_compat = "androidx.appcompat:appcompat:$versions.appcompat"
support.recyclerview = "androidx.recyclerview:recyclerview:$versions.support"
support.cardview = "androidx.cardview:cardview:$versions.support"
support.design = "com.google.android.material:material:$versions.support"
support.core_utils = "androidx.legacy:legacy-support-core-utils:$versions.support"
support.core_ktx = "androidx.core:core-ktx:$versions.core_ktx"
support.fragment_runtime = "androidx.fragment:fragment:${versions.fragment}"
support.fragment_runtime_ktx = "androidx.fragment:fragment-ktx:${versions.fragment}"
support.fragment_testing = "androidx.fragment:fragment-testing:${versions.fragment}"
support.preference = "androidx.preference:preference:$versions.support"
deps.support = support

def room = [:]
room.runtime = "androidx.room:room-runtime:$versions.room"
room.compiler = "androidx.room:room-compiler:$versions.room"
room.rxjava2 = "androidx.room:room-rxjava2:$versions.room"
room.testing = "androidx.room:room-testing:$versions.room"
deps.room = room

def lifecycle = [:]
lifecycle.runtime = "androidx.lifecycle:lifecycle-runtime:$versions.lifecycle"
lifecycle.java8 = "androidx.lifecycle:lifecycle-common-java8:$versions.lifecycle"
lifecycle.compiler = "androidx.lifecycle:lifecycle-compiler:$versions.lifecycle"
lifecycle.viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$versions.lifecycle"
lifecycle.livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:$versions.lifecycle"
deps.lifecycle = lifecycle

def activity = [:]
activity.activity_ktx = "androidx.activity:activity-ktx:$versions.activity"
deps.activity = activity

def arch_core = [:]
arch_core.runtime = "androidx.arch.core:core-runtime:$versions.arch_core"
arch_core.testing = "androidx.arch.core:core-testing:$versions.arch_core"
deps.arch_core = arch_core

def retrofit = [:]
retrofit.runtime = "com.squareup.retrofit2:retrofit:$versions.retrofit"
retrofit.gson = "com.squareup.retrofit2:converter-gson:$versions.retrofit"
retrofit.mock = "com.squareup.retrofit2:retrofit-mock:$versions.retrofit"
deps.retrofit = retrofit

def dagger = [:]
dagger.runtime = "com.google.dagger:dagger:$versions.dagger"
dagger.android = "com.google.dagger:dagger-android:$versions.dagger"
dagger.android_support = "com.google.dagger:dagger-android-support:$versions.dagger"
dagger.compiler = "com.google.dagger:dagger-compiler:$versions.dagger"
dagger.android_support_compiler = "com.google.dagger:dagger-android-processor:$versions.dagger"
deps.dagger = dagger

def espresso = [:]
espresso.core = "androidx.test.espresso:espresso-core:$versions.espresso"
espresso.contrib = "androidx.test.espresso:espresso-contrib:$versions.espresso"
espresso.intents = "androidx.test.espresso:espresso-intents:$versions.espresso"
deps.espresso = espresso

def atsl = [:]
atsl.core = "androidx.test:core:$versions.atsl_core"
atsl.ext_junit = "androidx.test.ext:junit:$versions.atsl_junit"
atsl.runner = "androidx.test:runner:$versions.atsl_runner"
atsl.rules = "androidx.test:rules:$versions.atsl_rules"
atsl.ui_automator = "androidx.test.uiautomator:uiautomator:$versions.atsl_uiautomator"
deps.atsl = atsl

def mockito = [:]
mockito.core = "org.mockito:mockito-core:$versions.mockito"
mockito.all = "org.mockito:mockito-all:$versions.mockito_all"
mockito.android = "org.mockito:mockito-android:$versions.mockito_android"
deps.mockito = mockito

def kotlin = [:]
kotlin.stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$versions.kotlin"
kotlin.reflect = "org.jetbrains.kotlin:kotlin-reflect:$versions.kotlin"
kotlin.test = "org.jetbrains.kotlin:kotlin-test-junit:$versions.kotlin"
kotlin.plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$versions.kotlin"
kotlin.allopen = "org.jetbrains.kotlin:kotlin-allopen:$versions.kotlin"
deps.kotlin = kotlin

def glide = [:]
glide.runtime = "com.github.bumptech.glide:glide:$versions.glide"
glide.compiler = "com.github.bumptech.glide:compiler:$versions.glide"
deps.glide = glide

def crashlytics = [:]
crashlytics.google_services_plugin = "com.google.gms:google-services:$versions.google_services"
crashlytics.fabric_plugin = "io.fabric.tools:gradle:$versions.crashlytics"
deps.crashlytics = crashlytics

def robovm = [:]
robovm.rt = "com.mobidevelop.robovm:robovm-rt:$versions.robovm"
robovm.cocoatouch = "com.mobidevelop.robovm:robovm-cocoatouch:$versions.robovm"
robovm.plugin = "com.mobidevelop.robovm:robovm-gradle-plugin:$versions.robovm"
deps.robovm = robovm

def work = [:]
work.runtime = "androidx.work:work-runtime:$versions.work"
work.testing = "androidx.work:work-testing:$versions.work"
work.runtime_ktx = "androidx.work:work-runtime-ktx:$versions.work"
deps.work = work

def navigation = [:]
navigation.runtime = "androidx.navigation:navigation-runtime:$versions.navigation"
navigation.runtime_ktx = "androidx.navigation:navigation-runtime-ktx:$versions.navigation"
navigation.fragment = "androidx.navigation:navigation-fragment:$versions.navigation"
navigation.fragment_ktx = "androidx.navigation:navigation-fragment-ktx:$versions.navigation"
navigation.ui = "androidx.navigation:navigation-ui:$versions.navigation"
navigation.ui_ktx = "androidx.navigation:navigation-ui-ktx:$versions.navigation"
navigation.safe_args_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$versions.navigation"
deps.navigation = navigation

def joda = [:]
joda.time = "joda-time:joda-time:$versions.joda"
deps.joda = joda

// koin x kotlin
def koin = [:]
koin.core = /* implementation */ "org.koin:koin-core:$versions.koin"   // Koin for Kotlin
koin.ext = /* implementation */ "org.koin:koin-core-ext:$versions.koin"   // Koin extended & experimental features
koin.test = /* testImplementation */ "org.koin:koin-test:$versions.koin"   // Koin for Unit tests
koin.java = /* implementation */ "org.koin:koin-java:$versions.koin"   // Koin for Java developers
deps.koin = koin

// koin x android
def koin_android = [:]
koin_android.core = /* implementation */ "org.koin:koin-android:$versions.koin"   // Koin for Android
koin_android.scope = /* implementation */ "org.koin:koin-android-scope:$versions.koin"   // Koin Android Scope features
koin_android.viewmodel = /* implementation */ "org.koin:koin-android-viewmodel:$versions.koin"   // Koin Android ViewModel features
koin_android.ext = /* implementation */ "org.koin:koin-android-ext:$versions.koin"   // Koin Android Experimental features
deps.koin_android = koin_android

// koin x androidx
def koin_androidx = [:]
koin_androidx.scope = /* implementation */ "org.koin:koin-androidx-scope:$versions.koin"   // Koin AndroidX Scope features
koin_androidx.viewmodel = /* implementation */ "org.koin:koin-androidx-viewmodel:$versions.koin"   // Koin AndroidX ViewModel features
koin_androidx.ext = /* implementation */ "org.koin:koin-androidx-ext:$versions.koin"   // Koin AndroidX Experimental features
deps.koin_androidx = koin_androidx

// koin x ktor
def koin_ktor = [:]
koin_ktor.ext = /* implementation */ "org.koin:koin-ktor:$versions.koin"   // Koin for Ktor Kotlin
deps.koin_ktor = koin_ktor

ext.deps = deps

static def addRepos(RepositoryHandler handler) {
    handler.google()
    handler.jcenter()
    handler.maven { url "https://maven.fabric.io/public" }
    handler.maven { url 'https://oss.sonatype.org/content/repositories/snapshots' }
    handler.maven { url "https://jitpack.io" }
}
ext.addRepos = this.&addRepos
