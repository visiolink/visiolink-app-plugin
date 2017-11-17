package com.visiolink.app.task

import com.visiolink.app.execute
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Generates Generic change log since last tag
 */
open class GenerateGenericChangeLogTask : VisiolinkGroupTask() {

    init {
        description = "Generates Generic change log since last tag"
    }

    @TaskAction
    fun action() {
        val tag = "git describe --tags --abbrev=0".execute(File("generic")).trim()

        val logCmd = "git log $tag..HEAD --oneline"
        val log = logCmd.execute(File("generic"))
        //println("Got log: $log")

        project.file("src/wrapper/assets/git.log").writeText(log)
    }
}