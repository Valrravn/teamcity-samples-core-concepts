# TeamCity Samples: Core Concepts
A bunch of small samples demonstrating core TeamCity concepts


## Sample 1 — Basic TeamCity Objects

Demonstrates a hierarchy of core TeamCity entities:

```
Main Project
├── Sub-Project A
│   └── Sub-project Build Configuration
│       └── Build Step (CLI)
└── Build Configuration 1: Hello World
        └── Build Step (CLI)
```

* A **build step** is the elemental block of any building routine. Encapsulates an action (or a series of actions) that cannot be executed partially.
* A **build configuration** is a collection of sequentially executed steps. Each step can have its own run conditions, so you can control which steps to execute depending on required conditions.
* A **project** is a home for build configurations and other sub-projects. Cannot perform any CI/CD routines and allows you to group/categorize builds on your server.

Related help articles: [Project Administrator Guide](https://www.jetbrains.com/help/teamcity/project-administrator-guide.html#Steps%2C+Configurations+and+Projects)

## Sample 2 — Build Configuration Teamplates

Templates allow you to pre-set every new build configuration according to your requirements. In this sample:

* **Source Configuration** — a manually created configuration. Includes one build step, a parameter, a VCS trigger, and the "Build Approval" build feature.
* **Sample Template** — extracted from **Source Configuration** via "Configuration settings | Quick Actions | Extract template..." menu action. After the template was extracted, it automatically applies to its parent configuration (in Kotlin DSL, note the configuration has no unique settings except for description and name: all settings were moved to the extracted template).
* **Templated Build Configuration** — a configuration whose "based on template" property was set upon creation. Inherits all settings of a template, but adds one additional build step and overrides the default parameter value. This technique allows you to quickly "clone" a default build configuration, and then customize it as needed.

You can stop synchronizing a templated configuration with its parent parent template at any time. To do so, go to "Configuration settings | Quick Actions" and select "Detach from template...".

A template can be designated as the default for a project, ensuring that all new configurations automatically follow its structure and settings.

Related help articles: [Build Configuration Template](https://www.jetbrains.com/help/teamcity/build-configuration-template.html)

## Sample 3 — Input Parameters

TeamCity parameters are name-value pairs. Parameters can be input (designed to be used directly in the configuration/project that declares them) and output (can be referenced by downstream configurations of a build chain).

Depending on their origin, parameters can be custom (user-defined) and predefined. TeamCity exposes a wide range of predefined build parameters that return the build number, the build agent OS type, the user who triggered the build, and so on.

This sample performs two steps:

1. Merges values of the custom `greeting.string` and predefined `teamcity.build.triggeredBy.username` parameters. The result is written to a custom `output.string` parameter via the `##teamcity[setParameter name='name' value='value']` service message.
2. Prints the `output.string` parameter value to the build log.

> Note: You cannot get the modified parameter value in the same step that sent the `##teamcity[setParameter]` message. For that reason, reporting the `output.string` parameter value is done as a separate step.

Related help articles: [Build Parameters](https://www.jetbrains.com/help/teamcity/configuring-build-parameters.html) | [Custom parameters](https://www.jetbrains.com/help/teamcity/typed-parameters.html) | [setParameter Service Message](https://www.jetbrains.com/help/teamcity/service-messages.html#set-parameter) 
