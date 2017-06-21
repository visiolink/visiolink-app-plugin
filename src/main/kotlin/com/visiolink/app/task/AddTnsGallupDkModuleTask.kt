package com.visiolink.app.task

import org.gradle.api.tasks.TaskAction

open class AddTnsGallupDkModuleTask: VisiolinkGroupTask() {

    init {
        description = "Task to add the TNS DK sub module to an existing project"
    }

    @TaskAction
    fun action() {
        addSubModule("TNSGallupDK", "tns_dk")

        println("Done adding TNSGallupDK")
    }
}