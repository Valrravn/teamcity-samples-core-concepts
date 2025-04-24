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

    buildType(ConfigF)
    buildType(ConfigE)
    buildType(BuildAll)
    buildType(ConfigD)
    buildType(ConfigC)
    buildType(ConfigB)
    buildType(ConfigA)
}

object BuildAll : BuildType({
    name = "Build All"

    type = BuildTypeSettings.Type.COMPOSITE

    vcs {
        showDependenciesChanges = true
    }

    dependencies {
        snapshot(ConfigE) {
        }
        snapshot(ConfigF) {
        }
        artifacts(ConfigC) {
            cleanDestination = true
            artifactRules = "file.txt"
        }
    }
})

object ConfigA : BuildType({
    name = "Config A"

    artifactRules = "file.txt"

    vcs {
        cleanCheckout = true
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """
                touch file.txt
                echo "Created in Config A" > file.txt
            """.trimIndent()
        }
    }
})

object ConfigB : BuildType({
    name = "Config B"

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "sleep 10"
        }
    }
})

object ConfigC : BuildType({
    name = "Config C"

    artifactRules = "file.txt"

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """echo "Edited in Config C" >> file.txt"""
        }
    }

    dependencies {
        dependency(ConfigA) {
            snapshot {
            }

            artifacts {
                artifactRules = "file.txt"
            }
        }
        snapshot(ConfigB) {
            reuseBuilds = ReuseBuilds.ANY
        }
    }
})

object ConfigD : BuildType({
    name = "Config D"

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """
                echo "Always failing"
                unknown_command
            """.trimIndent()
        }
    }
})

object ConfigE : BuildType({
    name = "Config E"

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "sleep 7"
        }
    }

    dependencies {
        snapshot(ConfigC) {
            reuseBuilds = ReuseBuilds.NO
        }
        snapshot(ConfigD) {
            reuseBuilds = ReuseBuilds.NO
            onDependencyFailure = FailureAction.IGNORE
            onDependencyCancel = FailureAction.ADD_PROBLEM
        }
    }
})

object ConfigF : BuildType({
    name = "Config F"

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "sleep 5"
        }
    }
})
