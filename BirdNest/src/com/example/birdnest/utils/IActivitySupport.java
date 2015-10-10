package com.example.birdnest.utils;



import com.example.birdnest.application.MyApplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Activity帮助支持类接�?.
=======
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
//import csdn.shimiso.eim.model.LoginConfig;

/**
 * Activity����֧����ӿ�?.
>>>>>>> 添加附近的人和群组资源文�?
 * 
 * @author shimiso
 */
public interface IActivitySupport {
	/**
	 * 
<<<<<<< HEAD
	 * 获取EimApplication.
	 * 
	 * @author shimiso
	 * @update 2012-7-6 上午9:05:51
	 */
	public abstract MyApplication getEimApplication();

	/**
	 * 
	 * 终止服务.
	 * 
	 * @author shimiso
	 * @update 2012-7-6 上午9:05:51
=======
	 * ��ȡEimApplication.
	 * 
	 * @author shimiso
	 * @update 2012-7-6 ����9:05:51
	 */
//	public abstract EimApplication getEimApplication();

	/**
	 * 
	 * ��ֹ����.
	 * 
	 * @author shimiso
	 * @update 2012-7-6 ����9:05:51
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract void stopService();

	/**
	 * 
<<<<<<< HEAD
	 * �?启服�?.
	 * 
	 * @author shimiso
	 * @update 2012-7-6 上午9:05:44
=======
	 * ��������.
	 * 
	 * @author shimiso
	 * @update 2012-7-6 ����9:05:44
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract void startService();

	/**
	 * 
<<<<<<< HEAD
	 * 校验网络-如果没有网络就弹出设�?,并返回true.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 上午9:03:56
=======
	 * У������-���û������͵�������,������true.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 ����9:03:56
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract boolean validateInternet();

	/**
	 * 
<<<<<<< HEAD
	 * 校验网络-如果没有网络就返回true.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 上午9:05:15
=======
	 * У������-���û������ͷ���true.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 ����9:05:15
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract boolean hasInternetConnected();

	/**
	 * 
<<<<<<< HEAD
	 * �?出应�?.
	 * 
	 * @author shimiso
	 * @update 2012-7-6 上午9:06:42
=======
	 * �˳�Ӧ��.
	 * 
	 * @author shimiso
	 * @update 2012-7-6 ����9:06:42
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract void isExit();

	/**
	 * 
<<<<<<< HEAD
	 * 判断GPS是否已经�?�?.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 上午9:04:07
=======
	 * �ж�GPS�Ƿ��Ѿ�����.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 ����9:04:07
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract boolean hasLocationGPS();

	/**
	 * 
<<<<<<< HEAD
	 * 判断基站是否已经�?�?.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 上午9:07:34
=======
	 * �жϻ�վ�Ƿ��Ѿ�����.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 ����9:07:34
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract boolean hasLocationNetWork();

	/**
	 * 
<<<<<<< HEAD
	 * �?查内存卡.
	 * 
	 * @author shimiso
	 * @update 2012-7-6 上午9:07:51
=======
	 * ����ڴ�?.
	 * 
	 * @author shimiso
	 * @update 2012-7-6 ����9:07:51
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract void checkMemoryCard();

	/**
	 * 
<<<<<<< HEAD
	 * 显示toast.
	 * 
	 * @param text
	 *            内容
	 * @param longint
	 *            内容显示多长时间
	 * @author shimiso
	 * @update 2012-7-6 上午9:12:02
=======
	 * ��ʾtoast.
	 * 
	 * @param text
	 *            ����
	 * @param longint
	 *            ������ʾ�೤ʱ��
	 * @author shimiso
	 * @update 2012-7-6 ����9:12:02
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract void showToast(String text, int longint);

	/**
	 * 
<<<<<<< HEAD
	 * 短时间显示toast.
	 * 
	 * @param text
	 * @author shimiso
	 * @update 2012-7-6 上午9:12:46
=======
	 * ��ʱ����ʾtoast.
	 * 
	 * @param text
	 * @author shimiso
	 * @update 2012-7-6 ����9:12:46
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract void showToast(String text);

	/**
	 * 
<<<<<<< HEAD
	 * 获取进度�?.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 上午9:14:38
=======
	 * ��ȡ�����?.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 ����9:14:38
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract ProgressDialog getProgressDialog();

	/**
	 * 
<<<<<<< HEAD
	 * 返回当前Activity上下�?.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 上午9:19:54
=======
	 * ���ص�ǰActivity������.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 ����9:19:54
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public abstract Context getContext();

	/**
	 * 
<<<<<<< HEAD
	 * 获取当前登录用户的SharedPreferences配置.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 上午9:23:02
	 */
	public SharedPreferences getLoginUserSharedPre();

//	/**
//	 * 
//	 * 保存用户配置.
//	 * 
//	 * @param loginConfig
//	 * @author shimiso
//	 * @update 2012-7-6 上午9:58:31
//	 */
//	public void saveLoginConfig(LoginConfig loginConfig);
//
//	/**
//	 * 
//	 * 获取用户配置.
//	 * 
//	 * @param loginConfig
//	 * @author shimiso
//	 * @update 2012-7-6 上午9:59:49
//	 */

	/* * ��ȡ��ǰ��¼�û���SharedPreferences����.
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-6 ����9:23:02
	 */


	/**
	 * 
	 * �����û�����.
	 * 
	 * @param loginConfig
	 * @author shimiso
	 * @update 2012-7-6 ����9:58:31
	 */
//	public void saveLoginConfig(LoginConfig loginConfig);

	/**
	 * 
	 * ��ȡ�û�����.
	 * 
	 * @param loginConfig
	 * @author shimiso
	 * @update 2012-7-6 ����9:59:49
	 */

//	public LoginConfig getLoginConfig();

	/**
	 * 
<<<<<<< HEAD
	 * 用户是否在线（当前网络是否重连成功）
	 * 
	 * @param loginConfig
	 * @author shimiso
	 * @update 2012-7-6 上午9:59:49
=======
	 * �û��Ƿ����ߣ���ǰ�����Ƿ������ɹ���
	 * 
	 * @param loginConfig
	 * @author shimiso
	 * @update 2012-7-6 ����9:59:49
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public boolean getUserOnlineState();

	/**
<<<<<<< HEAD
	 * 设置用户在线状�?? true 在线 false 不在�?
=======
	 * �����û�����״̬ true ���� false ������
>>>>>>> 添加附近的人和群组资源文�?
	 * 
	 * @param isOnline
	 */
	public void setUserOnlineState(boolean isOnline);

	/**
	 * 
<<<<<<< HEAD
	 * 发出Notification的method.
	 * 
	 * @param iconId
	 *            图标
	 * @param contentTitle
	 *            标题
	 * @param contentText
	 *            你内�?
	 * @param activity
	 * @author shimiso
	 * @update 2012-5-14 下午12:01:55
=======
	 * ����Notification��method.
	 * 
	 * @param iconId
	 *            ͼ��
	 * @param contentTitle
	 *            ����
	 * @param contentText
	 *            ������
	 * @param activity
	 * @author shimiso
	 * @update 2012-5-14 ����12:01:55
>>>>>>> 添加附近的人和群组资源文�?
	 */
	public void setNotiType(int iconId, String contentTitle,
			String contentText, Class activity, String from);
}
