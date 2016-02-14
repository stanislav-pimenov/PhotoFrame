package com.esprit.photoframe.photowidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.RemoteViews;
import net.danlew.android.joda.JodaTimeAndroid;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Weeks;

import java.util.Arrays;


public class PhotoWidget extends AppWidgetProvider {

    final String LOG_TAG = "photoWidgetLog";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        //DateTimeZone MSC = DateTimeZone.forID("Europe/Moscow");
        DateTimeZone UTC = DateTimeZone.forID("UTC");
        DateTime now = new DateTime(UTC);
        DateTime sexDate = new DateTime(2015, 11, 2, 0, 0, 0, UTC);
        //DateTime benchMark = new DateTime(2015, 6, 26, 0, 0, 0, UTC);
        Resources res = context.getResources();

        int weeks = Math.abs(Weeks.weeksBetween(now, sexDate).getWeeks());
        int days = Math.abs(Days.daysBetween(now, sexDate).getDays()) % 7;

        String text = String.format(resolveDaysOutputStyle(res, days), weeks, days);
        Log.d(LOG_TAG, "new text is: " + text);
        views.setTextViewText(R.id.tv, text);

        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    private String resolveDaysOutputStyle(Resources res, int days) {
        if (days == 1) {
            return res.getString(R.string.age1);
        } else if (days >= 2 && days <= 4) {
            return res.getString(R.string.age234);
        } else if (days >= 5 || days == 0) {
            return res.getString(R.string.age);
        }
        return "";
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(LOG_TAG, "onDisabled");
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        JodaTimeAndroid.init(context);
        Log.d(LOG_TAG, "onEnabled");

    }
}