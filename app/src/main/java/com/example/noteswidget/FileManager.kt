package com.example.noteswidget
import android.content.Context
import java.io.IOException

class FileManager {

    companion object {
        val fileName = "note.txt"
        fun savetext(context : Context, text: String): Boolean {
            return try {
                context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                    it.write(text.toByteArray())
                }
                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }

        fun loadtext(context : Context): String {
            return try {
                context.openFileInput(fileName).bufferedReader().useLines { lines ->
                    lines.fold("") { some, text ->
                        //if on first line only return text
                        if (some.isEmpty()) {
                            text
                        } else {
                            "$some\n$text"
                        }
                    }
                }

            } catch (e: IOException) {
                return e.printStackTrace().toString()
            }
        }
    }
}