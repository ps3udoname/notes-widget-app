package com.example.noteswidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
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

//    override fun onReceive(context: Context?, intent: Intent?) {
//        super.onReceive(context, intent)
////        println("dnsof")
////        println(intent?.action)
//            Log.d("onReceive", "onReceiveudasbdh")
//
//            val extras = intent?.extras
//            if (extras != null) {
//                val appWidgetManager = AppWidgetManager.getInstance(context)
//                val thisAppWidget = ComponentName(
//                    context!!.packageName,
//                    Widget::class.java.getName()
//                )
//                val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget)
//
//                onUpdate(context, appWidgetManager, appWidgetIds)
//
//    }
//}

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
        setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)
        setTextViewText(R.id.appwidget_text, FileManager.loadtext(context))
    }
    Log.d("onUpdate", "onUPdateudasbdh")
    // Tell the AppWidgetManager to perform an update on the current
    // widget.
    appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}
//fun updateButtonTextView(context: Context, appWidgetId: Int) {
//    val appWidgetManager = AppWidgetManager.getInstance(context)
//    val remoteViews = RemoteViews(context.getPackageName(), R.layout.widget).also {
//        setTextViewText(R.id.appwidget_Button, "Updated text1")
//    }
//    appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
//}
//
//fun getRefreshPendingIntent(context: Context?, appWidgetId: Int): PendingIntent {
//    val intent = Intent("my.package.ACTION_UPDATE_WIDGET")
//    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
//    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//}
