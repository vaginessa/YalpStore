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

package com.github.yeriomin.yalpstore.task;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;

import com.github.yeriomin.yalpstore.R;
import com.github.yeriomin.yalpstore.model.App;
import com.github.yeriomin.yalpstore.view.DialogWrapper;

import java.util.ArrayList;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

public abstract class SystemRemountTask extends TaskWithProgress<List<String>> {

    static private final String MOUNT_RW = "mount -o rw,remount,rw /system";
    static private final String MOUNT_RO = "mount -o ro,remount,ro /system";

    protected App app;
    protected boolean busybox;

    abstract protected List<String> getCommands();

    public SystemRemountTask(Activity context, App app) {
        this.context = context;
        this.app = app;
    }

    public App getApp() {
        return app;
    }

    public void setBusybox(boolean busybox) {
        this.busybox = busybox;
    }

    @Override
    protected void onPreExecute() {
        prepareDialog(
            R.string.dialog_message_remounting_system,
            R.string.dialog_title_remounting_system
        );
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(String... params) {
        List<String> commands = new ArrayList<>();
        commands.add(MOUNT_RW);
        commands.addAll(getCommands());
        commands.add(MOUNT_RO);
        return Shell.SU.run(commands);
    }

    @Override
    protected void onPostExecute(List<String> output) {
        super.onPostExecute(output);
        if (null != output) {
            for (String outputLine: output) {
                Log.i(getClass().getSimpleName(), outputLine);
            }
        }
        showRebootDialog();
    }

    protected String getBusyboxCommand(String command) {
        return (busybox ? "busybox " : "") + command;
    }

    private void showRebootDialog() {
        new DialogWrapper((Activity) context)
            .setMessage(R.string.dialog_message_reboot_required)
            .setTitle(R.string.dialog_title_reboot_required)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new RebootTask().execute();
                    dialog.dismiss();
                }
            })
            .setNegativeButton(R.string.dialog_two_factor_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .show()
        ;
    }
}
