package com.example.flashcards.createCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcards.App
import com.example.flashcards.util.CreatingCardViewFactory
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.flashcards.data.FlashcardSet
import com.example.flashcards.data.MethodOfPreparation

@Composable
fun CreatingCard(
    setsId: Long,
    onDone: () -> Unit = {},
    viewModel: CreateCardViewModel = viewModel(
        factory = CreatingCardViewFactory (
            (LocalContext.current.applicationContext as App).repository
        )
    )
){
    var front by remember { mutableStateOf("") }
    var back by remember { mutableStateOf("") }

    LaunchedEffect(setsId) {
        viewModel.load(setsId)
    }

    val deck = viewModel.deck.collectAsState().value ?: FlashcardSet(name = "", methodOfPreparation = MethodOfPreparation.Input)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        CreatingCardScreen(
            front = front,
            back = back,
            deck,
            onFrontChange = { front = it },
            onBackChange = { back = it },
            onAddCard = {
                viewModel.addCard(setsId, front, back)
                onDone()
            }
        )
    }
}

@Composable
fun CreatingCardScreen(
    front: String,
    back: String,
    deck: FlashcardSet,
    onFrontChange: (String) -> Unit,
    onBackChange: (String) -> Unit,
    onAddCard: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("deck: ${deck.name}", fontSize = 20.sp)

        OutlinedTextField(
            value = front,
            label = { Text("Front") },
            onValueChange = onFrontChange,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )

        OutlinedTextField(
            value = back,
            onValueChange = onBackChange,
            label = { Text("Back") },
            singleLine = false,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )

        IconButton(onClick = { onAddCard() }) {//viewModel.addCard(setsId, front, back)
            Icon(imageVector = Icons.Default.Add, contentDescription = "add",
                modifier = Modifier.scale(2f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Test(){
    CreatingCardScreen(
        front = "fdsf",
        back = "fdsfds",
        deck = FlashcardSet(name = "Name", methodOfPreparation = MethodOfPreparation.Input),
        onFrontChange = {},
        onBackChange = {},
        onAddCard = {}
    )
}