package com.visiolink.app.task

import org.gradle.api.tasks.TaskAction

/**
 * Task to add the Cxense sub module to an existing project.
 */
open class AddCxenseModuleTask: VisiolinkGroupTask() {

    init {
        description = "Task to add the Cxense sub module to an existing project"
    }

    @TaskAction
    fun action() {
        addSubModule("CxenseAdProvider", "cxense")

        println("Done adding Cxense")
    }
}