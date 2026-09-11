package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

class MainActivity : ComponentActivity() { // main function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> // layout of the app
                    CityListScreen(
                        cities = cityRepository.cities,             // let cities be the ones in repository
                        onAddCity = {cityRepository.addCity(it)},   // it gets the parameter anyway
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) { // useless for our purposes
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() { // also useless for our purposes
    ListyCityTheme {
        Greeting("Android")
    }
}

class CityRepository { // where we store the cities
    private val _cities = mutableStateListOf( // our initial round of cities
        "Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna",
        "Tokyo", "Beijing", "Osaka", "New Delhi"
    )

    val cities: List<String> get() =  _cities   // city list loaded with our initial round

    fun addCity(city: String) {         // when fed city name, adds to cities
        _cities.add(city)
    }

    // function that deletes cities
    fun deleteCity(city: String) {      // when fed city name, removes from cities
        _cities.remove(city)
    }
}

@Composable
fun CityListScreen (                    // the actual app layout
    cities: List<String>,               // let cities be a list of strings
    onAddCity: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = newCityName,
                onValueChange = {newCityName = it},
                label = {Text("City name")},
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button( // the button that adds cities
                onClick = {
                    if (newCityName.isNotBlank()) {
                        onAddCity(newCityName)
                        newCityName = ""
                    }
                }
            ) {
                Text("Add")
            }

            // Button text code based off of the filled button code below:
            // https://developer.android.com/develop/ui/compose/components/button

            Button( // the button that deletes cities
                onClick = {
                    if (newCityName.isNotBlank()) {
                        onAddCity(newCityName)
                        newCityName = ""
                    }
                }
            ) {
                Text("Delete")
            }
        }

        LazyColumn(modifier = modifier.fillMaxSize()) { // displays the cities as city row objects
            items(cities) { city ->
                CityRow(city = city)
            }
        }
    }
}

@Composable
fun CityRow(city: String) { // when fed city name, displays city
    Text(
        text = city,
        fontSize = 28.sp,
        modifier = Modifier.fillMaxWidth().padding(horizontal=18.dp, vertical=14.dp)
    )
}
