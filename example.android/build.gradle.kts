configureAndroidApp(project)


android {
    defaultConfig {
        applicationId "com.github.ericytsang.screenfilter.app.android"
        versionCode 1
        versionName "1.0.0"
    }
}

dependencies {

    // todo: add Koin!

    // ericytsang libraries
    implementation project(":androidlib.confirmdialog")
    implementation project(":androidlib.billingclientfacade")
    implementation project(":androidlib.cannotopenlinkdialog")
    implementation project(":androidlib.colorpreference")
    implementation project(":androidlib.listitempickerdialog")
    implementation project(":androidlib.seekbarpreferenceinline")
    implementation project(":androidlib.shakelistener")
    implementation project(":androidlib.timepreference")
    implementation project(":lib.closeablegroup")
    implementation project(":lib.domainobjects")
    implementation project(":lib.prop")
    implementation project(":lib.setofatleastone")

    // app compat
    api deps.support.core_utils
    api deps.support.app_compat
    api deps.support.recyclerview

    // leak canary
    debugImplementation "com.squareup.leakcanary:leakcanary-android:1.6.3"
    releaseImplementation "com.squareup.leakcanary:leakcanary-android-no-op:1.6.3"
    debugImplementation "com.squareup.leakcanary:leakcanary-support-fragment:1.6.3"
}
