package com.visiolink.app.task

import org.gradle.api.tasks.TaskAction

/**
 * Task to add the AdTech sub module to an existing project.
 */
open class AddAdtechModuleTask: VisiolinkGroupTask() {

    init {
        description = "Task to add the AdTech sub module to an existing project"
    }

    @TaskAction
    fun action() {
        addSubModule("AdTechAdProvider", "adtech")

        println("Done adding AdTech")
    }
}