package com.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.R;
import com.app.util.Logger;

/**
 * The Class PageControl.
 */
public class PageControl extends LinearLayout {

	/** The number of pages. */
	private int numberOfPages = 1;

	/** The current page. */
	private int currentPage = 0;

	/** The selected res id. */
	private int selectedResId;

	/** The un selected res id. */
	private int unSelectedResId;

	/** The indicator gap. */
	private int indicatorGap = 2;

	/** The hide when single. */
	private boolean hideWhenSingle = true;

	private static String TAG = PageControl.class.getSimpleName();

	/**
	 * Instantiates a new page control.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public PageControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		Logger.i(TAG, "Constructing PageControl object");

		setStyleAttributes(attrs);
	}

	/**
	 * Sets the style attributes.
	 * 
	 * @param attrs
	 *            the new style attributes
	 */
	private void setStyleAttributes(AttributeSet attrs) {
		Logger.i(TAG, "Constructing PageControl default style attributes");
		TypedArray styledAttrs = getContext().obtainStyledAttributes(attrs,
				R.styleable.PageControl);
		selectedResId = styledAttrs.getResourceId(
				R.styleable.PageControl_selectedDrawable, 0);
		unSelectedResId = styledAttrs.getResourceId(
				R.styleable.PageControl_unSelectedDrawable, 0);
		indicatorGap = styledAttrs.getDimensionPixelSize(
				R.styleable.PageControl_indicatorGap, 2);
		styledAttrs.recycle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onFinishInflate()
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		refresh();
	}

	/**
	 * Gets the number of pages.
	 * 
	 * @return the number of pages
	 */
	public int getNumberOfPages() {
		return numberOfPages;
	}

	/**
	 * Sets the number of pages.
	 * 
	 * @param numberOfPages
	 *            the new number of pages
	 */
	public void setNumberOfPages(int numberOfPages) {
		Logger.i(
				TAG,
				"setNumberOfPages() with number: "
						+ Integer.toString(numberOfPages));
		this.numberOfPages = Math.max(numberOfPages, 0);
		currentPage = 0;

		if (!isInEditMode()) {
			refresh();
		} 
	}

	/**
	 * Refresh.
	 */
	private void refresh() {
		Logger.i(TAG, "Entering refresh()");
		removeAllViews();
		if (numberOfPages > 1) {
			for (int i = 0; i < this.numberOfPages; i++) {
				ImageView iv = new ImageView(getContext());
				if (getOrientation() == LinearLayout.HORIZONTAL) {
					iv.setPadding((int) indicatorGap / 2, 0,
							(int) indicatorGap / 2, 0);
				} else {
					iv.setPadding(0, (int) indicatorGap / 2, 0,
							(int) indicatorGap / 2);
				}
				if (i == this.currentPage) {
					iv.setImageResource(selectedResId);
				} else {
					iv.setImageResource(unSelectedResId);
				}
				this.addView(iv);
			}
		}
		Logger.i(TAG, "Exiting refresh()");
	}

	/**
	 * Gets the current page.
	 * 
	 * @return the current page
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * Sets the current page.
	 * 
	 * @param currentPage
	 *            the new current page
	 */
	public void setCurrentPage(int currentPage) {
		Logger.i(TAG,
				"setCurrentPage() with page: " + Integer.toString(currentPage));
		if (currentPage >= numberOfPages)
			this.currentPage = 0;
		else
			this.currentPage = Math.max(0, currentPage);

		refresh();
	}

	/**
	 * Checks if is hide when single.
	 * 
	 * @return true, if is hide when single
	 */
	public boolean isHideWhenSingle() {
		return hideWhenSingle;
	}

	/**
	 * Sets the hide when single.
	 * 
	 * @param hideWhenSingle
	 *            the new hide when single
	 */
	public void setHideWhenSingle(boolean hideWhenSingle) {
		this.hideWhenSingle = hideWhenSingle;
	}
}
