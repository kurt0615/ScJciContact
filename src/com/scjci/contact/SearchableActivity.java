package com.scjci.contact;

import com.scjci.contact.dummy.ContactAdapter;
import com.scjci.contact.dummy.DummyContent;
import com.util.MenuHelper;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class SearchableActivity extends ListActivity {

    private MenuHelper menuHelper;
    private Cursor dataSource ;
    private DummyContent dummyContent;
    private String queryString;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("xxx", "onSerch");
		handleIntent(getIntent());
	}

	@Override
    protected void onNewIntent(Intent intent) {
		Log.i("xxx", "onSerch2");
        handleIntent(intent);
    }

	 private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            this.getActionBar().setDisplayHomeAsUpEnabled(true);
            this.queryString = intent.getStringExtra(SearchManager.QUERY);
            this.dummyContent = new DummyContent(this);
            searchData(this);
        }else{
        	this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("showModal", "onCreateOptionsMenu****");
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        this.menuHelper = new MenuHelper(this, menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
            searchView.setQueryHint("輸入電話或姓名");
            searchView.setSearchableInfo(((SearchManager) getSystemService(this.SEARCH_SERVICE)).getSearchableInfo(getComponentName()));
            menu.findItem(R.id.menu_search).expandActionView();
            searchView.setQuery(queryString, false);
            menu.findItem(R.id.sync).setVisible(false);
            menu.findItem(R.id.menu_search).setOnActionExpandListener(new OnActionExpandListener() {
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    Log.i("xa","_Searchabl");
                    SearchableActivity.this.finish();
                    return true;
                }

                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

	@Override
	public void finish() {
		Log.i("life", "finish_SearchableAcitvity");
		super.finish();
	}

	@Override
	protected void onDestroy() {
		Log.i("life", "onDestroy_SearchableAcitvity");
	    super.onDestroy();
        dataSource.close();
	}

	@Override
	public boolean onSearchRequested() {
		Log.i("life", "onSearchRequested_SearchableAcitvity");
	    return super.onSearchRequested();
	}

    @Override
    public void onListItemClick(ListView listView, View view, int position,
                                long id) {
        super.onListItemClick(listView, view, position, id);
        Intent detailIntent = new Intent(this, ItemDetailActivity.class);
        detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, ((TextView) view.findViewById(R.id.userId)).getText().toString());
        startActivity(detailIntent);
    }

    public void searchData(final Context context) {
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

                dataSource = dummyContent.searchData(queryString);
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
                                pd = ProgressDialog.show((Context) msg.obj, "通知", "搜尋中", false, false);
                            }
                            break;
                        case 1:
                            setListAdapter( new ContactAdapter(
                                    mContext,
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
}
