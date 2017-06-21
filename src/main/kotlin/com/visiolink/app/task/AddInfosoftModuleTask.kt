package com.visiolink.app.task

import org.gradle.api.tasks.TaskAction

/**
 * Task to add the Infosoft sub module to an existing project.
 */
open class AddInfosoftModuleTask: VisiolinkGroupTask() {

    init {
        description = "Task to add the Infosoft sub module to an existing project"
    }

    @TaskAction
    fun action() {
        addSubModule("InfosoftAuthenticationProvider", "infosoft")

        println("Done adding Infosoft")
    }
}