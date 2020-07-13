import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.bmuschko.gradle.docker.tasks.container.*
import org.liquibase.gradle.LiquibaseTask

plugins {
    kotlin("jvm") version "1.3.70"
    id("org.liquibase.gradle") version "2.0.1"
    id("com.bmuschko.docker-remote-api") version "4.10.0"
}

group = "org.erittenhouse"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/ktor")
    jcenter()
    maven("https://dl.bintray.com/kordlib/Kord")
}

dependencies {
    val ktorVersion = "1.3.2"
    val coroutinesVersion = "1.3.4"
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")

    implementation("io.ktor:ktor-client:$ktorVersion")
    implementation("io.ktor:ktor-client-websockets:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")

    implementation("com.gitlab.kordlib.kord:kord-core:0.5.8")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.10.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.2")

    implementation("io.github.cdimascio:java-dotenv:5.1.4")
}

liquibase {
    activities {
        register("sqlDBSetup") {
            arguments = mapOf(
                "changeLogFile" to "changelogFileLocation",
                "driver" to "com.mysql.jdbc.Driver",
                "classpath" to "liquibase-files-dir/",
                "url" to "jdbc:mysql://database-url",
                "username" to "db-username",
                "password" to "db-password"
            )
        }
    }

    runList = "sqlDBSetup"
}
tasks {
    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn", "-Xinline-classes")
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    val createDatabaseContainerTask = register<DockerCreateContainer>("createDatabaseContainer") {
        group = "docker"

        autoRemove.set(true)
        imageId.set("mysql:8")
        containerName.set("playtime-detector-db")
        containerId.set("test-database")
        envVars.set(mapOf(
            "MYSQL_ROOT_PASSWORD" to "some-password",
            "MYSQL_DATABASE" to "dbname"
        ))
        portBindings.set(listOf("3306:3306"))
    }
    val spinupDatabaseContainerTask = register<DockerStartContainer>("spinUpDatabaseContainer") {
        group = "docker"
        dependsOn(createDatabaseContainerTask)

        containerId.set("test-database")
    }

    register<LiquibaseTask>("startProvisionedDatabase") {
        group = "docker"

        dependsOn(spinupDatabaseContainerTask)
        command = "update"
        args = listOf("--runList=sqlDBSetup")

        doFirst {
            println("Waiting for the container to spin up fully...")
            Thread.sleep(15000)
        }
    }
}
