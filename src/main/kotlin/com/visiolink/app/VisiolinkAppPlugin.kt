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
        with(project.tasks) {
            create("verifyVersionControl", VerifyVersionControlTask::class.java)
            create("verifyNoStageUrl", VerifyNoStageUrlTask::class.java)
            create("verifyBuildServer", VerifyBuildServerTask::class.java)
            create("generateProjectChangeLog", GenerateProjectChangeLogTask::class.java)
            create("generateGenericChangeLog", GenerateGenericChangeLogTask::class.java)
            create("increaseMajorVersionName", IncreaseMajorVersionNameTask::class.java)
            create("increaseMinorVersionName", IncreaseMinorVersionNameTask::class.java)
            create("increaseBuildVersionName", IncreaseBuildVersionNameTask::class.java)
            create("getFlavors", GetFlavorsTask::class.java)
            create("addAdtechModule", AddAdtechModuleTask::class.java)
            create("addAndroidTvModule", AddAndroidTvModuleTask::class.java)
            create("addCxenseModule", AddCxenseModuleTask::class.java)
            create("addDfpModule", AddDfpModuleTask::class.java)
            create("addInfosoftModule", AddInfosoftModuleTask::class.java)
            create("addKindleModule", AddKindleModuleTask::class.java)
            create("addSpidModule", AddSpidModuleTask::class.java)
            create("addTnsDkModule", AddTnsGallupDkModuleTask::class.java)
            create("addTnsNoModule", AddTnsGallupNoModuleTask::class.java)
            create("addComScoreModule", AddComScoreModuleTask::class.java)
            create("tagProject", TagProjectTask::class.java)

            whenTaskAdded { task ->
                if (task.name.startsWith("generate")
                        && task.name.endsWith("ReleaseBuildConfig")
                        && !project.hasProperty("ignoreChecks")) {
                    //println("Task name: ${task.name}")

                    task.dependsOn("tagProject")
                    task.dependsOn("generateProjectChangeLog")
                    task.dependsOn("verifyBuildServer")
                    task.dependsOn("verifyVersionControl")
                    task.dependsOn("verifyNoStageUrl")
                }
                if (task.name == "preWrapperReleaseBuild") {
                    //println("Task name: ${task.name}")

                    task.dependsOn("generateGenericChangeLog")
                }
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
                    val fileName = "${variant.flavorName}_${variant.versionName.replace(".", "")}_${variant.versionCode}.apk"
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