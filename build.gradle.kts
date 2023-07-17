plugins {
    java
    idea
    `maven-publish`
}

group = "com.thebombzen"
version = "1.1.1"

repositories.mavenCentral()
dependencies.compileOnly("org.jetbrains:annotations:24.0.1")

publishing {
    publications.create<MavenPublication>("mavenCommon").from(components["java"])

    repositories {
        mavenLocal()
        maven {
            val releasesRepoUrl = "https://maven.generations.gg/releases"
            val snapshotsRepoUrl = "https://maven.generations.gg/snapshots"
            url = uri(if (project.version.toString().endsWith("SNAPSHOT") || project.version.toString().startsWith("0")) snapshotsRepoUrl else releasesRepoUrl)
            name = "Generations-Repo"
            credentials {
                username = project.properties["repoLogin"]?.toString()
                password = project.properties["repoPassword"]?.toString()
            }
        }
    }
}