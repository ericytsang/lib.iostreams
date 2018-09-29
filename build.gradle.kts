import org.apache.tools.ant.taskdefs.Java
import org.gradle.internal.impldep.org.bouncycastle.util.Properties
import org.gradle.jvm.tasks.Jar
import java.io.InputStream
import java.util.Properties as JavaProperties

plugins {
    `kotlin-dsl`
}

val project_version = "36.0.2"
val kotlin_version = "1.2.61"
val room_version = "1.1.1"
val paging_version = "1.0.1"
val work_manager_version = "1.0.0-alpha05"
val android_support_version = "27.1.1"
val firebase_version = "12.0.1"
val anko_version = "0.10.4"

allprojects {

    ext["project_version"] = project_version
    ext["kotlin_version"] = kotlin_version
    ext["room_version"] = room_version
    ext["paging_version"] = paging_version
    ext["work_manager_version"] = work_manager_version
    ext["android_support_version"] = android_support_version
    ext["firebase_version"] = firebase_version
    ext["anko_version"] = anko_version

    group = "com.github.ericytsang"
    version = project_version

    val projectKind = getProjectKind(this)
    when(projectKind)
    {
        is Build_gradle.ProjectKind.Library ->
        {
            plugins.apply("maven")
            plugins.apply("kotlin")

            tasks.withType(JavaCompile::class.java) {
                sourceCompatibility = JavaVersion.VERSION_1_6.toString()
                targetCompatibility = JavaVersion.VERSION_1_6.toString()
            }

//            java {
//                sourceCompatibility = JavaVersion.VERSION_1_6
//                targetCompatibility = JavaVersion.VERSION_1_6
//            }

            dependencies {
                projectKind.addDependencies(this)
            }

            repositories {
                jcenter()
                mavenCentral()
            }

            Unit
        }
        is Build_gradle.ProjectKind.Unknown -> Unit
    }.apply {/* exhaustive */}
}

// project kinds

fun getProjectKind(project:Project) = when(project.name)
{
    "lib.abstractstream",
    "lib.awaitable",
    "lib.cipherstream",
    "lib.concurrent",
    "lib.datastore",
    "lib.deltarepo",
    "lib.modem",
    "lib.net",
    "lib.onlycallonce",
    "lib.randomstream",
    "lib.regulatedstream",
    "lib.repo",
    "lib.rpc",
    "lib.setofatleastone",
    "lib.simplepipestream",
    "lib.simplifiedmap",
    "lib.streamtest",
    "test.playground"->ProjectKind.Library()
    {
        compile(kotlin("stdlib",kotlin_version))
        compile(kotlin("reflect",kotlin_version))
        testCompile(project(":lib.testutils"))
    }
    "lib.testutils"->ProjectKind.Library()
    {
        compile(kotlin("stdlib",kotlin_version))
        compile(kotlin("reflect",kotlin_version))
    }
    else->ProjectKind.Unknown()
}

sealed class ProjectKind
{
    abstract fun addDependencies(dependencyHandler:DependencyHandler)
    class Library(val dependencyAdder:DependencyHandler.()->Unit):ProjectKind()
    {
        override fun addDependencies(dependencyHandler:DependencyHandler) = dependencyHandler.dependencyAdder()
    }
    class Unknown:ProjectKind()
    {
        override fun addDependencies(dependencyHandler:DependencyHandler) = Unit
    }
}

// new gradle tasks

task("install_tag_and_push")
{
    // install all modules
    dependsOn.addAll(allprojects
            .iterator().asSequence()
            .flatMap {it.tasks.toList().asSequence()}
            .filter {it.name == "install"})

    actions.apply {} += Action<Task> {

        // make sure working branch is clean
        executeCommand("git status",{"nothing to commit, working tree clean" in it},{"working branch not clean"})

        // make sure there is no conflicting release
        executeCommand("git fetch",0)
        executeCommand("git tag -l",{project_version !in it.split("\n")},{"a tag with the name \"$project_version\" already exists; please update version number:\n$it"})

        // add tag and push
        executeCommand("git tag -a \"$project_version\" -m \"v$project_version\"",0)
        executeCommand("git push origin $project_version",0)
    }
}

fun executeCommand(
        command:String,
        expectedReturnValue:Int=0,
        failureMessage:(actual:Int)->String={"execution of command \"$command\" returned $it instead of $expectedReturnValue"})
{
    // execute process
    println("$ $command")
    val process = Runtime.getRuntime().exec(command)

    // read process output
    process.inputStream.copyTo(System.out)
    println()

    // await process termination
    val returnValue = process.waitFor()

    // fail if predicate not satisfied
    if (returnValue != expectedReturnValue)
    {
        throw Exception(failureMessage(returnValue))
    }
}

fun executeCommand(
        command:String,
        predicate:(output:String)->Boolean,
        failureMessage:(actualOutput:String)->String={"output of command \"$command\" did not satisfy predicate. raw output:\n$it"})
{
    // execute process
    println("$ $command")
    val process = Runtime.getRuntime().exec(command)

    // read process output
    val outputOfProcess = String(process.inputStream.readBytes())
    println(outputOfProcess)

    // await process termination
    process.waitFor()

    // fail if predicate not satisfied
    if (!predicate(outputOfProcess))
    {
        throw Exception(failureMessage(outputOfProcess))
    }
}
