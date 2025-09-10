// =============================================================================
// Arquivo: app/build.gradle.kts
// Descrição: Configuração otimizada do Gradle para o módulo :app.
// =============================================================================

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.marin.catfeina"
    compileSdk = libs.versions.compileSdk.get().toInt()
    flavorDimensions += "app"

    defaultConfig {
        applicationId = "com.marin.catfeina"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        buildConfigField("boolean", "ROOM_EXPORT_SCHEMA", "true")
        buildConfigField("int", "VERSION_CODE", libs.versions.versionCode.get())
        buildConfigField("String", "VERSION_NAME", "\"${libs.versions.versionName.get()}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["dagger.hilt.android.internal.disableAndroidSuperclassValidation"] = "true"
    }

    buildTypes {
        debug {
            isCrunchPngs = false
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            buildConfigField("boolean", "ROOM_EXPORT_SCHEMA", "false")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.addAll(
                listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-Xannotation-default-target=param-property"
                )
            )
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.incremental", "true")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
        jniLibs.keepDebugSymbols += listOf(
            "**/libandroidx.graphics.path.so",
            "**/libdatastore_shared_counter.so"
        )
    }

    lint {
        enable.add("DuplicateStrings")
        // Gera o relatório em formato SARIF
        sarifReport = true
        abortOnError = false
        checkReleaseBuilds = true
        warningsAsErrors = false
    }

    sourceSets["main"].java.srcDirs("src/main/java", "src/main/kotlin")
}

dependencies {
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.base)
    implementation(libs.bundles.compose.ui)

    // DI e Room
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.room)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.room.compiler)

    // AndroidX extras
    implementation(libs.androidx.animation.graphics)
    implementation(libs.androidx.core.animation)

    // Testes
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.compose.testing)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// =============================================================================
// Tarefas: Abertura automática de relatórios Lint
// =============================================================================

fun abrirRelatorioLint(path: String) {
    val os = System.getProperty("os.name").lowercase()
    val comando = when {
        os.contains("win") -> arrayOf("cmd", "/c", "start", "\"\"", path)
        os.contains("mac") -> arrayOf("open", path)
        os.contains("nix") || os.contains("nux") || os.contains("aix") -> arrayOf("xdg-open", path)
        else -> null
    }
    comando?.let { Runtime.getRuntime().exec(it) } ?: println("SO não suportado para abrir relatório.")
}

tasks.register("openDebugLintReport") {
    description = "Abre o relatório HTML do Lint para a variante debug."
    group = "Verification"
    val reportFile = layout.buildDirectory.file("reports/lint-results-debug.html")
    doLast {
        val file = reportFile.get().asFile
        if (file.exists()) abrirRelatorioLint(file.absolutePath)
        else println("Relatório Debug não encontrado: ${file.absolutePath}")
    }
}

tasks.register("openReleaseLintReport") {
    description = "Abre o relatório HTML do Lint para a variante release."
    group = "Verification"
    val reportFile = layout.buildDirectory.file("reports/lint-results-release.html")
    doLast {
        val file = reportFile.get().asFile
        if (file.exists()) abrirRelatorioLint(file.absolutePath)
        else println("Relatório Release não encontrado: ${file.absolutePath}")
    }
}

project.afterEvaluate {
    listOf("lintDebug", "lintVitalReportDebug", "reportLintDebug").firstNotNullOfOrNull {
        tasks.findByName(it)
    }?.finalizedBy(tasks.named("openDebugLintReport"))

    listOf("lintRelease", "lintVitalReportRelease", "reportLintRelease").firstNotNullOfOrNull {
        tasks.findByName(it)
    }?.finalizedBy(tasks.named("openReleaseLintReport"))
}

// =============================================================================
// Tarefa: Cópia de banco de dados e imagens do projeto catfeina_php
// =============================================================================

val caminhoBase = "C:/marin/apps/catfeina_php"
val dbOrigem = file("$caminhoBase/data/catfeina.db")
val imagensOrigem = file("$caminhoBase/webroot/catfeina")
val pastaAssets = layout.projectDirectory.dir("src/main/assets").asFile
val pastaDbDestino = File(pastaAssets, "databases")
val pastaImagensDestino = File(pastaAssets, "catfeina")

tasks.register<Delete>("limparAssets") {
    delete(pastaDbDestino, pastaImagensDestino)
}

val copiarDadosParaAssets = tasks.register("copiarDadosParaAssets") {
    dependsOn("limparAssets")
    doLast {
        if (!dbOrigem.exists()) throw GradleException("Banco de dados não encontrado: ${dbOrigem.absolutePath}")
        if (!imagensOrigem.exists() || !imagensOrigem.isDirectory) throw GradleException("Pasta de imagens inválida: ${imagensOrigem.absolutePath}")

        pastaDbDestino.mkdirs()
        pastaImagensDestino.mkdirs()

        dbOrigem.copyTo(File(pastaDbDestino, dbOrigem.name), overwrite = true)

        imagensOrigem.walkTopDown().filter { it.isFile }.forEach { arquivo ->
            val destinoRelativo = arquivo.relativeTo(imagensOrigem)
            val destinoFinal = File(pastaImagensDestino, destinoRelativo.path)
            destinoFinal.parentFile.mkdirs()
            arquivo.copyTo(destinoFinal, overwrite = true)
        }

        val dbCopiado = File(pastaDbDestino, dbOrigem.name).exists()
        val imagensCopiadas = pastaImagensDestino.listFiles()?.isNotEmpty() == true

        println("Banco de dados: ${if (dbCopiado) "copiado com sucesso." else "falhou."}")
        println("Imagens: ${if (imagensCopiadas) "copiadas com sucesso." else "falharam."}")

        if (!dbCopiado || !imagensCopiadas) throw GradleException("Falha na cópia dos dados. Verifique os arquivos e permissões.")
    }
}

tasks.named("preBuild") {
    dependsOn(copiarDadosParaAssets)
}