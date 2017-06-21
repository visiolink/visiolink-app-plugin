package com.visiolink.app.task

/**
 * Task to increase build version
 */
open class IncreaseBuildVersionNameTask : VisiolinkGroupTask() {

    init {
        description = "Increases the build version name"
    }

    @org.gradle.api.tasks.TaskAction
    fun action() {
        val versionPropsFile = project.file("version.properties")
        if (versionPropsFile.canWrite()) {
            println("Increasing build version name")

            val versionProps = java.util.Properties()
            versionProps.load(java.io.FileInputStream(versionPropsFile))
            val versionBuild = versionProps.getProperty("versionBuild").toInt() + 1

            versionProps.setProperty("versionBuild", versionBuild.toString())
            versionProps.store(versionPropsFile.writer(), "versionMajor.versionMinor.versionBuild")
        } else {
            throw org.gradle.api.GradleException("Could not write to version.properties!")
        }
    }
}