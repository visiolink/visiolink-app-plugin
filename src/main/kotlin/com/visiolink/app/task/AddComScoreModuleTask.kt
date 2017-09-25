package com.visiolink.app.task

import org.gradle.api.tasks.TaskAction

/**
 * Task to add the ComScore sub module to an existing project.
 */
open class AddComScoreModuleTask : VisiolinkGroupTask() {

    init {
        description = "Task to add the ComScore sub module to an existing project"
    }

    @TaskAction
    fun action() {
        addSubModule("ComScore", "comscore")

        println("Done adding ComScore")
    }
}