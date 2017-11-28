package com.visiolink.app.task

import com.android.build.gradle.BasePlugin

/**
 * Task to print flavor, appId and version as JSON
 */
open class GetFlavorsTask : VisiolinkGroupTask() {

    init {
        description = "Print flavors as JSON"
    }

    @org.gradle.api.tasks.TaskAction
    fun action() {

        androidBasePlugin
                ?: throw org.gradle.api.GradleException("You must apply the Android plugin or the Android library plugin before using the visiolink plugin")

        val result = org.json.JSONArray()

        val android = project.extensions.getByName("android") as com.android.build.gradle.AppExtension

        android.applicationVariants.all { variant ->
            if (variant.buildType.name != "release") {
                //println "Skipping build type ${variant.buildType.name}."
                return@all
            }

            var productFlavorNames = variant.productFlavors.map { it.name.capitalize() }
            if (productFlavorNames.isEmpty()) {
                productFlavorNames = listOf("")
            }
            val productFlavorName = productFlavorNames.joinToString()
            val flavor = productFlavorName[0].toLowerCase() + productFlavorName.substring(1)

            val flavorJson = org.json.JSONObject()
            flavorJson.put("flavor", flavor)
            flavorJson.put("applicationId", variant.productFlavors[0].applicationId)
            flavorJson.put("versionName", variant.productFlavors[0].versionName ?: variant.versionName)

            result.put(flavorJson)
        }

        println(result)
    }

    private val androidBasePlugin: BasePlugin?
        get() = (project.plugins.findPlugin("com.android.application") ?:
                project.plugins.findPlugin("com.android.library")) as BasePlugin?
}