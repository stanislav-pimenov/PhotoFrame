package com.epam.photoframe.photowidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.RemoteViews;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;

import java.util.Arrays;


public class PhotoWidget extends AppWidgetProvider {

    private final static String LOG_TAG = "photoWidgetLog";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        //DateTimeZone MSC = DateTimeZone.forID("Europe/Moscow");
        DateTimeZone UTC = DateTimeZone.forID("UTC");
        DateTime now = new DateTime(UTC);
        DateTime end = new DateTime(2015, 6, 26, 17, 0, 0, UTC);
        DateTime benchMark = new DateTime(2015, 6, 26, 0, 0, 0, UTC);
        Resources res = context.getResources();

        Days days = Days.daysBetween(now, end);

        if (now.isBefore(end)) {
            String text = String.format(resolveDaysOutputStyle(res, days.getDays(), true), days.getDays());
            views.setTextViewText(R.id.tv, text);
        } else {
            //long daysAfter = Math.abs(days.getDays()) + 1;
            long daysAfter = Math.abs(Days.daysBetween(now, benchMark).getDays()) + 1;
            String text = String.format(resolveDaysOutputStyle(res, daysAfter, false), daysAfter);
            views.setTextViewText(R.id.tv, text);
        }
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    private String resolveDaysOutputStyle(Resources res, long days, boolean before) {
        days = days % 10;
        if (before) {
            if (days == 1) {
                return res.getString(R.string.before1);
            } else if (days >= 2 && days <= 4) {
                return res.getString(R.string.before234);
            } else if (days >= 5 || days == 0) {
                return res.getString(R.string.before);
            }
        } else if (days == 1) {
            return res.getString(R.string.after1);
        } else if (days >= 2 && days <= 4) {
            return res.getString(R.string.after234);
        } else if (days >= 5 || days == 0) {
            return res.getString(R.string.after);
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
        Log.d(LOG_TAG, "onEnabled");
    }
}