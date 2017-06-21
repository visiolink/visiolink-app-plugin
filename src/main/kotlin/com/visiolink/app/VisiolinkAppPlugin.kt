package com.visiolink.app

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApkVariantOutput
import com.visiolink.app.task.*
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.internal.plugins.DefaultExtraPropertiesExtension
import java.io.FileInputStream
import java.util.*

open class VisiolinkAppPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.create("verifyVersionControl", VerifyVersionControlTask::class.java)
        project.tasks.create("verifyNoStageUrl", VerifyNoStageUrlTask::class.java)
        project.tasks.create("generateChangeLog", GenerateChangeLogTask::class.java)
        project.tasks.create("increaseMajorVersionName", IncreaseMajorVersionNameTask::class.java)
        project.tasks.create("increaseMinorVersionName", IncreaseMinorVersionNameTask::class.java)
        project.tasks.create("increaseBuildVersionName", IncreaseBuildVersionNameTask::class.java)
        project.tasks.create("getFlavors", GetFlavorsTask::class.java)
        project.tasks.create("addAdtechModule", AddAdtechModuleTask::class.java)
        project.tasks.create("addAndroidTvModule", AddAndroidTvModuleTask::class.java)
        project.tasks.create("addCxenseModule", AddCxenseModuleTask::class.java)
        project.tasks.create("addDfpModule", AddDfpModuleTask::class.java)
        project.tasks.create("addInfosoftModule", AddInfosoftModuleTask::class.java)
        project.tasks.create("addKindleModule", AddKindleModuleTask::class.java)
        project.tasks.create("addSpidModule", AddSpidModuleTask::class.java)
        project.tasks.create("addTnsDkModule", AddTnsGallupDkModuleTask::class.java)
        project.tasks.create("addTnsNoModule", AddTnsGallupNoModuleTask::class.java)

        project.tasks.whenTaskAdded { task ->
            if (task.name.startsWith("generate") && task.name.endsWith("ReleaseBuildConfig")) {
                //println("Task name: ${task.name}")

                task.dependsOn("verifyVersionControl")
                task.dependsOn("verifyNoStageUrl")
            }
            if (task.name.startsWith("pre") && task.name.endsWith("ReleaseBuild")) {
                //println("Task name: ${task.name}")

                task.dependsOn("generateChangeLog")
            }
        }

        //Set output file name for release builds
        val android = project.extensions.getByName("android") as AppExtension
        android.applicationVariants.all { variant ->
            if (variant.buildType.name == "release") {
                //If apkPath has been defined in ~/.gradle/gradle.properties or local.properties
                if (project.hasProperty("apkPath")) {
                    //TODO:
                    //releaseDir = apkPath + "/" + rootProject.name
                }

                variant.outputs.filterIsInstance(ApkVariantOutput::class.java).forEach {
                    val fileName = "${variant.name}_${variant.versionName.replace(".", "")}_${variant.versionCode}.apk"
                    //println("Setting APK file name to $fileName")
                    it.outputFileName = fileName
                }
            }
        }

        val ext = project.extensions.getByName("ext") as DefaultExtraPropertiesExtension

        //Equivalent to project.ext.getVersionCodeTimestamp = { -> }
        ext.set("getVersionCodeTimestamp", closure {
            if (project.hasProperty("devBuild")) {
                1
            } else {
                dateFormat("yyMMddHHmm").format(Date()).toInt()
            }
        })

        //Equivalent to project.ext.getVersionNameFromFile = { -> }
        ext.set("getVersionNameFromFile", closure {
            val versionPropsFile = project.file("version.properties")
            if (versionPropsFile.canRead()) {
                val versionProps = Properties()
                versionProps.load(FileInputStream(versionPropsFile))
                val versionMajor = versionProps.getProperty("versionMajor").trim()
                val versionMinor = versionProps.getProperty("versionMinor").trim()
                val versionBuild = versionProps.getProperty("versionBuild").trim()

                "$versionMajor.$versionMinor.$versionBuild"
            } else {
                throw GradleException("Could not read version.properties!")
            }
        })
    }
}