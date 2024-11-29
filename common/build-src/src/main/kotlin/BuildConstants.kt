import org.gradle.api.JavaVersion

object BuildConstants {
    val JAVA_VERSION = JavaVersion.VERSION_1_8 // TODO: Update Java version
    const val TEST_INSTRUMENTATION_RUNNER = "com.ignacio.rickandmorty.android_utils.runner.CustomTestRunner"
}
