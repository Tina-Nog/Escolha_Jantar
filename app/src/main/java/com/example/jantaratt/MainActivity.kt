package com.example.jantaratt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JantarApp()
        }
    }
}

@Composable
fun JantarApp() {
    val navController = rememberNavController()

    // Definir o NavHost com as duas telas
    NavHost(navController = navController, startDestination = "TelaInicial") {
        composable("TelaInicial") {
            TelaInicial(navController = navController)
        }
        composable("Sorteio") {
            JantarAppSorteio()
        }
    }
}
@Composable
fun TelaInicial(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize() // Preenche toda a tela
            .background(Color(0xFFE79039)) // Cor de fundo do Box
    ) {
        // Imagem da elipse, colocada atrás da imagem de fundo da panela
        Image(
            painter = painterResource(id = R.drawable.ellipse_), // Substitua com o ID da sua imagem de elipse
            contentDescription = "Imagem de Elipse",
            modifier = Modifier
                .fillMaxWidth() // Preenche toda a largura
                .height(463.dp) // Defina a altura da imagem da elipse (ajuste conforme necessário)
                .align(Alignment.TopCenter) // Alinha a imagem ao topo
                .alpha(30f) // Opacidade ajustada
        )

        // Imagem de fundo da panela
        Image(
            painter = painterResource(id = R.drawable.imagem_fundo), // Imagem de fundo da panela
            contentDescription = "Imagem de Fundo da Panela",
            modifier = Modifier
                .fillMaxWidth() // Preenche toda a largura
                .height(515.dp) // Define a altura da imagem
                .align(Alignment.TopCenter) // Alinha a imagem ao topo
                .alpha(30f) // Ajusta a opacidade da imagem
        )

        // Coloca o Box com o texto logo abaixo da imagem
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp), // Espaçamento entre os itens
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomCenter) // Alinha ao fundo da tela
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color(0xFF19642D), shape = RoundedCornerShape(12.dp))
                    .border(2.dp, Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bem-vindo à escolha, vamos sortear seu jantar?",
                    color = Color.White,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(1f) // Aplica a animação de fade-in
                )
            }

            // Botão para ir para a tela de sorteio
            Button(
                onClick = { navController.navigate("Sorteio") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19642D)),
                modifier = Modifier.size(250.dp, 60.dp)
            ) {
                Text(
                    text = "Escolher o Jantar",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun JantarAppSorteio() {
    // Lista de pratos com as novas opções
    val pratos = listOf(
        Pair(R.drawable.imagem_pizza, "Pizza"),
        Pair(R.drawable.imagem_lasanha, "Lasanha"),
        Pair(R.drawable.imagem_hanburgue, "Hambúrguer"),
        Pair(R.drawable.imagem_sushi, "Sushi"),
        Pair(R.drawable.imagem_frango, "Frango"),
        Pair(R.drawable.imagem_sopa, "Sopa"),
        Pair(R.drawable.imagem_moqueca, "Moqueca"),
        Pair(R.drawable.imagem_strogonoff, "Strogonoff"),
        Pair(R.drawable.imagem_macarrao, "Macarrão"),
        Pair(R.drawable.imagem_carne, "Carne")
    )

    var pratoSelecionado by remember { mutableStateOf(pratos[0]) }
    var sorteando by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (sorteando) 360f else 0f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE79039)),
        contentAlignment = Alignment.Center
    ) {
        // Imagem de fundo (somente uma vez)
        Image(
            painter = painterResource(id = R.drawable.image_fundod),
            contentDescription = "Imagem de Fundo",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .alpha(30f) // Define a opacidade da imagem de fundo
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Box com o nome do prato com borda
            Box(
                modifier = Modifier
                    .background(Color(0xFF19642D), shape = RoundedCornerShape(8.dp))
                    .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp)) // Borda adicionada
                    .padding(16.dp)
            ) {
                Text(
                    text = pratoSelecionado.second, // Nome do prato
                    fontSize = 24.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Imagem do prato que está sendo exibido com rotação
            Image(
                painter = painterResource(id = pratoSelecionado.first),
                contentDescription = "Imagem do Prato",
                modifier = Modifier
                    .size(250.dp)
                    .rotate(rotation) // animação de rotação
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Box com a descrição do prato com borda
            Box(
                modifier = Modifier
                    .background(Color(0xFF19642D), shape = RoundedCornerShape(8.dp))
                    .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp)) // Borda adicionada
                    .padding(16.dp)
            ) {
                Text(
                    text = "Deliciosa opção de ${pratoSelecionado.second} para o seu jantar. Uma escolha irresistível!",
                    fontSize = 25.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Botão para sortear o prato
            Button(
                onClick = {
                    sorteando = true
                    // Adiciona um pequeno atraso para simular o sorteio
                    GlobalScope.launch {
                        delay(1000)
                        pratoSelecionado = pratos[Random.nextInt(pratos.size)] // Sorteia um prato aleatório
                        sorteando = false
                    }
                },
                enabled = !sorteando,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF19642D)),
                modifier = Modifier.size(250.dp, 60.dp)
            ) {
                Text(
                    text = "Escolher o Jantar",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJantarApp() {
    JantarApp()
}
