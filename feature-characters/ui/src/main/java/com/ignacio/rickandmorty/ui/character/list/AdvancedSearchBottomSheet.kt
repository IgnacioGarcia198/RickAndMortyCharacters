package com.ignacio.rickandmorty.ui.character.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui.character.mapping.localized
import com.ignacio.rickandmorty.ui_common.composables.AppBottomSheet
import com.ignacio.rickandmorty.ui_common.composables.Spinner
import com.ignacio.rickandmorty.ui_common.extensions.blankExplicit

@Composable
fun AdvancedSearchBottomSheet(
    show: Boolean,
    criteria: CharacterListQueryCriteria,
    updateCriteria: (CharacterListQueryCriteria) -> Unit,
    onClose: () -> Unit = {},
) {
    AppBottomSheet(
        show = show,
        title = R.string.advanced_search_title,
        skipPartiallyExpanded = true,
        onClose = onClose,
    ) {
        val context = LocalContext.current
        val nameContentDesc =
            context.getString(R.string.advanced_search_enter_name, criteria.name.blankExplicit())
        val speciesContentDesc = context.getString(
            R.string.advanced_search_enter_species,
            criteria.species.blankExplicit()
        )
        val typeContentDesc =
            context.getString(R.string.advanced_search_enter_type, criteria.type.blankExplicit())
        val statusContentDesc =
            context.getString(R.string.advanced_search_select_status, criteria.status.localized(context))
        val genderContentDesc =
            context.getString(R.string.advanced_search_select_gender, criteria.gender.localized(context))
        OutlinedTextField(
            value = criteria.name,
            onValueChange = { updateCriteria(criteria.copy(name = it)) },
            label = { Text(stringResource(id = R.string.name)) },
            modifier = Modifier
                .semantics {
                    contentDescription = nameContentDesc
                }
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = criteria.species,
            onValueChange = { updateCriteria(criteria.copy(species = it)) },
            label = { Text(stringResource(id = R.string.species)) },
            modifier = Modifier
                .semantics { contentDescription = speciesContentDesc }
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = criteria.type,
            onValueChange = { updateCriteria(criteria.copy(type = it)) },
            label = { Text(stringResource(id = R.string.type)) },
            modifier = Modifier
                .semantics { contentDescription = typeContentDesc }
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spinner(
            label = stringResource(id = R.string.status),
            selected = criteria.status,
            entries = CharacterListQueryCriteria.Status.entries,
            modifier = Modifier
                .semantics {
                    contentDescription = statusContentDesc
                }
                .padding(top = 16.dp),
            stringRepresentation = { it.localized(context) },
        ) {
            updateCriteria(criteria.copy(status = it))
        }

        Spinner(
            label = stringResource(id = R.string.gender),
            selected = criteria.gender,
            entries = CharacterListQueryCriteria.Gender.entries,
            modifier = Modifier
                .semantics {
                    contentDescription = genderContentDesc
                }
                .padding(top = 8.dp),
            stringRepresentation = { it.localized(context) }
        ) {
            updateCriteria(criteria.copy(gender = it))
        }
    }
}

