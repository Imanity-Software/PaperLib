plugins {
    java
    `maven-publish`
}

val javadoc by tasks.existing(Javadoc::class)
val jar by tasks.existing

group = "io.papermc"
version = "1.0.7-SNAPSHOT"

val mcVersion = "1.16.5-R0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("com.google.code.findbugs:jsr305:1.3.9")
    compileOnly("com.destroystokyo.paper:paper-api:$mcVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

javadoc {
    isFailOnError = false
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(javadoc)
    classifier = "javadoc"
    from(javadoc)
}

// A couple aliases just to simplify task names
tasks.register("install") {
    group = "publishing"
    description = "Alias for publishToMavenLocal"
    dependsOn(tasks.named("publishToMavenLocal"))
}

tasks.register("deploy") {
    group = "publishing"
    description = "Alias for publish"
    dependsOn(tasks.named("publish"))
}

artifacts {
    add("archives", sourcesJar)
    add("archives", javadocJar)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())

            pom {
                name.set("PaperLib")
                description.set("Plugin library for interfacing with Paper specific APIs with graceful fallback that maintains Spigot compatibility.")
                url.set("https://github.com/Imanity-Software/PaperLib")
                scm {
                    url.set("https://github.com/Imanity-Software/PaperLib")
                }
                issueManagement {
                    system.set("github")
                    url.set("https://github.com/Imanity-Software/PaperLib/issues")
                }
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
            }
        }
    }

    if (project.hasProperty("imanityLibrariesUsername") && project.hasProperty("imanityLibrariesPassword")) {
        val imanityLibrariesUsername: String by project
        val imanityLibrariesPassword: String by project

        val repoUrl = if (version.toString().endsWith("-SNAPSHOT")) {
            "https://maven.imanity.dev/repository/imanity-libraries/"
        } else {
            "https://maven.imanity.dev/repository/imanity-libraries/"
        }

        repositories {
            maven {
                url = uri(repoUrl)
                name = "Imanity Libraries"
                credentials {
                    username = imanityLibrariesUsername
                    password = imanityLibrariesPassword
                }
            }
        }
    }
}
