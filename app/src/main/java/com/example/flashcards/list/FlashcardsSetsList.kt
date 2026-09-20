package com.example.flashcards.list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashcards.App
import com.example.flashcards.data.FlashcardSet
import com.example.flashcards.data.MethodOfPreparation
import com.example.flashcards.util.FlashcardsSetsListViewFactory
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FlashcardsSetsList(
    allSets: List<FlashcardSet>,
    onDelete: (Long) -> Unit,
    onCopySet: (Long) -> Unit,
    onEdit: (Long) -> Unit,
    onAdd: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        items(allSets, key = { it.id }) { cardsSet ->
            FlashcardSetItem(
                cardsSet = cardsSet,
                onDelete = { onDelete(cardsSet.id) },
                onCopySet = { onCopySet(cardsSet.id) },
                onEdit = {onEdit(cardsSet.id)},
                onAdd = {onAdd(cardsSet.id)}
            )
        }
    }
}

@Composable
fun FlashcardsSetListScreenWithViewModel(
    onAddClick: () -> Unit,
    onEdit: (Long) -> Unit,
    onAdd: (Long) -> Unit,
    viewModel: FlashcardsSetsListViewModel = viewModel(
        factory = FlashcardsSetsListViewFactory(
            (LocalContext.current.applicationContext as App).repository
        )
    )
){
    val allSets by viewModel.allSets.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Добавить напоминание")
            }
        }
    ) { innerPadding ->
        FlashcardsSetsList(
            allSets = allSets,
            onDelete = { id -> viewModel.deleteSet(id) },
            onCopySet = { id -> viewModel.copySet(id) },
            modifier = Modifier.padding(innerPadding),
            onEdit = {id -> onEdit(id)},
            onAdd = {id -> onAdd(id)}
        )
    }
}

@Composable
fun FlashcardSetItem(
    cardsSet: FlashcardSet,
    onDelete: (Long) -> Unit,
    onCopySet: (Long) -> Unit,
    onEdit: (Long) -> Unit,
    onAdd: (Long) -> Unit
){
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .animateContentSize()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = cardsSet.name, style = MaterialTheme.typography.titleMedium)

            Box{
                IconButton(onClick = { menuExpanded = true}) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "options")
                }

                DropdownMenu(expanded = menuExpanded,
                    onDismissRequest = {menuExpanded = false}) {
                    DropdownMenuItem(
                        text = { Text("Copy") },
                        onClick = {
                            menuExpanded = false
                            onCopySet(cardsSet.id)
                        }
                    )

                    DropdownMenuItem(
                        text = {Text("Delete")},
                        onClick = {
                            menuExpanded = false
                            onDelete(cardsSet.id)
                        }
                    )

                    DropdownMenuItem(
                        text = {Text("Add")},
                        onClick = {
                            menuExpanded = false
                            onAdd(cardsSet.id)
                        }
                    )

                    DropdownMenuItem(
                        text = {Text("Edit")},
                        onClick = {
                            menuExpanded = false
                            onEdit(cardsSet.id)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Список")
@Composable
fun PreviewReminderListScreen() {
    val now = System.currentTimeMillis()
    val mockList = listOf(
        FlashcardSet(0, "English", now, MethodOfPreparation.ByYourSelf))//,
        //FlashcardSet(1, "Spanish", now, methodOfPreparation.ByYourSelf),
        //FlashcardSet(2, "Russian", now, methodOfPreparation.ByYourSelf))

    FlashcardsSetsList(
        allSets = mockList,
        onCopySet = {},
        onDelete = {},
        modifier = Modifier,
        onEdit = {},
        onAdd = {}
    )
}