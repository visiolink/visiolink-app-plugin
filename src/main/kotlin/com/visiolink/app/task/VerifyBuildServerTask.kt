package com.visiolink.app.task

import com.visiolink.app.execute

/**
 * Task to verify we are running on build server
 */
open class VerifyBuildServerTask : VisiolinkGroupTask() {

    init {
        description = "Checks if release build is run on build server"
    }

    @org.gradle.api.tasks.TaskAction
    fun action() {
        val result = "users".execute()
        if (result.trim() != "build")
            throw org.gradle.api.GradleException("Only build server can make release builds")
    }
}