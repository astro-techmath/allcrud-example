pluginManagement {
    providers.gradleProperty("allcrudGeneratorLocalPath").orNull?.let { includeBuild(it) }
}

rootProject.name = "allcrud-example"

providers.gradleProperty("allcrudLocalPath").orNull?.let { includeBuild(it) }
