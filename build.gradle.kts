plugins {
    kotlin("jvm")
    `maven-publish`
    `java-gradle-plugin`
}

dependencies {
    compileOnly(gradleApi())
    implementation("com.android.tools.build:gradle:3.0.0-alpha5")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.1.3")
    implementation("org.json:json:20160810")
}

repositories {
    jcenter()
    google()
}

publishing {
    publications {
        create("mavenJava", MavenPublication::class.java) {
            groupId = "com.visiolink"
            artifactId = "app"
            version = "0.4"

            from(components.getByName("java"))
        }
    }
}