package com.example.noteswidget



import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.example.noteswidget.ui.theme.NotesWidgetTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            NotesWidgetTheme {

                val focusRequester = remember { FocusRequester() }
                var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                    mutableStateOf(TextFieldValue(FileManager.loadtext(this), TextRange(0, 7)))
                }
                LaunchedEffect(key1 = Unit) {
                    focusRequester.requestFocus()
                }

                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        // it is crucial that the update is fed back into BasicTextField in order to
                        // see updates on the text
                        text = it
                        FileManager.savetext(this, it.text)
                        updateButtonText()
                    },
                    modifier = Modifier.focusRequester(focusRequester),
//                    colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Transparent,
//                        focusedContainerColor = Transparent,
//                        errorContainerColor = Transparent
//                    ) doesnt seem to make a diffrence with dialog theme
                )
            }
        }
    }

    private fun updateButtonText() {
        val i = Intent(this, Widget::class.java)
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        val appWidgetManager = AppWidgetManager.getInstance(this)
                val thisAppWidget = ComponentName(
                    this.packageName,
                    Widget::class.java.getName())
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget)
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

        sendBroadcast(i)
    }


}