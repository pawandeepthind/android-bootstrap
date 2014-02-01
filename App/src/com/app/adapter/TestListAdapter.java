package com.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.R;
import com.app.model.itunes.Item;

public class TestListAdapter extends BaseAdapter {
	ArrayList<Item> users;
	Context ctx;

	static class ViewHolder {
		TextView name;
		TextView desc;
		TextView age;
	}

	public TestListAdapter(Context ctx, ArrayList<Item> p) {
		this.ctx = ctx;
		this.users = p;
	}

	@Override
	public int getCount() {
		return users.size();
	}

	@Override
	public Object getItem(int position) {
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout userView = (LinearLayout) convertView;
		final ViewHolder holder;
		final Item i = (Item) getItem(position);

		if (convertView == null) {
			holder = new ViewHolder();
			userView = new LinearLayout(ctx);
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.cell, userView, true);
			holder.name = (TextView) userView.findViewById(R.id.name);
			holder.desc = (TextView) userView.findViewById(R.id.description);
			holder.age = (TextView) userView.findViewById(R.id.age);
			userView.setTag(holder);
		} else {
			holder = (ViewHolder) userView.getTag();
		}

		holder.name.setText(i.getArtistName());
		holder.desc.setText(i.getTrackName());
		holder.age.setText(i.getDiscNumber());

		return userView;
	}
}
