package com.cnh.samples.apps.schools.compose.allschools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.cnh.samples.apps.school.R
import com.cnh.samples.apps.schools.data.School

@Composable
fun SchoolListItem(
    school: School,
    onClick: () -> Unit,
) {
    ImageListItem(
        name = school.schoolName,
        /** Igor - TODO if more time: imageUrl = school.imageUrl, */
        onClick = onClick
    )
}

@Composable
fun ImageListItem(
    name: String,
    /** Igor - TODO with more time: imageUrl: String, */
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier =
        Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.card_side_margin))
            .padding(bottom = dimensionResource(id = R.dimen.card_bottom_margin)),
    ) {
        Column(Modifier.fillMaxWidth()) {
            /** Igor - TODO with more time:
            GlideImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.a11y_school_item_image),
            Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.school_item_image_height)),
            contentScale = ContentScale.Crop,
            ) */

            Text(
                text = name,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.margin_normal))
                    .wrapContentWidth(Alignment.CenterHorizontally),
            )
        }
    }
}
