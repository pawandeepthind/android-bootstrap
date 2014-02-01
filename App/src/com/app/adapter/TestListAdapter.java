package com.app.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.R;
import com.app.helper.AppHelper;
import com.app.model.itunes.Item;
import com.app.util.Constants;
import com.app.util.Utils;
import com.app.views.FadeInNetworkImageView;

public class TestListAdapter extends BaseAdapter {
	private List<Item> items;
	private Context ctx;
	private LayoutInflater inflater;

	static class ViewHolder {
		FadeInNetworkImageView artworkUrl60;
		TextView trackName;
		TextView artistName;
		TextView collectionName;
		TextView duration;

		public ViewHolder(LinearLayout cellView) {
			artworkUrl60 = (FadeInNetworkImageView) cellView
					.findViewById(R.id.artworkUrl60);
			trackName = (TextView) cellView.findViewById(R.id.trackName);
			artistName = (TextView) cellView.findViewById(R.id.artistName);
			collectionName = (TextView) cellView
					.findViewById(R.id.collectionName);
			duration = (TextView) cellView.findViewById(R.id.duration);
		}
	}

	public void setItems(List<Item> itms) {
		this.items = itms;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public TestListAdapter(Context ctx) {
		this.ctx = ctx;
		this.inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Item getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout cellView = (LinearLayout) convertView;
		final ViewHolder holder;
		final Item i = getItem(position);

		if (convertView == null) {
			cellView = new LinearLayout(this.ctx);
			this.inflater.inflate(R.layout.cell, cellView, true);
			holder = new ViewHolder(cellView);
			cellView.setTag(holder);
		} else {
			holder = (ViewHolder) cellView.getTag();
		}

		holder.trackName.setText(i.getTrackName());
		holder.collectionName.setText(i.getCollectionName());
		holder.artistName.setText(i.getArtistName());
		if (i.getTrackTimeMillis() != null)
			holder.duration.setText(Utils.convertToDuration(i
					.getTrackTimeMillis()));
		else
			holder.duration.setText(Constants.EMPTY_STRING);
		if (!TextUtils.isEmpty(i.getArtworkUrl60()))
			holder.artworkUrl60.setImageUrl(i.getArtworkUrl60(),
					AppHelper.getImageLoader());

		return cellView;
	}
}
