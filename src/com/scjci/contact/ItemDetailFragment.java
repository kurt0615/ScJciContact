package com.scjci.contact;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import org.apache.commons.lang3.StringUtils;
import com.scjci.contact.dummy.DummyContent;
import com.scjci.contact.vo.UserInfo;

import java.util.HashMap;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "itemId";

    /**
     * The dummy content this fragment is presenting.
     */
    private HashMap<String, Object> mItem;
    private UserInfo userInfo;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = new DummyContent(getActivity()).getUserContact(getArguments().getString(ARG_ITEM_ID));
            userInfo = new DummyContent(getActivity()).getUserInfo(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail,
                container, false);
//avatar

        ImageView iv = (ImageView) rootView.findViewById(R.id.avatar);
        if(StringUtils.isNotBlank(userInfo.getAvatarPath())){
            //iv.setImageBitmap(BitmapFactory.decodeFile( mItem.get("avatarPath").toString()));
            iv.setImageBitmap(BitmapFactory.decodeFile(userInfo.getAvatarPath()));
            //iv.setImageDrawable((BitmapDrawable) mItem.get("avatarPath"));
        }else {
            iv.setImageDrawable(getResources().getDrawable(R.drawable.nopicture));
        }

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            //getActivity().getActionBar().setTitle(mItem.get("name").toString());
            getActivity().getActionBar().setTitle(userInfo.getName());
			/*((TextView) rootView.findViewById(R.id.item_detail))
					.setText(mItem.get("mobile").toString());
			
			((TextView) rootView.findViewById(R.id.item_detail2))
			.setText("hihi");*/
			

			
			/*TextView mobile = (TextView) localView.findViewById(R.id.mobile);
			String mobielText = mobile.getText().toString();
			final Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobielText));
			
			mobile.setTextColor(Color.BLUE); 
			mobile.setOnClickListener(new OnClickListener() {

			        @Override
			        public void onClick(View v) {
			            // TODO Auto-generated method stub
			            startActivity(callIntent);
			        }
			    });*/
        }

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               // getActivity().finish();
                Intent intent = new Intent();
                intent.setClass(this.getActivity(), ItemListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
