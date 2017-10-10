plugins {
    kotlin("jvm", "1.1.51")
    `maven-publish`
    `java-gradle-plugin`
}

dependencies {
    compileOnly(gradleApi())
    implementation("com.android.tools.build:gradle:3.0.0-beta7")
    implementation(kotlin("stdlib", "1.1.51"))
    implementation("org.json:json:20160810")
}

repositories {
    jcenter()
    google()
}

publishing {
    publications {
        create("appPlugin", MavenPublication::class.java) {
            groupId = "com.visiolink"
            artifactId = "app"
            version = "0.6"

            from(components["java"])
        }
    }
}