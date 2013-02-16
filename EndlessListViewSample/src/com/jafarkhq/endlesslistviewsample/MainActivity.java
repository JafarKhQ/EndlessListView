package com.jafarkhq.endlesslistviewsample;

import java.util.ArrayList;
import java.util.List;

import com.jafarkhq.views.EndlessListView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final int ID_MENU_REFRESH = 666;

	private MyAdapter adapter;
	private EndlessListView endlessListView;

	private int mCount;
	private boolean mHaveMoreDataToLoad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("Old item");
		}

		mCount = 0;
		mHaveMoreDataToLoad = true;
		adapter = new MyAdapter(MainActivity.this, list);
		endlessListView = (EndlessListView) findViewById(R.id.endlessListView);

		endlessListView.setAdapter(adapter);
		endlessListView.setOnLoadMoreListener(loadMoreListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, ID_MENU_REFRESH, 0, "Refresh");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (ID_MENU_REFRESH == item.getItemId()) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < 20; i++) {
				list.add("Old item");
			}

			mCount = 0;
			mHaveMoreDataToLoad = true;
			adapter = new MyAdapter(MainActivity.this, list);
			endlessListView.setAdapter(adapter);

			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadMoreData() {
		new LoadMore().execute((Void) null);
	}

	private EndlessListView.OnLoadMoreListener loadMoreListener = new EndlessListView.OnLoadMoreListener() {

		@Override
		public boolean onLoadMore() {
			if (true == mHaveMoreDataToLoad) {
				loadMoreData();
			} else {
				Toast.makeText(MainActivity.this, "No more data to load",
						Toast.LENGTH_SHORT).show();
			}

			return mHaveMoreDataToLoad;
		}
	};

	private class LoadMore extends AsyncTask<Void, Void, List<String>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mCount++;
		}

		@Override
		protected List<String> doInBackground(Void... params) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < 7; i++) {
				list.add("Round " + mCount);
			}

			try {
				Thread.sleep(2112);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return list;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			super.onPostExecute(result);

			adapter.addItems(result);
			endlessListView.loadMoreCompleat();
			mHaveMoreDataToLoad = mCount < 4;
		}
	}
}
