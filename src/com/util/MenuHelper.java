package com.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.scjci.contact.R;

public class MenuHelper {
    public Menu mOptionsMenu;
    private View mRefreshIndeterminateProgressView = null;
    private Activity mActivity;

    public MenuHelper(Context context, Menu optionsMenu) {
        this.mActivity = (Activity) context;
        this.mOptionsMenu = optionsMenu;
    }

    public void setRefreshActionItemState(boolean refreshing) {
        if (mOptionsMenu == null) {
            return;
        }

        final MenuItem refreshItem = mOptionsMenu.findItem(R.id.sync);
        if (refreshItem != null) {
            if (refreshing) {
                if (mRefreshIndeterminateProgressView == null) {
                    LayoutInflater inflater = (LayoutInflater) this.mActivity
                            .getActionBar().getThemedContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    mRefreshIndeterminateProgressView = inflater.inflate(
                            R.layout.actionbar_indeterminate_progress, null);
                }

                refreshItem.setActionView(mRefreshIndeterminateProgressView);
            } else {
                refreshItem.setActionView(null);
            }
        }
    }
}
