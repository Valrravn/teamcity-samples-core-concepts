import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.approval
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

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

    buildType(TemplatedBuildConfiguration)
    buildType(SourceConfiguration)

    template(SampleTemplate)
}

object SourceConfiguration : BuildType({
    templates(SampleTemplate)
    name = "Source Configuration"
    description = """This configuration was set up manually to spawn a template from the "Quick Actions |  Extract Template..." menu item"""
})

object TemplatedBuildConfiguration : BuildType({
    templates(SampleTemplate)
    name = "Templated Build Configuration"
    description = """This configuration has its "Based on template" property configured"""

    params {
        param("myParam", "custom_value")
    }

    steps {
        script {
            id = "simpleRunner_1"
            scriptContent = """echo "Custom build step""""
        }
    }
})

object SampleTemplate : Template({
    name = "Sample Template"

    params {
        param("myParam", "default_value")
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """echo "Hello""""
        }
    }

    triggers {
        vcs {
            id = "TRIGGER_75"
            triggerRules = "+:*"
            branchFilter = ""
            enableQueueOptimization = false
        }
    }

    features {
        approval {
            id = "approval-feature"
            approvalRules = "user:Valravn"
            timeout = 120
        }
    }
})
