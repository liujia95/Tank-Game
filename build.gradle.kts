import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    application
}

application{
    mainClassName="me.liujie95.game.AppKt"
}

repositories {
    mavenCentral()
    maven {setUrl("https://jitpack.io")}
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.shaunxiao","kotlinGameEngine","0.0.4")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}