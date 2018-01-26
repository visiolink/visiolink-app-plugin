plugins {
    kotlin("jvm") version "1.2.21"
    `maven-publish`
    `java-gradle-plugin`
}

dependencies {
    compileOnly(gradleApi())
    implementation("com.android.tools.build:gradle:3.0.1")
    implementation(kotlin("stdlib", "1.2.21"))
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
            version = "1.3"

            from(components["java"])
        }
    }
}