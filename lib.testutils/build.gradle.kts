configureKotlin(project)

dependencies {

    // kotlin co-routines
    // todo: add to versins.gradle
    api "org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlin_coroutines_version"

    // project dependencies
    api project(":lib.concurrent")

    // external dependencies
    api deps.kotlin.test
    api deps.junit
    api deps.mockito.all
}
