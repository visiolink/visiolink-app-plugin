package com.visiolink.app.task

/**
 * Task to increase major version name
 */
open class IncreaseMajorVersionNameTask : VisiolinkGroupTask() {

    init {
        description = "Increases the major version name"
    }

    @org.gradle.api.tasks.TaskAction
    fun action() {
        val versionPropsFile = project.file("version.properties")
        if (versionPropsFile.canWrite()) {
            println("Increasing major version name")

            val versionProps = java.util.Properties()
            versionProps.load(java.io.FileInputStream(versionPropsFile))
            val versionBuild = versionProps.getProperty("versionMajor").toInt() + 1

            versionProps.setProperty("versionMajor", versionBuild.toString())
            versionProps.setProperty("versionMinor", "0")
            versionProps.setProperty("versionBuild", "0")
            versionProps.store(versionPropsFile.writer(), "versionMajor.versionMinor.versionBuild")
        } else {
            throw org.gradle.api.GradleException("Could not write to version.properties!")
        }
    }
}