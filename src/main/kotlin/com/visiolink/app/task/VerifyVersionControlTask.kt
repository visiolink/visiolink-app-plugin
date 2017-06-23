package com.visiolink.app.task

import com.visiolink.app.execute

/**
 * Create task to check Git repo
 */
open class VerifyVersionControlTask: VisiolinkGroupTask() {

    init {
        description = "Checks if all files are committed before release builds"
    }

    @org.gradle.api.tasks.TaskAction
    fun action() {
        if(project.hasProperty("ignoreChecks")) return

        val result = "git diff-files".execute()
        if (result.trim().isNotEmpty())
            throw org.gradle.api.GradleException("Git not clean, commit changes before running release build")
    }
}