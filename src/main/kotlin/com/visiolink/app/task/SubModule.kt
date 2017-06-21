package com.visiolink.app.task

import com.visiolink.app.execute
import com.visiolink.app.print
import java.io.File

/**
 * Add the git module to the project
 */
fun addSubModule(repoModuleName: String, folderModuleName: String) {
    val file = File(folderModuleName)
    if (file.exists()) {
        println("Module folder already exists")
        return
    }

    //Add sub module
    "git submodule add --force git@git.e-pages.dk:Device/Android/Modules/$repoModuleName.git $folderModuleName".execute().print()

    "git submodule update --init $folderModuleName".execute().print()
    "git -C $folderModuleName pull".execute().print()
    "git add $folderModuleName".execute().print()

    //Add the module to settings.gradle
    val settingsGradleFile = File("settings.gradle")
    settingsGradleFile.appendText("\ninclude ':$folderModuleName'")
    "git add settings.gradle".execute().print()

    //Add the module dependency to app/modules.gradle
    addDependency(folderModuleName)
    "git add app/modules.gradle".execute().print()
}

/**
 * Add the module dependency to app/build.gradle
 */
fun addDependency(folderModuleName: String) {
    // Read the contents of build.gradle
    val buildGradleFile = File("app/modules.gradle")
    val lines = buildGradleFile.readLines()

    // Write the contents back and insert new depend.
    buildGradleFile.writeText("")
    for (line in lines) {
        buildGradleFile.appendText(line + "\n")

        if (line.contains("dependencies")) {
            if (folderModuleName == "kindle")
                buildGradleFile.appendText("    kindleFlavorImplementation project(':$folderModuleName')\n")
            else
                buildGradleFile.appendText("    implementation project(':$folderModuleName')\n")
        }
    }
}