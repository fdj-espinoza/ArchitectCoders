import ing.espinoza.architectcoders.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DiLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                pluginManager.apply("architectcoders.di.library")
                pluginManager.apply("dagger.hilt.android.plugin")
            }
            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
            }

            //pluginManager.apply("architectcoders.di.library")
            //dependencies.add("implementation", libs.findLibrary("koin.compose").get())
        }
    }
}