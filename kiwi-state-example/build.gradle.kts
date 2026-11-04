plugins {
    application
}

dependencies {
    implementation(project(":kiwi-state-core"))

    testImplementation(libs.kotlinx.coroutines.test)
}

application {
    mainClass =
        "io.github.kiwiwhale.kiwistate.example.ExampleApplicationKt"
}
