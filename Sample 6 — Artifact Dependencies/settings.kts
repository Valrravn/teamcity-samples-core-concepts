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

    buildType(Producer)
    buildType(Consumer_1)
}

object Consumer_1 : BuildType({
    id("Consumer")
    name = "Consumer"

    artifactRules = """
        output.txt
        output_consumer.txt
    """.trimIndent()

    params {
        param("env.imported", "false")
    }

    vcs {
        cleanCheckout = true
    }

    steps {
        script {
            name = "Verify the import"
            id = "simpleRunner"
            scriptContent = """
                FILE_PATH="output.txt"
                
                if [ -f "${'$'}FILE_PATH" ]; then
                  echo "File 'output.txt' was imported from the 'Producer' configuration"
                  echo "##teamcity[setParameter name='env.imported' value='true']"
                else
                  echo "Failed to locate the 'output.txt' file; artifact dependency was not resolved"
                  # leave the 'imported' parameter as 'false'
                fi
            """.trimIndent()
        }
        script {
            name = "Print file contents"
            id = "simpleRunner_1"
            scriptContent = """
                if [ "${'$'}imported" = "true" ]; then
                  echo "The 'imported' environment variable is 'true'"
                  cat "output.txt"
                elif [ "${'$'}imported" = "false" ]; then
                  echo "The 'imported' environment variable is 'false'"
                  touch output_consumer.txt
                  echo "File from Consumer build configuration" > output_consumer.txt
                  cat "output_consumer.txt"
                else
                  echo "Environment variable 'imported' is not set or has an unexpected value."
                fi
            """.trimIndent()
        }
    }

    dependencies {
        artifacts(Producer) {
            buildRule = lastSuccessful()
            artifactRules = "?:archive.zip!/output.txt"
        }
    }
})

object Producer : BuildType({
    name = "Producer"

    artifactRules = """
        output.txt => archive.zip
        output2.txt => archive.zip
        output3.txt => archive.zip
    """.trimIndent()

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """
                touch output.txt
                echo "File from Producer build configuration" > output.txt
                
                touch output2.txt
                touch output3.txt
            """.trimIndent()
        }
    }
})
