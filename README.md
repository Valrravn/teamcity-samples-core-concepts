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

## Sample 2 — Build Configuration Teamplates

Templates allow you to pre-set every new build configuration according to your requirements. In this sample:

* **Source Configuration** — a manually created configuration. Includes one build step, a parameter, a VCS trigger, and the "Build Approval" build feature.
* **Sample Template** — extracted from **Source Configuration** via "Configuration settings | Quick Actions | Extract Template..." menu action. After the template was extracted, it's automatically applied to its parent configuration, so every template change will affect this configuration as well.
* **Templated Build Configuration** — a 
