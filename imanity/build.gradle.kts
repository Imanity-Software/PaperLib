plugins {
    java
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://maven.imanity.dev/repository/imanity-libraries/")
}

dependencies {
    compileOnly("org.imanity.imanityspigot:api:2021.07.3b1")
    compile(project(":"))
}