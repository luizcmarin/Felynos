// =============================================================================
// Arquivo: app/build.gradle.kts
// Descrição: Configuração do Gradle para o módulo :app.
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
    flavorDimensions += listOf("app")

    defaultConfig {
        applicationId = "com.marin.catfeina"
        minSdk =  libs.versions.minSdk.get().toInt()
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
            buildConfigField( "boolean", "ROOM_EXPORT_SCHEMA", "false")

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
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
            freeCompilerArgs.add("-Xannotation-default-target=param-property")
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
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        jniLibs {
            keepDebugSymbols += "**/libandroidx.graphics.path.so"
            keepDebugSymbols += "**/libdatastore_shared_counter.so"
        }
    }

    lint {
        // Gera o relatório em formato SARIF
        sarifReport = true
        abortOnError = false
        checkReleaseBuilds = true
        warningsAsErrors = false
    }

    sourceSets["main"].java.srcDirs("src/main/java", "src/main/kotlin")
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.compose.base)
    implementation(libs.bundles.compose.ui)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.room)
    implementation(libs.androidx.animation.graphics)
    implementation(libs.androidx.core.animation)

    ksp(libs.hilt.compiler)
    ksp(libs.androidx.room.compiler)

    // Dependências de teste
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.compose.testing)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// =============================================================================
// Tarefas de Abertura de Relatório Lint
// =============================================================================
tasks.register("openDebugLintReport") {
    description = "Abre o relatório HTML do Lint para a variante debug no navegador padrão."
    group = "Verification"
    val reportFileProvider = project.layout.buildDirectory.file("reports/lint-results-debug.html")
    doLast {
        val reportFile = reportFileProvider.get().asFile
        if (reportFile.exists()) {
            val reportPath = reportFile.absolutePath
            val os = System.getProperty("os.name").lowercase()
            try {
                when {
                    os.contains("win") -> Runtime.getRuntime().exec(arrayOf("cmd", "/c", "start", "\"\"", reportPath))
                    os.contains("mac") -> Runtime.getRuntime().exec(arrayOf("open", reportPath))
                    os.contains("nix") || os.contains("nux") || os.contains("aix") -> Runtime.getRuntime().exec(arrayOf("xdg-open", reportPath))
                    else -> println("SO não suportado para abrir automaticamente o relatório Lint (Debug).")
                }
            } catch (e: Exception) {
                println("Erro ao abrir relatório Lint (Debug): ${e.message}")
            }
        } else {
            println("Relatório Lint (Debug) não encontrado em: ${reportFile.absolutePath}")
        }
    }
}

tasks.register("openReleaseLintReport") {
    description = "Abre o relatório HTML do Lint para a variante release no navegador padrão."
    group = "Verification"
    val reportFileProvider = project.layout.buildDirectory.file("reports/lint-results-release.html")
    doLast {
        val reportFile = reportFileProvider.get().asFile
        if (reportFile.exists()) {
            val reportPath = reportFile.absolutePath
            val os = System.getProperty("os.name").lowercase()
            try {
                when {
                    os.contains("win") -> Runtime.getRuntime().exec(arrayOf("cmd", "/c", "start", "\"\"", reportPath))
                    os.contains("mac") -> Runtime.getRuntime().exec(arrayOf("open", reportPath))
                    os.contains("nix") || os.contains("nux") || os.contains("aix") -> Runtime.getRuntime().exec(arrayOf("xdg-open", reportPath))
                    else -> println("SO não suportado para abrir automaticamente o relatório Lint (Release).")
                }
            } catch (e: Exception) {
                println("Erro ao abrir relatório Lint (Release): ${e.message}")
            }
        } else {
            println("Relatório Lint (Release) não encontrado em: ${reportFile.absolutePath}")
        }
    }
}

project.afterEvaluate {
    val lintDebugTaskNames = listOf("lintDebug", "lintVitalReportDebug", "reportLintDebug")
    lintDebugTaskNames.firstNotNullOfOrNull { project.tasks.findByName(it) }?.let { lintTask ->
        project.tasks.findByName("openDebugLintReport")?.let { openReportTask ->
            lintTask.finalizedBy(openReportTask)
        } ?: println("AVISO: Tarefa 'openDebugLintReport' não encontrada para configurar finalizedBy.")
    } ?: println("AVISO: Nenhuma das tarefas de Lint Debug (${lintDebugTaskNames.joinToString()}) encontrada para configurar finalizedBy.")

    val lintReleaseTaskNames = listOf("lintRelease", "lintVitalReportRelease", "reportLintRelease")
    lintReleaseTaskNames.firstNotNullOfOrNull { project.tasks.findByName(it) }?.let { lintTask ->
        project.tasks.findByName("openReleaseLintReport")?.let { openReportTask ->
            lintTask.finalizedBy(openReportTask)
        } ?: println("AVISO: Tarefa 'openReleaseLintReport' não encontrada para configurar finalizedBy.")
    } ?: println("AVISO: Nenhuma das tarefas de Lint Release (${lintReleaseTaskNames.joinToString()}) encontrada para configurar finalizedBy.")
}

/*
* Copia db e imagens do projeto catfeina_php
*/
val caminhoBaseDaOrigemParaCopia = "C:/marin/apps/catfeina_php"
val dbOrigem = file("$caminhoBaseDaOrigemParaCopia/data/catfeina.db")
val imagensOrigem = file("$caminhoBaseDaOrigemParaCopia/webroot/catfeina")
val pastaAssets = project.layout.projectDirectory.dir("src/main/assets").asFile
val pastaDbDestino = File(pastaAssets, "databases")
val pastaImagensDestino = File(pastaAssets, "catfeina")

tasks.register<Delete>("limparAssets") {
    delete(pastaDbDestino, pastaImagensDestino)
}

val copiarDadosParaAssets = tasks.register("copiarDadosParaAssets") {
    dependsOn("limparAssets")

    doLast {
        println("Iniciando copia manual dos dados para assets...")

        // Verificação de origem
        if (!dbOrigem.exists()) throw GradleException("Banco de dados nao encontrado: ${dbOrigem.absolutePath}")
        if (!imagensOrigem.exists() || !imagensOrigem.isDirectory) throw GradleException("Pasta de imagens invalida: ${imagensOrigem.absolutePath}")

        // Criação dos diretórios de destino
        if (!pastaDbDestino.exists()) pastaDbDestino.mkdirs()
        if (!pastaImagensDestino.exists()) pastaImagensDestino.mkdirs()

        // Cópia do banco de dados
        val dbDestino = File(pastaDbDestino, dbOrigem.name)
        dbOrigem.copyTo(dbDestino, overwrite = true)

        // Cópia das imagens
        imagensOrigem.walkTopDown().forEach { arquivo ->
            if (arquivo.isFile) {
                val destinoRelativo = arquivo.relativeTo(imagensOrigem)
                val destinoFinal = File(pastaImagensDestino, destinoRelativo.path)
                destinoFinal.parentFile.mkdirs()
                arquivo.copyTo(destinoFinal, overwrite = true)
            }
        }

        // Verificação final
        val dbCopiado = dbDestino.exists()
        val imagensCopiadas = pastaImagensDestino.listFiles()?.isNotEmpty() == true

        println("Banco de dados: ${if (dbCopiado) "copiado com sucesso." else "falhou."}")
        println("Imagens: ${if (imagensCopiadas) "copiadas com sucesso." else "falharam."}")

        if (!dbCopiado || !imagensCopiadas) {
            throw GradleException("Falha na copia dos dados. Verifique os arquivos e permissoes.")
        }
    }
}

// Integração com o build
tasks.named("preBuild") {
    dependsOn(copiarDadosParaAssets)
}