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
