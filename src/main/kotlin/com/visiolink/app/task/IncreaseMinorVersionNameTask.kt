package com.visiolink.app.task

/**
 * Task to increase minor version name
 */
open class IncreaseMinorVersionNameTask : VisiolinkGroupTask() {

    init {
        description = "Increases the minor version name"
    }

    @org.gradle.api.tasks.TaskAction
    fun action() {
        val versionPropsFile = project.file("version.properties")
        if (versionPropsFile.canWrite()) {
            println("Increasing minor version name")

            val versionProps = java.util.Properties()
            versionProps.load(java.io.FileInputStream(versionPropsFile))
            val versionBuild = versionProps.getProperty("versionMinor").toInt() + 1

            versionProps.setProperty("versionMinor", versionBuild.toString())
            versionProps.setProperty("versionBuild", "0")
            versionProps.store(versionPropsFile.writer(), "versionMajor.versionMinor.versionBuild")
        } else {
            throw org.gradle.api.GradleException("Could not write to version.properties!")
        }
    }
}