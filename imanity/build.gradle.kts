plugins {
    java
    `maven-publish`
}

group = "io.papermc.paperlib"
version = "1.0.7-SNAPSHOT"
val javadoc by tasks.existing(Javadoc::class)
val jar by tasks.existing

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://maven.imanity.dev/repository/imanity-libraries/")
}

dependencies {
    compileOnly("org.imanity.imanityspigot:api:2021.07.3b1")
    compile(project(":"))
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
                name.set("PaperLib-imanity")
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