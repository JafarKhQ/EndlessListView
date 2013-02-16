package com.jafarkhq.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

public class EndlessListView extends ListView {
	@SuppressWarnings("unused")
	private final static String TAG = "EndlessListView";

	public interface OnLoadMoreListener {
		public boolean onLoadMore();
	}

	private boolean mIsLoading;
	private ProgressBar progressBar;
	private OnLoadMoreListener onLoadMoreListener;

	public EndlessListView(Context context) {
		super(context);
		init();
	}

	public EndlessListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public EndlessListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.onLoadMoreListener = onLoadMoreListener;
	}

	public void loadMoreCompleat() {
		mIsLoading = false;
		progressBar.setVisibility(View.GONE);
	}

	private void init() {
		mIsLoading = false;

		progressBar = new ProgressBar(getContext(), null,
				android.R.attr.progressBarStyleSmall);
		LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		progressBar.setLayoutParams(progressBarParams);
		progressBar.setPadding(6, 6, 6, 6);
		progressBar.setVisibility(View.GONE);

		LinearLayout footerLinearLayout = new LinearLayout(getContext());
		LayoutParams layoutParams = new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		footerLinearLayout.setGravity(Gravity.CENTER);
		footerLinearLayout.setLayoutParams(layoutParams);
		footerLinearLayout.addView(progressBar);

		addFooterView(footerLinearLayout);

		super.setOnScrollListener(new ELScrollChangedListener());
	}

	private class ELScrollChangedListener implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

			boolean loadMore;
			loadMore = (0 != totalItemCount)
					&& ((firstVisibleItem + visibleItemCount) >= (totalItemCount));

			if (false == mIsLoading && true == loadMore) {

				if (null != onLoadMoreListener) {
					if (onLoadMoreListener.onLoadMore()) {
						mIsLoading = true;
						progressBar.setVisibility(View.VISIBLE);
					}
				}
			}
		}
	}

}
