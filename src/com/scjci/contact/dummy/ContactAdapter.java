package com.scjci.contact.dummy;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.scjci.contact.R;

import java.lang.ref.WeakReference;

public class ContactAdapter extends SimpleCursorAdapter {
    private Context mContext;
    public ContactAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.getCursor() != null && this.getCursor().moveToPosition(position)) {
            final ViewHolder holder;
            if(convertView == null){
                convertView =  LayoutInflater.from(mContext).inflate(R.layout.fragment_item_list,null);
                holder = new ViewHolder();
                holder.avatar =  (ImageView) convertView.findViewById(R.id.avatar);
                holder.avatarPath =  (TextView) convertView.findViewById(R.id.avatarPath);
                holder.userId =  (TextView) convertView.findViewById(R.id.userId);
                holder.name =  (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(this.getCursor().getString(this.getCursor().getColumnIndex("NAME")));
            holder.userId.setText(this.getCursor().getString(this.getCursor().getColumnIndex("_id")));
            new ImageDownloaderTask(holder.avatar).execute(this.getCursor().getString(this.getCursor().getColumnIndex("AVATARPATH")));
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView avatarPath;
        TextView name;
        TextView userId;
        ImageView avatar;
    }

    private class ImageDownloaderTask extends AsyncTask<Object, Void, Bitmap> {
        private final WeakReference imageViewReference;
        public ImageDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference(imageView);
        }
        @Override
        protected Bitmap doInBackground(Object... params) {
            if(params[0] != null){
                return BitmapFactory.decodeFile(params[0].toString());
            }else{
                return null;
            }
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = (ImageView)imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageResource(R.drawable.nopicture);
                    }
                }

            }
        }
    }

    /*private class ContentFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = dataSource;
                results.count = dataSource.size();
            }
            else {
                List filterRet = new ArrayList<HashMap<String, Object>>();
                for (Map p : dataSource) {
                    if (p.get("name") != null &&
                       p.get("name").toString().toUpperCase().contains(constraint.toString().toUpperCase())){
                        filterRet.add(p);
                        Log.i( p.get("name").toString(),p.get("phone").toString());
                        continue;
                    }
                    if (p.get("phone") != null &&
                            p.get("phone").toString().contains(constraint.toString())){
                        filterRet.add(p);
                    }
                }
                results.values = filterRet;
                results.count = filterRet.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            dataSource = (Cursor) filterResults.values;
            notifyDataSetChanged();
        }
    }*/
}