import ing.espinoza.architectcoders.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("architectcoders.android.library.compose")
            }

            dependencies {
                add("implementation", project(":feature:common"))
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())

                add("implementation", project(":test:unit"))
                add("implementation", libs.findLibrary("kotlinx.coroutines.test").get())
                add("implementation", libs.findLibrary("turbine").get())
            }
        }
    }
}