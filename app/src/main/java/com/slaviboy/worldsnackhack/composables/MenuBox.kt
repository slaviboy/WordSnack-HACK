package com.slaviboy.worldsnackhack.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.slaviboy.worldsnackhack.R
import com.slaviboy.worldsnackhack.viewmodels.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBox(
    viewModel: HomeScreenViewModel,
    modifier: Modifier
) {
    val context = LocalContext.current
    ExposedDropdownMenuBox(
        expanded = viewModel.expanded,
        onExpandedChange = {
            viewModel.expanded = !viewModel.expanded
        },
        modifier = modifier
    ) {
        TextField(
            value = viewModel.selectedItem.name,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = stringResource(id = R.string.choose_language))
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = viewModel.expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.baseline_language_24),
                    contentDescription = null
                )
            }
        )
        DropdownMenu(
            expanded = viewModel.expanded,
            onDismissRequest = { viewModel.expanded = false },
            modifier = Modifier
                .background(Color.White)
                .exposedDropdownSize()
        ) {
            viewModel.languages.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = language.name
                        )
                    }, onClick = {
                        viewModel.clearAll(language)
                        viewModel.updateLanguage(context, language.localeCode)
                    }
                )
            }
        }
    }
}