package com.visiolink.app.task

import org.gradle.api.tasks.TaskAction

/**
 * Task to add the Android TV sub module to an existing project.
 */
open class AddAndroidTvModuleTask: VisiolinkGroupTask() {

    init {
        description = "Task to add the Android TV sub module to an existing project"
    }

    @TaskAction
    fun action() {
        addSubModule("AndroidTV", "tv")

        println("Done adding Android TV")
    }
}