package com.example.onlypaws.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.onlypaws.models.InterestGroup
import com.example.onlypaws.ui.theme.OnlyPawsTheme

@Composable
fun TagsBox(
    interestGroup: InterestGroup,
    onItemSelectClick : (Int)->Unit,
    modifier : Modifier = Modifier,
){
    ConstraintLayout(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .border(2.dp,MaterialTheme.colorScheme.outline),
    ) {
        val (topRow, itemsRow) = createRefs()
        Row (
            modifier = Modifier.constrainAs(topRow){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
                .background(MaterialTheme.colorScheme.surfaceBright)
        ){
            Text(
                text = interestGroup.title,
                modifier = Modifier
                    .weight(0.8f)
            )
            Button(
                onClick = { onItemSelectClick(interestGroup.id) },
                Modifier.weight(0.3f)
            ) {
                Text("Select")
            }
        }
        Row(
            Modifier.constrainAs(itemsRow){
                top.linkTo(topRow.bottom,2.dp)
                start.linkTo(topRow.start)
            }
        ) {
            interestGroup.values.forEach {
                Text(it,Modifier.padding(horizontal = 2.dp))
            }
        }

    }


}


@Preview(name="LightMode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name="DarkMode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewTagsBox(

) {

    OnlyPawsTheme {
        Surface {

            TagsBox(
                InterestGroup(0,"Test Interest",listOf("Why not?","Trying stuff out")),
                {}
            )
        }

    }
}