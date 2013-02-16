package com.jafarkhq.endlesslistviewsample;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private List<String> mList;
	private LayoutInflater mInflater;

	public MyAdapter(Context context, List<String> list) {
		mList = list;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void addItems(List<String> newItems) {
		if (null == newItems || newItems.size() <= 0) {
			return;
		}

		if (null == mList) {
			mList = new ArrayList<String>();
		}

		mList.addAll(newItems);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (null == mList) {
			return 0;
		}

		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = null;
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.list_item, null);

			textView = (TextView) convertView.findViewById(R.id.textView);

			convertView.setTag(textView);
		} else {
			textView = (TextView) convertView.getTag();
		}

		final String text = (String) getItem(position);
		textView.setText(text);

		return convertView;
	}

}
