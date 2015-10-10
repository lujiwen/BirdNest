package com.example.birdnest.actionbar;

import com.example.birdnest.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class SlidingTabIndicator extends GridView implements android.widget.AdapterView.OnItemClickListener {

	/**
	 * Get resource id in position
	 */
	public interface TabResProvider 
	{
		public TabItem getTabResId(int position);
	}

	private ViewPager pager;
	private Paint tabPaint;
	private Paint divderPaint;

	private int tabCount;// number of pages
	private int selectedPosition = 0;// select page index

	//private int tabColor = 0xFFFFFFFF;
	private int tabColor = 0x00000000;
	private int divderlineColor = 0x2F000000;
	private int tabTextColor = 0xFF666666;
	private int selectedTabTextColor = 0xFF33CC33;

	private int divderlineHeight = 1;
	public OnPageChangeListener delegatePageListener;

	public SlidingTabIndicator(Context context) {
		super(context);
		init();
	}

	public SlidingTabIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SlidingTabIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void init() {
		tabPaint = new Paint();
		tabPaint.setAntiAlias(true);
		tabPaint.setStyle(Style.FILL);
		divderPaint = new Paint();
		divderPaint.setAntiAlias(true);
		divderPaint.setStyle(Style.FILL);
	}

	/**
	 * set reference ViewPager
	 * 
	 * @param pager
	 */
	public void setPager(ViewPager pager) 
	{
		this.pager = pager;
		if (pager.getAdapter() == null) {
			throw new IllegalStateException("ViewPager does not have adapter instance.");
		}
		AlphaAnimation animation = new AlphaAnimation(0, 255);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		pager.setAnimation(animation);
		pager.setOnPageChangeListener(pageListener);
		this.tabCount = pager.getAdapter().getCount();
		this.setAdapter(adapter);
		this.setOnItemClickListener(this);
	}

	public void setDelegatePageListener(OnPageChangeListener listener) {
		this.delegatePageListener = listener;
	}

	/**
	 * page change listener
	 */
	private OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			adapter.notifyDataSetChanged();
			if (delegatePageListener != null) {
				delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				// scrollToChild(pager.getCurrentItem(), 0);
			}

			if (delegatePageListener != null) {
				delegatePageListener.onPageScrollStateChanged(state);
			}
		}

		@Override
		public void onPageSelected(int position) {
			selectedPosition = position;
			adapter.notifyDataSetChanged();
			if (delegatePageListener != null) {
				delegatePageListener.onPageSelected(position);
			}
		}
	};

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		tabPaint.setColor(tabColor);
		final int height = getHeight();
		final int width = getWidth();
		canvas.drawRect(0, 0, width, height, tabPaint);
		divderPaint.setColor(divderlineColor);
		canvas.drawRect(0, 0, width, divderlineHeight, divderPaint);
	}

	static class TabItemHolder {
		ImageView tabIcon;
		TextView tabTitle;
	}

	public static class TabItem {
		int iconResId;
		int titleResId;

		public TabItem(int iconResId, int titleResId) {
			super();
			this.iconResId = iconResId;
			this.titleResId = titleResId;
		}
	}

	private BaseAdapter adapter = new BaseAdapter() {

		@Override
		public int getCount() {
			return tabCount;
		}

		@Override
		public Object getItem(int position) {
			return ((TabResProvider) pager.getAdapter()).getTabResId(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TabItemHolder holder = new TabItemHolder();
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.sliding_tab_item, null, false);
				holder.tabIcon = (ImageView) convertView.findViewById(R.id.tab_icon);
				holder.tabTitle = (TextView) convertView.findViewById(R.id.tab_title);
				convertView.setTag(holder);
			} else {
				holder = (TabItemHolder) convertView.getTag();
			}
			final TabItem item = (TabItem) getItem(position);
			holder.tabIcon.setBackgroundResource(item.iconResId);
			holder.tabTitle.setText(item.titleResId);
			if (position != selectedPosition) {
				holder.tabIcon.setSelected(false);
				holder.tabTitle.setTextColor(tabTextColor);
			} else {
				holder.tabIcon.setSelected(true);
				holder.tabTitle.setTextColor(selectedTabTextColor);
			}
			return convertView;
		}
	};

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {
		pager.setCurrentItem(postion,false);
		selectedPosition = postion;
		adapter.notifyDataSetChanged();
	}
}
