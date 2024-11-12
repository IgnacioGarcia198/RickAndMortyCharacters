package com.ignacio.rickandmorty.ui.character.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui_common.composables.Spinner

@Composable
fun AdvancedSearchBottomSheet(
    criteria: CharacterListQueryCriteria,
    onClose: () -> Unit = {},
    updateCriteria: (CharacterListQueryCriteria) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(stringResource(id = R.string.advanced_search_title), style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = criteria.name,
            onValueChange = { updateCriteria(criteria.copy(name = it)) },
            label = { Text(stringResource(id = R.string.name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = criteria.species,
            onValueChange = { updateCriteria(criteria.copy(species = it)) },
            label = { Text(stringResource(id = R.string.species)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = criteria.type,
            onValueChange = { updateCriteria(criteria.copy(type = it)) },
            label = { Text(stringResource(id = R.string.type)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spinner(
            label = stringResource(id = R.string.status),
            selected = criteria.status.name,
            entries = CharacterListQueryCriteria.Status.entries.map { it.name },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            updateCriteria(criteria.copy(status = CharacterListQueryCriteria.Status.valueOf(it)))
        }

        Spinner(
            label = stringResource(id = R.string.gender),
            selected = criteria.gender.name,
            entries = CharacterListQueryCriteria.Gender.entries.map { it.name },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            updateCriteria(criteria.copy(gender = CharacterListQueryCriteria.Gender.valueOf(it)))
        }

        Button(
            onClick = {
                onClose()
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text(stringResource(id = R.string.done))
        }
    }
}