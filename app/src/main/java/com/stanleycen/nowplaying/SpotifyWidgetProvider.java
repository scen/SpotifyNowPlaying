package com.stanleycen.nowplaying;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by scen on 10/18/15.
 */
public class SpotifyWidgetProvider extends AppWidgetProvider {

    /**
     *
     * @param context
     * @param text
     * @param color
     * @param typeface RobotFont typeface value
     * @param fontSize in dps
     */
    private static Bitmap renderTextBitmap(Context context, String text, int color, int typefaceID, float fontSizeDP) {
        float fontSizePX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, fontSizeDP, context.getResources().getDisplayMetrics());
        int padding = (int)(fontSizePX / 9);

        Paint paint = new Paint();
        Typeface typeface = RobotoFont.obtainTypeface(context, typefaceID);
        paint.setAntiAlias(true);
        paint.setTypeface(typeface);
        paint.setColor(color);
        paint.setTextSize(fontSizePX);

        int textWidth = (int) (paint.measureText(text) + 2*padding);
        int textHeight = (int) (fontSizePX * 4. / 3);

        Bitmap bitmap = Bitmap.createBitmap(textWidth, textHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, padding, fontSizePX, paint);

        return bitmap;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ComponentName componentName = new ComponentName(context, SpotifyWidgetProvider.class);

        // we ignore appWidgetIds and compute it ourselves.
        appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

        for (int i = 0; i < appWidgetIds.length; i++) {
            int widgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.spotify_widget);

            // Android doesn't let us set the font on a remote text view.. so...

            views.setImageViewBitmap(R.id.tracknameText, renderTextBitmap(context,
                    NowPlayingState.getTrackname(context), 0xffffffff, RobotoFont.ROBOTO_LIGHT, 18.f));

            views.setImageViewBitmap(R.id.albumText, renderTextBitmap(context,
                    NowPlayingState.getAlbum(context), 0xffffffff, RobotoFont.ROBOTO_THIN, 18.f));

            views.setImageViewBitmap(R.id.artistText, renderTextBitmap(context,
                    NowPlayingState.getArtist(context), 0xffffffff, RobotoFont.ROBOTO_THIN, 18.f));


            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }
}
