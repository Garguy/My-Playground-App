package com.example.mycarrierapp

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mycarrierapp.data.models.PokemonListEntry
import com.example.mycarrierapp.ui.screens.pokemonlist.PokemonListViewModel
import com.example.mycarrierapp.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun PokemonListScreen(
    onClick: () -> Unit,
    viewModel: PokemonListViewModel = hiltViewModel(),
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pokemon_logo),
                contentDescription = "Pokemon Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(
                hint = "Search...", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.searchPokemonList(it)
            }

            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController)

        }

    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = MaterialTheme.colors.onBackground),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, shape = CircleShape)
                .background(color = Color.White, shape = CircleShape)
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isEmpty()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            )
        }
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (pokemonList.size % 2 == 0) {
            pokemonList.size / 2
        } else {
            pokemonList.size / 2 + 1
        }
        items(itemCount) {
            if (it >= itemCount - 1 && !endReached && !isLoading && !isSearching) {
                LaunchedEffect(key1 = true) {
                    viewModel.loadPokemonPaginated()
                }
            }
            PokemonRow(rowIndex = it, entries = pokemonList, navController = navController)
        }
    }
}

@Composable
fun PokemonEntry(
    entry: PokemonListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember { mutableStateOf(defaultDominantColor) }
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
                )
            }
    ) {
        Column {
            LaunchedEffect(key1 = entry.imageUrl) {
                withContext(Dispatchers.IO) {
                    isLoading = true
                    val stream = URL(entry.imageUrl).openStream()
                    bitmap = BitmapFactory.decodeStream(stream)
                    stream.close()
                }
                isLoading = false
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .align(CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .scale(0.5f)
                            .fillMaxSize()
                    )
                }
            } else {
                bitmap?.let {
                    viewModel.calcDominateColor(it) { color ->
                        dominantColor = color
                    }

                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = entry.pokemonName,
                        modifier = Modifier
                            .size(120.dp)
                            .align(CenterHorizontally),
                        contentScale = ContentScale.Crop,
                    )
                }
                Text(
                    text = entry.pokemonName,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun PokemonRow(
    rowIndex: Int,
    entries: List<PokemonListEntry>,
    navController: NavController
) {
    Column {
        Row {
            PokemonEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                PokemonEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonListScreenPreviewLight() {
    AppTheme {
        PokemonListScreen(onClick = { }, navController = rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PokemonListScreenPreviewDark() {
    AppTheme {
        PokemonListScreen(onClick = { }, navController = rememberNavController())
    }
}