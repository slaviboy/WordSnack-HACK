package com.slaviboy.worldsnackhack.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slaviboy.worldsnackhack.R
import com.slaviboy.worldsnackhack.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreenComposable(
    viewModel: HomeScreenViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
    ) {
        MenuBox(
            viewModel = viewModel,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.word_snack_logo),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp)
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillWidth
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(id = R.string.title),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontSize = 30.sp
        )
        Text(
            text = stringResource(id = R.string.description),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .align(Alignment.CenterHorizontally)
        )
        TextField(
            value = viewModel.enteredText,
            onValueChange = { viewModel.enteredText = it },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.letters)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            singleLine = true,
            shape = RoundedCornerShape(3.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            viewModel.enteredText = ""
                        }
                )
            }
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp)
                .fillMaxHeight()
        ) {
            viewModel.answerWords.forEach { message ->
                Text(
                    text = message,
                    modifier = Modifier
                        .align(Alignment.Start),
                    fontSize = 20.sp
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = viewModel.rangeSliderLabel?.let {
                stringResource(id = it, viewModel.rangeSliderMin, viewModel.rangeSliderMax)
            } ?: "",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 20.dp),
            fontSize = 16.sp
        )
        RangeSlider(
            modifier = Modifier
                .padding(horizontal = 30.dp),
            value = viewModel.rangeSliderMinMax,
            onValueChange = {
                viewModel.rangeSliderMinMax = it
                viewModel.rangeSliderLabel = R.string.range_label
            },
            valueRange = 0f..20f,
            onValueChangeFinished = {
                viewModel.rangeSliderLabel = null
                viewModel.generateWords()
            },
            steps = 20
        )
        Button(
            onClick = { viewModel.generateWords() },
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(id = R.string.generate_words).uppercase())
        }
    }
}