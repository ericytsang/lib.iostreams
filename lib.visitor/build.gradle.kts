configureKotlin(project)

apply plugin: "kotlin-kapt"

dependencies {

    // generate kotlin code
    implementation "com.squareup:kotlinpoet:1.3.0" // todo: add to versions.gradle

    // google's auto service
    implementation "com.google.auto.service:auto-service:1.0-rc5" // todo: add to versions.gradle
    kapt "com.google.auto.service:auto-service:1.0-rc5" // todo: add to versions.gradle
}
