package com.lusifer.shabdkosh.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.lusifer.shabdkosh.R;
import com.lusifer.shabdkosh.ui.detail.DetailActivity;
import com.lusifer.shabdkosh.ui.main.MainActivity;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent svcIntent = new Intent(ctxt, StockWidgetService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(ctxt.getPackageName(),
                    R.layout.widget_recent);

            widget.setRemoteAdapter(R.id.listview, svcIntent);
            Intent clickIntent = new Intent(ctxt, MainActivity.class);

            PendingIntent clickPI = PendingIntent
                    .getActivity(ctxt, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            widget.setPendingIntentTemplate(R.id.listview, clickPI);

            appWidgetManager.updateAppWidget(appWidgetId, widget);
        }
        super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
    }
}