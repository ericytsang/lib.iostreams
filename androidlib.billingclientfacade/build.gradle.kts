configureAndroidLib(project)

dependencies {

    api project(":androidlib.core")
    api project(":lib.prop")
    api project(":lib.simpletask")

    // android billing
    implementation "com.android.billingclient:billing:2.0.3"
}
