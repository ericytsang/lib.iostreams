apply plugin: "kotlin"
apply plugin: "kotlin-kapt"

dependencies {

    // lib.visitor
    kapt project(":lib.visitor")
    compileOnly project(":lib.visitor")

    // kotlin
    implementation deps.kotlin.stdlib
    implementation deps.kotlin.reflect

    // kotlin co-routines
    api "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-swing:$kotlin_coroutines_version"

    // test
    testImplementation project(":lib.testutils")

    // test
    api "junit:junit:4.12"
    api "org.mockito:mockito-all:2.0.2-beta"

    // test kotlin
    testImplementation "org.jetbrains.kotlin:kotlin-test:$kotlin_version"
    testImplementation "org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version"

    // external dependencies
    api "net.sf.joost:joost:0.9.1"
    api "net.sf.saxon:saxon:8.7"
    api "org.codehaus.woodstox:woodstox-core-asl:4.4.1"
    api "org.codehaus.woodstox:stax2-api:3.1.4"
    api "xerces:xercesImpl:2.9.1"
    api "com.cedarsoft.serialization:stax-mate:3.0.0"
    api "com.thoughtworks.xstream:xstream:1.4.2"
    api "org.apache.commons:commons-lang3:3.4"
    api "com.google.collections:google-collections:1.0-rc2"
}
