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

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    params {
        param("greeting.string", "안녕하세요")
        param("output.string", "")
    }

    steps {
        script {
            name = "Set parameter"
            id = "simpleRunner"
            scriptContent = """echo "##teamcity[setParameter name='output.string' value='%greeting.string% %teamcity.build.triggeredBy.username%']""""
        }
        script {
            name = "Say hello"
            id = "Say_hello"
            scriptContent = """echo "%output.string%""""
        }
    }
})
