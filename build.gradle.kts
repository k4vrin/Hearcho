import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.arrowOptics) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.ktor) apply false
    alias(libs.plugins.ktlint) apply false
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    extensions.configure<KtlintExtension> {
        additionalEditorconfig.put("ktlint_function_naming_ignore_when_annotated_with", "Composable")
        filter {
            exclude("build/**")
            exclude("**/build/**")
            exclude("**/generated/**")
            exclude(Spec { it.file.path.contains("build/generated") })
        }
    }

    tasks.withType<BaseKtLintCheckTask>().configureEach {
        exclude("build/**")
        exclude("**/build/**")
        exclude("**/generated/**")
        exclude(Spec { it.file.path.contains("build/generated") })
        if (!name.contains("KotlinScript")) {
            setSource(
                fileTree(projectDir) {
                    include("src/**/*.kt")
                }
            )
        }
    }

    extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        source.setFrom(
            fileTree(projectDir) {
                include("src/main/**/*.kt")
                include("src/*Main/**/*.kt")
            }
        )
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = "11"
        reports {
            html.required.set(true)
            sarif.required.set(true)
            xml.required.set(false)
            txt.required.set(false)
        }
    }

    tasks.matching { it.name == "check" }.configureEach {
        dependsOn("detekt", "ktlintCheck")
    }
}
