import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

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

    vcsRoot(MainRoot)

    buildType(Trunk)
    buildType(Development)
}

object Development : BuildType({
    name = "Development"

    vcs {
        root(MainRoot)

        branchFilter = """
            -:*
            +:<default>
            +:dev-*
        """.trimIndent()
    }
})

object Trunk : BuildType({
    name = "Trunk"

    vcs {
        root(MainRoot)
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """echo "Hello World!":"""
        }
    }
})

object MainRoot : GitVcsRoot({
    name = "MainRoot"
    url = "https://github.com/Valrravn/teamcity-multibranch-repo"
    branch = "main"
    branchSpec = """
        +:refs/heads/*
        -:refs/heads/sandbox
        -:refs/heads/test
    """.trimIndent()
})
