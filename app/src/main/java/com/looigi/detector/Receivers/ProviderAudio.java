package com.looigi.detector.Receivers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.looigi.detector.R;

public class ProviderAudio extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

	    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_audio);
	    Intent configIntent = new Intent(context, Audio.class);

	    PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

	    remoteViews.setOnClickPendingIntent(R.id.imgAudio, configPendingIntent);
	    appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);		 
	}
}
