// =============================================================================
// Arquivo: com.marin.catfeina.ui.PrivacyPolicyScreen.kt
// Descrição: Tela "Política de Privacidade".
// =============================================================================
package com.marin.catfeina.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marin.catfeina.R
import com.marin.catfeina.ui.Icones
import com.marin.catfeina.ui.theme.CatfeinaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.titulo_politica_privacidade)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icones.Voltar,
                            contentDescription = stringResource(R.string.botao_voltar)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Política de Privacidade do Catfeina\n",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Última atualização: [Data da última atualização]",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "1. Introdução\n\n" +
                            "Nós, os criadores do Catfeina, estamos comprometidos em proteger " +
                            "a sua privacidade. Esta política de privacidade descreve como " +
                            "coletamos, usamos e protegemos suas informações quando você usa " +
                            "o nosso aplicativo. Ao utilizar o Catfeina, você concorda com os " +
                            "termos desta política.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "2. Informações que Coletamos\n\n" +
                            "O Catfeina foi projetado para ser um aplicativo de finanças " +
                            "pessoais focado na privacidade. As suas informações financeiras, " +
                            "como transações, metas e orçamentos, são armazenadas " +
                            "exclusivamente no seu dispositivo. Nós não coletamos, acessamos " +
                            "ou armazenamos essas informações em nossos servidores. " +
                            "Qualquer dado que seja enviado para servidores externos (como " +
                            "serviços de backup que você pode configurar) será feito apenas " +
                            "com a sua permissão explícita.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "3. Como Usamos as Suas Informações\n\n" +
                            "Como não coletamos suas informações financeiras, elas não são " +
                            "usadas por nós para nenhum propósito. Os dados que você insere " +
                            "no aplicativo são utilizados apenas para as funcionalidades " +
                            "do próprio aplicativo, como a visualização de gráficos, o " +
                            "monitoramento de metas e a geração de relatórios, tudo de " +
                            "forma local no seu dispositivo.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "4. Segurança dos Dados\n\n" +
                            "Valorizamos a segurança dos seus dados. O Catfeina usa as " +
                            "funcionalidades de segurança do sistema operacional do seu " +
                            "dispositivo para proteger as informações armazenadas localmente, " +
                            "como o uso de `EncryptedSharedPreferences` para dados sensíveis. " +
                            "A funcionalidade de autenticação biométrica é opcional e visa " +
                            "aprimorar a sua segurança e a proteção dos seus dados.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "5. Seus Direitos\n\n" +
                            "Você tem controle total sobre os seus dados no aplicativo. " +
                            "Você pode adicionar, editar ou excluir qualquer informação a " +
                            "qualquer momento. A exclusão do aplicativo do seu dispositivo " +
                            "resultará na exclusão de todos os dados locais.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "6. Contato\n\n" +
                            "Se tiver alguma dúvida sobre esta política de privacidade, " +
                            "por favor, entre em contato conosco através do e-mail: " +
                            "suporte@catfeina.com.br.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrivacyPolicyScreenPreview() {
    CatfeinaTheme {
        PrivacyPolicyScreen(onNavigateBack = {})
    }
}