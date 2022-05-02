repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net")
}

val remapper by configurations.creating

dependencies {
    remapper(project(":remapper"))
}

evaluationDependsOnChildren()

val remappedJar = buildDir.resolve("remapped.jar")

val remapJar = tasks.register<JavaExec>("remapJar") {
    val classes = project(":remapper").tasks.getByName("classes")
    val jar = project(":example-code").tasks.getByName<Jar>("jar")
    dependsOn(classes, jar)
    classpath = remapper
    mainClass.set("remapper.Main")
    args = listOf(
        jar.archiveFile.get().asFile.absolutePath,
        remappedJar.absolutePath,
        file("mappings.tiny").absolutePath
    )
}

tasks.register<JavaExec>("testWithRemapping") {
    dependsOn(remapJar)
    classpath = files(remappedJar)
    mainClass.set("example.Parent")
}

tasks.register<JavaExec>("testWithoutRemapping") {
    val jar = project(":example-code").tasks.getByName<Jar>("jar")
    dependsOn(jar)
    classpath = files(jar.archiveFile.get().asFile)
    mainClass.set("example.Parent")
}
