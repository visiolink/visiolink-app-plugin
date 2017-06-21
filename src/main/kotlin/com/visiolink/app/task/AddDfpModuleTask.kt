package com.visiolink.app.task

import org.gradle.api.tasks.TaskAction

/**
 * Task to add the DFP sub module to an existing project.
 */
open class AddDfpModuleTask: VisiolinkGroupTask() {

    init {
        description = "Task to add the DFP sub module to an existing project"
    }

    @TaskAction
    fun action() {
        addSubModule("DfpAdProvider", "dfp")

        println("Done adding DFP")
    }
}