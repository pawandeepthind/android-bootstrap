package com.app.util;

import android.net.Uri;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

import com.app.activities.base.ActivityInterface;

public class URLSpanWithoutUnderline extends URLSpan {
	ActivityInterface bActivity;

	public URLSpanWithoutUnderline(String url, ActivityInterface act) {
		super(url);
		this.bActivity = act;
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		super.updateDrawState(ds);
		ds.setUnderlineText(false);
	}

	@Override
	public void onClick(View widget) {

		Uri uri = Uri.parse(getURL());
		String id = uri.getQueryParameter("id");

		if (uri.getQueryParameter("type").equalsIgnoreCase("hashtagfeed")) {
			id = uri.getQueryParameter("hashtag");
		}

		bActivity.onLinkClicked(uri.getQueryParameter("type"), id);
	}
}