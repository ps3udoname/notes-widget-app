package com.example.noteswidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews


/**
 * Implementation of App Widget functionality.
 */
class Widget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }


internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        /* context = */ context,
        /* requestCode = */  0,
        /* intent = */ Intent(context, MainActivity::class.java),
        /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // Get the layout for the widget and attach an onClick listener to
    // the button.

    val views: RemoteViews = RemoteViews(
        context.packageName,
        R.layout.widget
    ).apply {
        //launch the activity if textview is clicked
        setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)
        //update the text on the textview
        setTextViewText(R.id.appwidget_text, FileManager.loadtext(context))
    }
    // Tell the AppWidgetManager to perform an update on the current
    // widget.
    appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}

