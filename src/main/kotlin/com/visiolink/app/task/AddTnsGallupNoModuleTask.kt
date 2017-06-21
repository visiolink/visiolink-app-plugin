package com.visiolink.app.task

import org.gradle.api.tasks.TaskAction

open class AddTnsGallupNoModuleTask: VisiolinkGroupTask() {

    init {
        description = "Task to add the TNS NO sub module to an existing project"
    }

    @TaskAction
    fun action() {
        addSubModule("TNSGallupNO", "tns_no")

        println("Done adding TNSGallupNO")
    }
}