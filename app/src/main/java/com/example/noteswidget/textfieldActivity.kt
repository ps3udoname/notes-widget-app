package com.example.noteswidget



import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import com.example.noteswidget.ui.theme.NotesWidgetTheme


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            NotesWidgetTheme {
                val keyboardController = LocalSoftwareKeyboardController.current
                val initialText = FileManager.loadtext(this)
                val focusRequester = remember { FocusRequester() }
                var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                    mutableStateOf(TextFieldValue(initialText))
                }


                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        // it is crucial that the update is fed back into BasicTextField in order to
                        // see updates on the text
                        text = it
                        Log.d("MainaActivity", "onValueChange: $it")
                        FileManager.savetext(this, it.text)
                        updateTextViewText()
                    },
                    //get cursor on textfield
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        }
                )
                LaunchedEffect(key1 = Unit) {
                    focusRequester.requestFocus()
                }
            }
        }
    }

    private fun updateTextViewText() {
        //send an identical intent as android to update the widget
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