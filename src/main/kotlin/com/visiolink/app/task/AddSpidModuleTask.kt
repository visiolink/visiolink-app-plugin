package com.visiolink.app.task

import org.gradle.api.tasks.TaskAction

/**
 * Task to add the SPiD sub module to an existing project.
 */
open class AddSpidModuleTask: VisiolinkGroupTask() {

    init {
        description = "Task to add the SPiD sub module to an existing project"
    }

    @TaskAction
    fun action() {
        //Add SPiD sub module
        addSubModule("SPiDAuthenticationProvider", "spid")

        println("Done adding SPiD, please call :app [addSpidCredentials]")
    }
}