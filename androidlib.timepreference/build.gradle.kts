configureAndroidLib(project)

dependencies {

    api project(":lib.closeablegroup")
    api project(":lib.prop")
    api project(":androidlib.core")
    api project(":androidlib.dialogpreference")

    // time library
    implementation deps.joda.time
}
