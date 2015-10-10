package com.example.birdnest.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.graphics.Bitmap;

import com.example.birdnest.R;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.utils.ActivitySupport;
import com.example.birdnest.utils.Utility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BrowsePhotosActivity extends  ActivitySupport
{

	private GridView photosView;
	private PhotosAdapter adapter;
	private HashMap<String, ArrayList<String>> photos;// 手机图库(包含父目录)
	private ArrayList<Photo> bitmaps;// 图库
	private ArrayList<String> selectedPhotos;
	private Intent intent;
	private int width;
	private int availNumber;
	// /////////////////////////////////////////////////////////
	private View parentsView;
	private ArrayList<Parent> items;
	private TextView selectParent;
	private TextView selectNumber;
	protected ImageLoader loader;
	protected DisplayImageOptions options;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	    Log.e("鲁继文","浏览图片文件的oncreate!");
		setContentView(R.layout.activity_native_photos);
		MyApplication.getInstance().addActivity(this);
		setTitle("手机图库");
		setActionRight("选择");
		initControls();
	}
	

	// 初始化
	public void initControls() {
		intent = getIntent();
		availNumber = intent.getIntExtra("availNumber", 1);
		width = Utility.getDefaultPhotoWidth(this, 3);
		
		loader = ImageLoader.getInstance();
	   // loader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.app_logo).
				bitmapConfig(Bitmap.Config.ALPHA_8).build();
		photos = getPhotos();
		if (photos.size() > 0) {
			bitmaps = new ArrayList<Photo>();
			selectedPhotos = new ArrayList<String>();
			items = new ArrayList<BrowsePhotosActivity.Parent>();

			@SuppressWarnings("rawtypes")
			Iterator iterator = photos.entrySet().iterator();
			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Entry entry = (Entry) iterator.next();
				String parent = (String) entry.getKey();
				@SuppressWarnings("unchecked")
				ArrayList<String> paths = (ArrayList<String>) entry.getValue();
				for (String path : paths) {
					bitmaps.add(new Photo(path, false));
				}
				if (paths.size() > 0) {
					String photo = paths.get(0);
					int number = paths.size();
					Parent item = new Parent(photo, parent, number);
					items.add(item);
				}
			}
			if (bitmaps.size() > 0) {
				items.add(0, new Parent(bitmaps.get(0).path, "所有图片", bitmaps.size()));// 所有图片
				photosView = (GridView) findViewById(R.id.photosView);
				adapter = new PhotosAdapter(this, bitmaps);
				photosView.setAdapter(adapter);
				selectNumber = (TextView) findViewById(R.id.select_number);
				selectNumber.setText("已选" + selectedPhotos.size() + "张");
				selectParent = (TextView) findViewById(R.id.select_parent);
				configMenu();
			} else {
				Toast.makeText(this, "没有发现任何图片哦...", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "没有发现任何图片哦...", Toast.LENGTH_SHORT).show();
		}
	}

	// 获取手机图库
	public HashMap<String, ArrayList<String>> getPhotos() {
		HashMap<String, ArrayList<String>> photos = new HashMap<String, ArrayList<String>>();
		Uri photosUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		ContentResolver resolver = getContentResolver();
		// 只查询jpeg和png的图片
		Cursor cursor = resolver.query(photosUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[] { "image/jpeg", "image/png" },
				MediaStore.Images.Media.DATE_MODIFIED);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				// 获取图片的路径
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
				File file = new File(path);
				String parent = file.getParentFile().getName();
				if (photos.containsKey(parent)) {
					if (file.isFile() && file.canRead() && file.length() > 1024) {
						photos.get(parent).add(path);
					}
				} else {
					ArrayList<String> subPhotos = new ArrayList<String>();
					photos.put(parent, subPhotos);
				}

			}
		}
		return photos;
	}

	static class PhotoHolder {
		ImageView thumbnail;
	}

	private class PhotosAdapter extends BaseAdapter {
		private Activity activity;
		private ArrayList<Photo> bitmaps;

		public PhotosAdapter(Activity activity, ArrayList<Photo> bitmaps) {
			this.activity = activity;
			this.bitmaps = bitmaps;
		}

		public int getCount() {
			return bitmaps.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			PhotoHolder holder = new PhotoHolder();
			final Photo photo = bitmaps.get(position);
			final String imageUri = "file:///" + photo.path;
			if (convertView == null) {
				convertView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.native_photo_item, null);
				holder.thumbnail = ((ImageView) convertView.findViewById(R.id.thumbnail));
				holder.thumbnail.setTag(imageUri);
				convertView.setTag(holder);
			} else {
				holder = (PhotoHolder) convertView.getTag();
			}
			Utility.disImg(loader, options, holder.thumbnail, imageUri, width);
			final ImageView back = ((ImageView) convertView.findViewById(R.id.back));
			Utility.setViewSize(back, width, width);// 设置和后面的图片一样大小
			back.setVisibility(photo.selected ? View.VISIBLE : View.INVISIBLE);
			final CheckBox select = (CheckBox) convertView.findViewById(R.id.select);
			select.setChecked(photo.selected);
			final int index = position;
			holder.thumbnail.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (selectedPhotos.size() < availNumber || bitmaps.get(index).selected) {
						select.setChecked(!bitmaps.get(index).selected);
						boolean status = select.isChecked();
						bitmaps.get(index).selected = status;
						if (status) {
							back.setVisibility(View.VISIBLE);
							selectedPhotos.add(photo.path);
						} else {
							back.setVisibility(View.INVISIBLE);
							selectedPhotos.remove(photo.path);
						}
					} else {
						if (bitmaps.get(index).selected) {
							bitmaps.get(index).selected = false;
							back.setVisibility(View.INVISIBLE);
							selectedPhotos.remove(photo.path);
						} else {
							select.setEnabled(false);
							Toast.makeText(BrowsePhotosActivity.this, "最多只能选择" + availNumber + "张图片", Toast.LENGTH_SHORT).show();
						}
					}
					selectNumber.setText("已选" + selectedPhotos.size() + "张");
				}
			});
			return convertView;
		}
	}

	private class Photo {
		boolean selected;
		String path;

		public Photo(String path, boolean selected) {
			super();
			this.selected = selected;
			this.path = path;
		}

	}

	// 获取选择的图片
	public ArrayList<String> getSelectedPhotos() {
		return selectedPhotos;
	}

	@Override
	protected void onStop() {
		if (loader != null)
			loader.stop();
		super.onStop();
	}

	public void OnClick(View view) {
		switch (view.getId()) {
		case R.id.action_bar_right:
			if (getSelectedPhotos().size() > 0) {
				intent.putStringArrayListExtra("photos", getSelectedPhotos());
				setResult(Activity.RESULT_OK, intent);
				BrowsePhotosActivity.this.finish();
				Log.i("Complete", "selectComplete");
			} else {
				showToast("未选择图片");
			}
			break;
		case R.id.action_back:
			finish();
			break;
		case R.id.select_parent:
			select(view);
			break;
		}
	}

	private class ParentsAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<Parent> items;

		public ParentsAdapter(Context context, ArrayList<Parent> items) {
			this.context = context;
			this.items = items;
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.native_photos_parent_item, null);
			}
			Parent item = items.get(position);
			ImageView photo = (ImageView) convertView.findViewById(R.id.photo);
			android.view.ViewGroup.LayoutParams params = photo.getLayoutParams();
			params.width = params.height = width / 2;
			photo.setLayoutParams(params);
			loader.displayImage("file:///" + item.photo, photo, options);
			((TextView) convertView.findViewById(R.id.parent)).setText(item.parent);
			((TextView) convertView.findViewById(R.id.number)).setText(item.number + "张");
			return convertView;
		}
	}

	// 父目录
	private class Parent {
		String photo;// 封面
		String parent;// 目录名
		int number;// 图片数目

		public Parent(String photo, String parent, int number) {

			this.photo = photo;
			this.parent = parent;
			this.number = number;
		}

	}

	// 选择目录监听器
	private class SelectListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			parentsView.setVisibility(View.INVISIBLE);
			Parent item = items.get(position);
			selectParent.setText(item.parent);
			bitmaps.clear();
			if (position == 0) {
				@SuppressWarnings("rawtypes")
				Iterator iterator = photos.entrySet().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("rawtypes")
					Entry entry = (Entry) iterator.next();
					@SuppressWarnings("unchecked")
					ArrayList<String> paths = (ArrayList<String>) entry.getValue();
					for (String path : paths) {
						bitmaps.add(new Photo(path, selectedPhotos.contains(path)));
					}
				}
			} else {
				for (String photo : photos.get(item.parent)) {
					bitmaps.add(new Photo(photo, selectedPhotos.contains(photo)));
				}
			}
			adapter.notifyDataSetChanged();
		}
	}

	public void configMenu() {
		parentsView = findViewById(R.id.parents_lay);
		ListView parent = (ListView) findViewById(R.id.parents);
		parent.setAdapter(new ParentsAdapter(this, items));
		parent.setOnItemClickListener(new SelectListener());
		parentsView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
					if (parentsView.getVisibility() == View.VISIBLE) {
						parentsView.setVisibility(View.INVISIBLE);
					}
					return false;
			}
		});
	}

	// 选择目录
	public void select(View view) {
		if (parentsView.getVisibility() == View.INVISIBLE) {
			parentsView.setVisibility(View.VISIBLE);
		} else {
			parentsView.setVisibility(View.INVISIBLE);
		}
	}
}
