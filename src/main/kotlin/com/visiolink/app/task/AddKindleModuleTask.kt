package com.visiolink.app.task

import org.gradle.api.tasks.TaskAction

/**
 * Task to add the Kindle sub module to an existing project.
 */
open class AddKindleModuleTask: VisiolinkGroupTask() {

    init {
        description = "Task to add the Kindle sub module to an existing project"
    }

    @TaskAction
    fun action() {
        addSubModule("Kindle", "kindle")

        println("Done adding Kindle, please modify modules.gradle and replace kindleFlavor with your flavor name")
    }
}