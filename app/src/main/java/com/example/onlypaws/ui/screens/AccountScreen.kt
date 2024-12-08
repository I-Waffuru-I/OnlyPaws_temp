package com.example.onlypaws.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.onlypaws.models.USER_INTERESTS
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.main.MainAction
import com.example.onlypaws.models.register.RegisterState
import com.example.onlypaws.ui.components.TagsBox

@Composable
fun AccountScreen(

    user : UserProfile,
    onAction : ()->Unit,
    onLogOutClick : ()->Unit,
){

    ConstraintLayout(

    ) {
        val (loginBtn, accountImage, accountNameTxt,boxes) = createRefs()

        Button(
            onClick = { onLogOutClick() },
            modifier = Modifier.constrainAs(loginBtn) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ) {
            Text("Meow out!")
        }

        Image(
            painter = rememberAsyncImagePainter(user.accountImageLink),
            contentDescription = user.accountName,
            modifier = Modifier.constrainAs(accountImage) {

            }
        )

        Text(
            user.accountName,
            modifier = Modifier.constrainAs(accountNameTxt) {
                top.linkTo(accountImage.bottom,5.dp)
            }
        )

        Column (
           modifier = Modifier.constrainAs(boxes) {
               top.linkTo(accountNameTxt.bottom,5.dp)
           }
        ){

            USER_INTERESTS.forEach {
                TagsBox(it.value,{},Modifier.fillMaxWidth().padding(5.dp))
            }

        }


    }
}