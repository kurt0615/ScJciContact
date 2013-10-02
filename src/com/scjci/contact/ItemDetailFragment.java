package com.scjci.contact;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
            userInfo = new DummyContent(getActivity()).getUserInfo(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail,
                container, false);

        if (userInfo != null) {
            getActivity().getActionBar().setTitle(userInfo.getName());

            ImageView avatar = (ImageView) rootView.findViewById(R.id.avatar);
            if(StringUtils.isNotBlank(userInfo.getAvatarPath())){
                avatar.setImageBitmap(BitmapFactory.decodeFile(userInfo.getAvatarPath()));
            }else {
                avatar.setImageDrawable(getResources().getDrawable(R.drawable.nopicture));
            }

            if(StringUtils.isNotBlank(userInfo.getName())){
                TextView name = (TextView) rootView.findViewById(R.id.name);
                name.setText(userInfo.getName());
            }

            if(StringUtils.isNotBlank(userInfo.getCouple())){
                TextView couple = (TextView) rootView.findViewById(R.id.couple);
                couple.setText(userInfo.getCouple());
            }

            if(StringUtils.isNotBlank(userInfo.getJob())){
                TextView job = (TextView) rootView.findViewById(R.id.job);
                job.setText(userInfo.getJob());
            }

            if(StringUtils.isNotBlank(userInfo.getPhoneInfo().getMobilePhone())){
                final TextView mobilephone; mobilephone = (TextView) rootView.findViewById(R.id.mobilephone);
                mobilephone.setText(userInfo.getPhoneInfo().getMobilePhone());

                final LinearLayout mobilephonecall = (LinearLayout) rootView.findViewById(R.id.mobilephonecall);
                mobilephonecall.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                mobilephonecall.setBackgroundResource(android.R.color.holo_blue_bright);
                                break;
                            case MotionEvent.ACTION_UP:
                                mobilephonecall.setBackgroundResource(android.R.color.transparent);
                                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + mobilephone.getText().toString())));
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                mobilephonecall.setBackgroundResource(android.R.color.transparent);
                                break;
                        }
                        return true;
                    }
                });
            }

            if(StringUtils.isNotBlank(userInfo.getPhoneInfo().getTelephone())){
                final TextView telephone = (TextView) rootView.findViewById(R.id.telephone);
                telephone.setText(userInfo.getPhoneInfo().getTelephone());

                final LinearLayout telephonecall = (LinearLayout) rootView.findViewById(R.id.telephonecall);
                telephonecall.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                telephonecall.setBackgroundResource(android.R.color.holo_blue_bright);
                                break;
                            case MotionEvent.ACTION_UP:
                                telephonecall.setBackgroundResource(android.R.color.transparent);
                                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + telephone.getText().toString())));
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                telephonecall.setBackgroundResource(android.R.color.transparent);
                                break;
                        }
                        return true;
                    }
                });
            }

            if(StringUtils.isNotBlank(userInfo.getPhoneInfo().getFax())){
                TextView fax = (TextView) rootView.findViewById(R.id.fax);
                fax.setText(userInfo.getPhoneInfo().getFax());
            }

            if(StringUtils.isNotBlank(userInfo.getAddrInfo().getHomeAddr())){
                TextView homeaddr = (TextView) rootView.findViewById(R.id.homeaddr);
                homeaddr.setText(userInfo.getAddrInfo().getHomeAddr());
            }

            if(StringUtils.isNotBlank(userInfo.getAddrInfo().getContactAddr())){
                TextView contactaddr = (TextView) rootView.findViewById(R.id.contactaddr);
                contactaddr.setText(userInfo.getAddrInfo().getContactAddr());
            }

            if(StringUtils.isNotBlank(userInfo.getAddrInfo().getWorkAddr())){
                TextView workaddr = (TextView) rootView.findViewById(R.id.workaddr);
                workaddr.setText(userInfo.getAddrInfo().getWorkAddr());
            }

            if(StringUtils.isNotBlank(userInfo.getDateInfo().getBirthday())){
                TextView birthday = (TextView) rootView.findViewById(R.id.birthday);
                birthday.setText(userInfo.getDateInfo().getBirthday());
            }

            if(StringUtils.isNotBlank(userInfo.getDateInfo().getJoinDate())){
                TextView joindate = (TextView) rootView.findViewById(R.id.joindate);
                joindate.setText(userInfo.getDateInfo().getJoinDate());
            }
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
