/*
 * Yalp Store
 * Copyright (C) 2018 Sergey Yeriomin <yeriomin@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.yeriomin.yalpstore.notification;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.github.yeriomin.yalpstore.BlackWhiteListManager;
import com.github.yeriomin.yalpstore.Paths;

public class IgnoreUpdatesService extends IntentService {

    static public final String PACKAGE_NAME = "PACKAGE_NAME";
    static public final String VERSION_CODE = "VERSION_CODE";

    public IgnoreUpdatesService() {
        super("IgnoreUpdatesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String packageName = intent.getStringExtra(PACKAGE_NAME);
        if (TextUtils.isEmpty(packageName)) {
            Log.w(getClass().getSimpleName(), "No package name provided in the intent");
            return;
        }
        Log.i(getClass().getSimpleName(), "Adding " + packageName + " to ignore list");
        BlackWhiteListManager manager = new BlackWhiteListManager(getApplicationContext());
        if (manager.isBlack()) {
            manager.add(packageName);
        } else {
            manager.remove(packageName);
        }
        cancelNotification(packageName);
        Paths.getApkPath(getApplicationContext(), packageName, intent.getIntExtra(VERSION_CODE, 0)).delete();
    }

    private void cancelNotification(String packageName) {
        PackageManager pm = getApplicationContext().getPackageManager();
        try {
            new NotificationManagerWrapper(getApplicationContext()).cancel(
                pm.getApplicationLabel(pm.getApplicationInfo(packageName, 0)).toString()
            );
        } catch (PackageManager.NameNotFoundException e) {
            // App is not installed
        }
    }
}
