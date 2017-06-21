#### visiolink-app-plugin

###Gradle Plugin providing custom tasks for Android app development

_Written in Kotlin :D_

Provides tasks for: 
- Adding Git submodules
- Generating Git log since last commit
- Printing all flavors as JSON
- Managing version name
- Verifying everything is committed before release builds

Build with:
`./gradlew publishMavenJavaPublicationToMavenLocal`

Then copy artifacts from `~/.m2/repository/` to your Maven repo.