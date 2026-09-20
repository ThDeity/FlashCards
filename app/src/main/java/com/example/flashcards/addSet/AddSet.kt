package com.example.flashcards.addSet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcards.App
import com.example.flashcards.util.AddSetViewModelFactory
import com.example.flashcards.data.MethodOfPreparation

@Composable
fun AddSet(
    onDone: () -> Unit = {},
    viewModel: AddSetViewModel = viewModel(
    factory = AddSetViewModelFactory (
            (LocalContext.current.applicationContext as App).repository
        )
    )
){
    var name by remember { mutableStateOf("") }
    var method by remember { mutableStateOf(MethodOfPreparation.ByYourSelf) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        addSetScreen(name = name,
            onNameChange = {name = it},
            onMethodChange = {method = it},
            onAddClick = {
                viewModel.addSet(name, method)
                onDone()
            }
        )
    }
}

@Composable
fun addSetScreen(
    name: String,
    onNameChange: (String) -> Unit,
    onMethodChange: (MethodOfPreparation) -> Unit,
    onAddClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var method by remember { mutableStateOf(MethodOfPreparation.ByYourSelf) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = name,
            label = { Text("Name") },
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Chosen method: $method", fontSize = 20.sp)

        Box(modifier = Modifier.weight(1f)){
            TextButton({ menuExpanded = !menuExpanded }) {
                Text("Change method", fontSize = 18.sp)
            }

            DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                DropdownMenuItem(
                    text = { Text("Test") },
                    onClick = {
                        menuExpanded = false
                        onMethodChange(MethodOfPreparation.Test)
                        method = MethodOfPreparation.Test
                    }
                )

                DropdownMenuItem(
                    text = { Text("By Your Self") },
                    onClick = {
                        menuExpanded = false
                        onMethodChange(MethodOfPreparation.ByYourSelf)
                        method = MethodOfPreparation.ByYourSelf
                    }
                )

                DropdownMenuItem(
                    text = { Text("Input") },
                    onClick = {
                        menuExpanded = false
                        onMethodChange(MethodOfPreparation.Input)
                        method = MethodOfPreparation.Input
                    }
                )
            }
        }

        IconButton(onClick = { onAddClick() }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add", modifier = Modifier.scale(2f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Test(){
    addSetScreen(
        name = "assd",
        onNameChange = {},
        onMethodChange = {},
        onAddClick = {}
    )
}