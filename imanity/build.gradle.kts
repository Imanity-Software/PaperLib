plugins {
    java
}

repositories {
    maven("https://maven.imanity.dev/repository/imanity-libraries/")
}

dependencies {
    compileOnly("org.imanity.imanityspigot:api:2021.07.3b1")
    compile(project(":"))
}