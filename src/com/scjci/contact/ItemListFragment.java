package com.scjci.contact;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.scjci.contact.dummy.ContactAdapter;
import com.scjci.contact.dummy.DummyContent;
import com.util.MenuHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A list fragment representing a list of Items. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link ItemDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ItemListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListFragment() {
    }

    private MenuHelper menuHelper;
    private Cursor dataSource ;
    private DummyContent dummyContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //notify Fragment has menu
        setHasOptionsMenu(true);
        this.dummyContent = new DummyContent(getActivity());
        initData(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("aa", "aaa");
        //setListShownNoAnimation(true);

        //this.getListView().setFastScrollEnabled(true);
        //this.getListView().setTextFilterEnabled(true);
        //Log.i("filter",String.valueOf(this.getListView().isTextFilterEnabled()));

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState
                    .getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        }
        Log.i("onAttach", "attach");
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("onDetach", "detach");
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position,
                                long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(((TextView) view.findViewById(R.id.userId)).getText().toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("onSaveInstanceState", "onSaveInstanceState*****");
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("onStart", "onStart****");
    }

    @Override
    public void onResume() {
        super.onStart();
        Log.i("onResume", "onResume****");
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy", "onDestroy****");
        dataSource.close();
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        Log.i("showModal", "onCreateOptionsMenu****");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.optionsmenu, menu);
        menuHelper = new MenuHelper(getActivity(), menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
            searchView.setSearchableInfo(((SearchManager) getActivity().getSystemService(getActivity().SEARCH_SERVICE)).getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint("輸入電話或姓名");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                public boolean onQueryTextSubmit(String paramString) {
                    menu.findItem(R.id.menu_search).collapseActionView();
                    return false;
                }
                public boolean onQueryTextChange(String paramString) {
                    return false;
                }
            });
        }
    }

    public void initData(final Context context) {
        new Thread(new Runnable() {
            Context mContext = context;
            @Override
            public void run() {
                Looper.prepare();
                try {
                    Thread.sleep(500);
                    initHandler.obtainMessage(0, mContext).sendToTarget();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
               // dataSource.clear();
                dataSource = dummyContent.initData();
                initHandler.obtainMessage(1, mContext).sendToTarget();
                Looper.loop();
            }

            Handler initHandler = new Handler() {
                ProgressDialog pd;
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            if(menuHelper != null){
                                menuHelper.setRefreshActionItemState(true);
                            }
                            if (msg.obj != null) {
                                pd = ProgressDialog.show((Context) msg.obj, "通知", "資料同步中", false, false);
                            }
                            break;
                        case 1:
                            setListAdapter( new ContactAdapter(
                                    getActivity(),
                                    R.layout.fragment_item_list,
                                    dataSource,
                                    new String[]{"NAME", "AVATARPATH", "_id"},
                                    new int[]{R.id.name, R.id.avatarPath, R.id.userId},
                                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));

                            Log.i("showModal", "No");
                            if(menuHelper != null){
                                menuHelper.setRefreshActionItemState(false);
                            }
                            if (pd != null) {
                                pd.dismiss();
                            }
                            break;
                    }
                }
            };
        }).start();
    }

    public void reloadData(final Context context) {
        new Thread(new Runnable() {
            Context mContext = context;
            @Override
            public void run() {
                Looper.prepare();
                try {
                    Thread.sleep(500);
                    reloadHandler.obtainMessage(0, mContext).sendToTarget();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                dataSource = dummyContent.reloadData();
                reloadHandler.obtainMessage(1, null).sendToTarget();
                Looper.loop();
            }

            Handler reloadHandler = new Handler() {
                ProgressDialog pd;
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            if(menuHelper != null){
                                menuHelper.setRefreshActionItemState(true);
                            }
                            if (msg.obj != null) {
                                pd = ProgressDialog.show((Context) msg.obj, "通知", "資料同步中", false, false);
                            }
                            break;
                        case 1:
                            ((SimpleCursorAdapter)ItemListFragment.this.getListAdapter()).changeCursor(dataSource);
                            Log.i("showModal", "No");
                            if(menuHelper != null){
                                menuHelper.setRefreshActionItemState(false);
                            }
                            if (pd != null) {
                                pd.dismiss();
                            }
                            break;
                    }
                }
            };
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        final Context mContext = this.getActivity();
        switch (item.getItemId()) {
            case R.id.sync:
                reloadData(mContext);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}