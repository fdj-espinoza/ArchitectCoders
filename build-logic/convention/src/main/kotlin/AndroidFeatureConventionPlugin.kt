import com.android.build.gradle.LibraryExtension
import ing.espinoza.architectcoders.addAndroidTestDependencies
import ing.espinoza.architectcoders.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("architectcoders.android.library.compose")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            dependencies {
                add("implementation", project(":feature:common"))
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())

                add("testImplementation", project(":test:unit"))
                add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
                add("testImplementation", libs.findLibrary("turbine").get())
            }

            addAndroidTestDependencies()
        }
    }
}