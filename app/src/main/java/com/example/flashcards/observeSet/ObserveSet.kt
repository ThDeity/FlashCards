package com.example.flashcards.observeSet

import android.graphics.Color
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcards.App
import com.example.flashcards.data.Flashcard
import com.example.flashcards.data.FlashcardSet
import com.example.flashcards.data.MethodOfPreparation
import com.example.flashcards.data.SetWithCards
import com.example.flashcards.util.ObserveSetViewModelFactory
import org.intellij.lang.annotations.JdkConstants

@Composable
fun ObserveSet(
    setId: Long,
    onAdd: (Long) -> Unit,
    onDone: () -> Unit = {},
    viewModel: ObserveSetViewModel = viewModel(
    factory = ObserveSetViewModelFactory (
        (LocalContext.current.applicationContext as App).repository
    ))
){
    LaunchedEffect(setId) {
        viewModel.load(setId)
    }

    val setWithCards by viewModel.setWithCards.collectAsState()
    val allCards = setWithCards?.flashcards ?: emptyList()
    val setOfCards = setWithCards?.set ?: FlashcardSet(name = "name", methodOfPreparation = MethodOfPreparation.Test)

    ObserveSetScreen(allCards, setOfCards,
        { onAdd(setId) },
        {
            viewModel.deleteSet(setId)
            onDone()
        })
}

@Composable
fun FlashcardItem(
    card: Flashcard
){
    var textExpanded by remember { mutableStateOf(true) }
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .animateContentSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = card.front, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))

                IconButton(onClick = { textExpanded = !textExpanded }) {
                    Icon(
                        imageVector = if (textExpanded) Icons.Default.KeyboardArrowDown
                        else Icons.Default.KeyboardArrowUp,
                        contentDescription = "moreText"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box{
                    IconButton(onClick = { menuExpanded = !menuExpanded }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "options"
                        )
                    }

                    DropdownMenu(expanded = menuExpanded,
                        onDismissRequest = {menuExpanded = false}) {
                        DropdownMenuItem(
                            text = { Text("Copy") },
                            onClick = {
                                menuExpanded = false
                            }
                        )

                        DropdownMenuItem(
                            text = {Text("Delete")},
                            onClick = {
                                menuExpanded = false
                            }
                        )

                        DropdownMenuItem(
                            text = {Text("Edit")},
                            onClick = {
                                menuExpanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = textExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = card.back)
            }
        }
    }
}

@Composable
fun ObserveSetScreen(
    allCards: List<Flashcard>,
    cardsSet: FlashcardSet,
    onAdd: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Name: ${cardsSet.name}",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Method of preparation: ${cardsSet.methodOfPreparation}",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (allCards.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)          // занимает всё свободное место
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Empty")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)          // ВАЖНО: вместо fillMaxSize()
                    .fillMaxWidth()
            ) {
                items(allCards, key = { it.id }) { card ->
                    FlashcardItem(card)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onDelete(cardsSet.id) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    Modifier.scale(2f)
                )
            }
            IconButton(onClick = { onAdd(cardsSet.id) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "addCard",
                    Modifier.scale(2f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun test(){
    val card = Flashcard(0, "sdsad", "dsfsaf", 0)
    val mockList = listOf(card)
    val setOfCards = FlashcardSet(name = "name", methodOfPreparation = MethodOfPreparation.Test)

    ObserveSetScreen(mockList, setOfCards, {}, {})
}