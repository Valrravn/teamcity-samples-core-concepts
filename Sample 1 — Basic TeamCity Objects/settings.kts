import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.12"

project {

    buildType(BuildConfiguration1HelloWorld)

    subProject(SubProjectA)
}

object BuildConfiguration1HelloWorld : BuildType({
    name = "Build Configuration 1: Hello World"

    steps {
        script {
            name = "Step 1"
            id = "Step_1"
            scriptContent = """echo "Hello World!""""
        }
    }
})


object SubProjectA : Project({
    name = "Sub-Project A"

    buildType(SubProjectA_SubProjectBuildConfiguration)
})

object SubProjectA_SubProjectBuildConfiguration : BuildType({
    name = "Sub-project Build Configuration"

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """echo "Hello again""""
        }
    }
})
