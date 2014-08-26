package com.creativei.viewpagerloop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private static final String LOG_TAG = "ViewPagerLoop";

	public static final String[] content = new String[] {
			"Hello Welcome to ViewPager Loop Example. This is first view. Swipe right → for second view, swipe left ← for last view.",
			"Awesome, now you are on second view. Swipe right → again to go to last view.",
			"Finally made it to last view, swipe right → again to go to first view." };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		TextView counter = (TextView) findViewById(R.id.counter);
		SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(
				getSupportFragmentManager(), pager, content, counter);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(adapter);
		pager.setCurrentItem(1, false);
	}

	public static class SimpleFragment extends Fragment {
		public SimpleFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			String content = getArguments().getString("content");
			TextView textView = (TextView) rootView.findViewById(R.id.content);
			textView.setText(content);
			return rootView;
		}
	}

	public static class SimpleViewPagerAdapter extends
			FragmentStatePagerAdapter implements OnPageChangeListener {

		private String[] content;
		private ViewPager pager;
		private TextView counter;

		public SimpleViewPagerAdapter(FragmentManager fm, ViewPager pager,
				String[] content, TextView counter) {
			super(fm);
			this.pager = pager;
			this.content = content;
			this.counter = counter;
		}

		@Override
		public Fragment getItem(int position) {
			SimpleFragment fragment = new SimpleFragment();
			Bundle bundle = new Bundle();
			int index = position - 1;
			if (position == 0) {
				index = content.length - 1;
			} else if (position == content.length + 1) {
				index = 0;
			}
			Log.d(LOG_TAG, "For page at position " + position
					+ ",fetching item at index " + index);
			bundle.putString("content", content[index]);
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return content.length + 2;
		}

		@Override
		public void onPageSelected(int position) {
			if (position == 0) {
				pager.setCurrentItem(content.length, false);
				counter.setText(makeCounterText(content.length));
				Log.d(LOG_TAG,
						"Swiped before first page, looping and resetting to last page.");
			} else if (position == content.length + 1) {
				pager.setCurrentItem(1, false);
				counter.setText(makeCounterText(1));
				Log.d(LOG_TAG,
						"Swiped beyond last page, looping and resetting to first page.");
			} else {
				counter.setText(makeCounterText(position));
			}
		}

		private String makeCounterText(int pageNo) {
			return "Page " + pageNo + " of " + content.length;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

	}
}
