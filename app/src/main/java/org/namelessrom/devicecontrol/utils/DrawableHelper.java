/*
 *  Copyright (C) 2013 - 2014 Alexander "Evisceration" Martinz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.namelessrom.devicecontrol.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.TypedValue;

import org.namelessrom.devicecontrol.Application;
import org.namelessrom.devicecontrol.Logger;
import org.namelessrom.devicecontrol.theme.AppResources;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Helps with ddddddrawwabllessr5hhwr5hbwb
 */
public class DrawableHelper {

    @Nullable public static Drawable applyColorFilter(Drawable drawable, int color) {
        if (drawable == null) {
            Logger.w("DrawableHelper", "drawable is null!");
            return null;
        }
        final LightingColorFilter lightingColorFilter = new LightingColorFilter(Color.BLACK, color);
        drawable.setColorFilter(lightingColorFilter);
        return drawable;
    }

    @Nullable public static Drawable applyAccentColorFilter(int drawableRes) {
        Drawable drawable = getDrawable(drawableRes);
        return applyColorFilter(drawable, AppResources.get().getAccentColor());
    }

    @Nullable public static Drawable applyAccentColorFilter(Drawable drawable) {
        return applyColorFilter(drawable, AppResources.get().getAccentColor());
    }

    @Nullable public static Drawable getDrawable(int drawableRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Application.get().getDrawable(drawableRes);
        }

        //noinspection deprecation
        return Application.get().getResources().getDrawable(drawableRes);
    }

    public static int getSelectableBackgroundResId(Context context) {
        TypedValue outValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        return outValue.resourceId;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static InputStream bitmapToInputStream(Bitmap bitmap) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }

}
