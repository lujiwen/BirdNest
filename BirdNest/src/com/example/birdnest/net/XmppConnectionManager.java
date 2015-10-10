package com.example.birdnest.net;
//package scu.android.util;
//
//
//
//import org.jivesoftware.smack.Connection;
//import org.jivesoftware.smack.ConnectionConfiguration;
//import org.jivesoftware.smack.Roster;
//import org.jivesoftware.smack.XMPPConnection;
//import org.jivesoftware.smack.provider.ProviderManager;
//import org.jivesoftware.smackx.GroupChatInvitation;
//import org.jivesoftware.smackx.PrivateDataManager;
//import org.jivesoftware.smackx.packet.ChatStateExtension;
//import org.jivesoftware.smackx.packet.LastActivity;
//import org.jivesoftware.smackx.packet.OfflineMessageInfo;
//import org.jivesoftware.smackx.packet.OfflineMessageRequest;
//import org.jivesoftware.smackx.packet.SharedGroupsInfo;
//import org.jivesoftware.smackx.provider.DataFormProvider;
//import org.jivesoftware.smackx.provider.DelayInformationProvider;
//import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
//import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
//import org.jivesoftware.smackx.provider.MUCAdminProvider;
//import org.jivesoftware.smackx.provider.MUCOwnerProvider;
//import org.jivesoftware.smackx.provider.MUCUserProvider;
//import org.jivesoftware.smackx.provider.MessageEventProvider;
//import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
//import org.jivesoftware.smackx.provider.RosterExchangeProvider;
//import org.jivesoftware.smackx.provider.StreamInitiationProvider;
//import org.jivesoftware.smackx.provider.VCardProvider;
//import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;
//import org.jivesoftware.smackx.search.UserSearch;
//
//import scu.android.application.MyApplication;
//
//
//
///**
// * 
// * XMPP服务器连接工具类.
// * 
// * @author shimiso
// */
//public class XmppConnectionManager {
//	private XMPPConnection connection;
//	private static ConnectionConfiguration connectionConfig;
//	private static XmppConnectionManager xmppConnectionManager;
//
//	private XmppConnectionManager() {
//
//	}
//
//	public static XmppConnectionManager getInstance() {
//		if (xmppConnectionManager == null) {
//			xmppConnectionManager = new XmppConnectionManager();
//		}
//		return xmppConnectionManager;
//	}
//
//	// init
//	public XMPPConnection init() {
//		Connection.DEBUG_ENABLED = false;
//		ProviderManager pm = ProviderManager.getInstance();
//		configure(pm);
//
//		System.out.println("MyApplication.hostIp:"+MyApplication.hostIp+"----MyApplication.hostName: "+MyApplication.hostName);
//		connectionConfig = new ConnectionConfiguration(
//				MyApplication.hostIp, 5222,
//				MyApplication.hostName);
//		connectionConfig.setSASLAuthenticationEnabled(false);// 不使用SASL验证，设置为false
//		connectionConfig
//				.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
//		// 允许自动连接
//		connectionConfig.setReconnectionAllowed(false);
//		// 允许登陆成功后更新在线状�?
//		connectionConfig.setSendPresence(true);
//		// 收到好友�?请后manual表示�?要经过同�?,accept_all表示不经同意自动为好�?
//		Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
//		connection = new XMPPConnection(connectionConfig);
//		return connection;
//	}
//
//	/**
//	 * 
//	 * 返回�?个有效的xmpp连接,如果无效则返回空.
//	 * 
//	 * @return
//	 * @author shimiso
//	 * @update 2012-7-4 下午6:54:31
//	 */
//	public XMPPConnection getConnection() {
//		if (connection == null) {
//			throw new RuntimeException("请先初始化XMPPConnection连接");
//		}
//		return connection;
//	}
//
//	/**
//	 * 
//	 * �?毁xmpp连接.
//	 * 
//	 * @author shimiso
//	 * @update 2012-7-4 下午6:55:03
//	 */
//	public void disconnect() {
//		if (connection != null) {
//			connection.disconnect();
//		}
//	}
//
//	public void configure(ProviderManager pm) {
//
//		// Private Data Storage
//		pm.addIQProvider("query", "jabber:iq:private",
//				new PrivateDataManager.PrivateDataIQProvider());
//
//		// Time
//		try {
//			pm.addIQProvider("query", "jabber:iq:time",
//					Class.forName("org.jivesoftware.smackx.packet.Time"));
//		} catch (ClassNotFoundException e) {
//		}
//
//		// XHTML
//		pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im",
//				new XHTMLExtensionProvider());
//
//		// Roster Exchange
//		pm.addExtensionProvider("x", "jabber:x:roster",
//				new RosterExchangeProvider());
//		// Message Events
//		pm.addExtensionProvider("x", "jabber:x:event",
//				new MessageEventProvider());
//		// Chat State
//		pm.addExtensionProvider("active",
//				"http://jabber.org/protocol/chatstates",
//				new ChatStateExtension.Provider());
//		pm.addExtensionProvider("composing",
//				"http://jabber.org/protocol/chatstates",
//				new ChatStateExtension.Provider());
//		pm.addExtensionProvider("paused",
//				"http://jabber.org/protocol/chatstates",
//				new ChatStateExtension.Provider());
//		pm.addExtensionProvider("inactive",
//				"http://jabber.org/protocol/chatstates",
//				new ChatStateExtension.Provider());
//		pm.addExtensionProvider("gone",
//				"http://jabber.org/protocol/chatstates",
//				new ChatStateExtension.Provider());
//
//		// FileTransfer
//		pm.addIQProvider("si", "http://jabber.org/protocol/si",
//				new StreamInitiationProvider());
//
//		// Group Chat Invitations
//		pm.addExtensionProvider("x", "jabber:x:conference",
//				new GroupChatInvitation.Provider());
//		// Service Discovery # Items
//		pm.addIQProvider("query", "http://jabber.org/protocol/disco#items",
//				new DiscoverItemsProvider());
//		// Service Discovery # Info
//		pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",
//				new DiscoverInfoProvider());
//		// Data Forms
//		pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());
//		// MUC User
//		pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user",
//				new MUCUserProvider());
//		// MUC Admin
//		pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin",
//				new MUCAdminProvider());
//		// MUC Owner
//		pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner",
//				new MUCOwnerProvider());
//		// Delayed Delivery
//		pm.addExtensionProvider("x", "jabber:x:delay",
//				new DelayInformationProvider());
//		// Version
//		try {
//			pm.addIQProvider("query", "jabber:iq:version",
//					Class.forName("org.jivesoftware.smackx.packet.Version"));
//		} catch (ClassNotFoundException e) {
//		}
//		// VCard
//		pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());
//		// Offline Message Requests
//		pm.addIQProvider("offline", "http://jabber.org/protocol/offline",
//				new OfflineMessageRequest.Provider());
//		// Offline Message Indicator
//		pm.addExtensionProvider("offline",
//				"http://jabber.org/protocol/offline",
//				new OfflineMessageInfo.Provider());
//		// Last Activity
//		pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());
//		// User Search
//		pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());
//		// SharedGroupsInfo
//		pm.addIQProvider("sharedgroup",
//				"http://www.jivesoftware.org/protocol/sharedgroup",
//				new SharedGroupsInfo.Provider());
//		// JEP-33: Extended Stanza Addressing
//		pm.addExtensionProvider("addresses",
//				"http://jabber.org/protocol/address",
//				new MultipleAddressesProvider());
//
//	}
//}
//
