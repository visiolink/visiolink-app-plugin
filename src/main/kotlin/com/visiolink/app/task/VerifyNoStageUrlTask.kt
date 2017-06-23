package com.visiolink.app.task

/**
 * Check for device-stage URLs
 */
open class VerifyNoStageUrlTask : VisiolinkGroupTask() {

    init {
        description = "Checks that no stage URLs are used before release builds"
    }

    @org.gradle.api.tasks.TaskAction
    fun action() {
        if(project.hasProperty("ignoreChecks")) return

        project.projectDir
                .walkTopDown()
                .filter { it.isFile
                        && !it.absolutePath.contains("/build/")
                        && (it.extension == "xml" || it.extension == "json") }
                .forEach { file ->
                    //println("Checking file $file")
                    file.forEachLine { line ->
                        if (line.contains("device-stage")) {
                            println("The following file contains device-stage:")
                            println(file)
                            println(line)

                            throw org.gradle.api.GradleException("Stage URL found in release build. File with device-stage:\n$file")
                        }
                    }
                }
    }
}