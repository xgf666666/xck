package com.example.xck.common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.delegate.EaseCustomAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseExpressionAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseFileAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseImageAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseLocationAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseTextAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseVideoAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseVoiceAdapterDelegate;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.manager.EaseMessageTypeSetManager;
import com.hyphenate.easeui.provider.EaseEmojiconInfoProvider;
import com.hyphenate.easeui.provider.EaseSettingsProvider;
import com.hyphenate.easeui.provider.EaseUserProfileProvider;
import com.hyphenate.push.EMPushConfig;

import java.util.List;
import java.util.Map;

/**
 * author ： xiaogf
 * time    ： 2023/3/14
 * describe    ：
 */
public class HxHelper {
    private static HxHelper mInstance;
    public boolean isSDKInit;//SDK是否初始化
    private Context mainContext;
    public static HxHelper getInstance() {
        if(mInstance == null) {
            synchronized (HxHelper.class) {
                if(mInstance == null) {
                    mInstance = new HxHelper();
                }
            }
        }
        return mInstance;
    }
    public void init(Context context) {
        //初始化IM SDK
        if(initSDK(context)) {
            // debug mode, you'd better set it to false, if you want release your App officially.
            EMClient.getInstance().setDebugMode(false);
            // set Call options
//            setCallOptions(context);
            //初始化推送
//            initPush(context);
            //注册call Receiver
            //initReceiver(context);
            //初始化ease ui相关
//            initEaseUI(context);
            //注册对话类型
            registerConversationType();

            //callKit初始化
//            InitCallKit(context);

            //启动获取用户信息线程
           /* fetchUserInfoList = FetchUserInfoList.getInstance();
            fetchUserRunnable = new FetchUserRunnable();
            fetchUserTread = new Thread(fetchUserRunnable);
            fetchUserTread.start();*/
        }

    }

    /**
     * 初始化SDK
     * @param context
     * @return
     */
    private boolean initSDK(Context context) {
        // 根据项目需求对SDK进行配置
        EMOptions options = initChatOptions(context);
        // 初始化SDK
        isSDKInit = EaseIM.getInstance().init(context, options);
        mainContext = context;
        return isSDKInit();
    }
    /**
     * 设置SDK是否初始化
     * @param init
     */
    public void setSDKInit(boolean init) {
        isSDKInit = init;
    }

    public boolean isSDKInit() {
        return isSDKInit;
    }
    /**
     * 根据自己的需要进行配置
     * @param context
     * @return
     */
    private EMOptions initChatOptions(Context context){
        EMOptions options = new EMOptions();
        // 设置是否自动接受加好友邀请,默认是true
        options.setAcceptInvitationAlways(true);
        // 设置是否需要接受方已读确认
        options.setRequireAck(true);
        // 设置是否需要接受方送达确认,默认false
        options.setRequireDeliveryAck(false);
        //设置fpa开关，默认false
        options.setFpaEnable(true);
        // 开启本地消息流量统计
        options.setEnableStatistics(true);
        options.setAppKey("1198221025163768#demo");

        return options;
    }
    /**
     * ChatPresenter中添加了网络连接状态监听，多端登录监听，群组监听，联系人监听，聊天室监听
     * @param context
     */
/*
    private void initEaseUI(Context context) {
        EaseIM.getInstance().addChatPresenter(ChatPresenter.getInstance());
        EaseIM.getInstance()
                .setSettingsProvider(new EaseSettingsProvider() {
                    @Override
                    public boolean isMsgNotifyAllowed(EMMessage message) {
                        if(message == null){
                            return demoModel.getSettingMsgNotification();
                        }
                        if(!demoModel.getSettingMsgNotification()){
                            return false;
                        }else{
                            String chatUsename = null;
                            List<String> notNotifyIds = null;
                            // get user or group id which was blocked to show message notifications
                            if (message.getChatType() == EMMessage.ChatType.Chat) {
                                chatUsename = message.getFrom();
                                notNotifyIds = demoModel.getDisabledIds();
                            } else {
                                chatUsename = message.getTo();
                                notNotifyIds = demoModel.getDisabledGroups();
                            }

                            if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }

                    @Override
                    public boolean isMsgSoundAllowed(EMMessage message) {
                        return demoModel.getSettingMsgSound();
                    }

                    @Override
                    public boolean isMsgVibrateAllowed(EMMessage message) {
                        return demoModel.getSettingMsgVibrate();
                    }

                    @Override
                    public boolean isSpeakerOpened() {
                        return demoModel.getSettingMsgSpeaker();
                    }
                })
                .setEmojiconInfoProvider(new EaseEmojiconInfoProvider() {
                    @Override
                    public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
                        EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
                        for(EaseEmojicon emojicon : data.getEmojiconList()){
                            if(emojicon.getIdentityCode().equals(emojiconIdentityCode)){
                                return emojicon;
                            }
                        }
                        return null;
                    }

                    @Override
                    public Map<String, Object> getTextEmojiconMapping() {
                        return null;
                    }
                })
                .setAvatarOptions(getAvatarOptions())
                .setUserProvider(new EaseUserProfileProvider() {
                    @Override
                    public EaseUser getUser(String username) {
                        return getUserInfo(username);
                    }

                });

    }

    public EaseUser getUserInfo(String username) {
        // To get instance of EaseUser, here we get it from the user list in memory
        // You'd better cache it if you get it from your server
        EaseUser user = null;
        if(username.equals(EMClient.getInstance().getCurrentUser()))
            return getUserProfileManager().getCurrentUserInfo();
        user = getContactList().get(username);
        if(user == null){
            //找不到更新会话列表 继续查找
            updateContactList();
            user = getContactList().get(username);
            //如果还找不到从服务端异步拉取 然后通知UI刷新列表
            if(user == null){
                if(fetchUserInfoList != null){
                    fetchUserInfoList.addUserId(username);
                }
            }
        }
        return user;
    }
*/
    /**
     *注册对话类型
     */
    private void registerConversationType() {
        EaseMessageTypeSetManager.getInstance()
                .addMessageType(EaseExpressionAdapterDelegate.class)       //自定义表情
                .addMessageType(EaseFileAdapterDelegate.class)             //文件
                .addMessageType(EaseImageAdapterDelegate.class)            //图片
//                .addMessageType(EaseLocationAdapterDelegate.class)         //定位
//                .addMessageType(EaseVideoAdapterDelegate.class)            //视频
//                .addMessageType(EaseVoiceAdapterDelegate.class)            //声音
//                .addMessageType(ChatConferenceInviteAdapterDelegate.class) //语音邀请
//                .addMessageType(ChatRecallAdapterDelegate.class)           //消息撤回
//                .addMessageType(ChatVideoCallAdapterDelegate.class)        //视频通话
//                .addMessageType(ChatVoiceCallAdapterDelegate.class)        //语音通话
//                .addMessageType(ChatUserCardAdapterDelegate.class)         //名片消息
                .addMessageType(EaseCustomAdapterDelegate.class)           //自定义消息
//                .addMessageType(ChatNotificationAdapterDelegate.class)     //入群等通知消息
                .setDefaultMessageType(EaseTextAdapterDelegate.class);       //文本
    }
}
