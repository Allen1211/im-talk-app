package com.allen.imsystem.common.Const;

public class GlobalConst {


    public static final String ENCODING = "UTF-8";

    public static final Long MAX_NOT_SHOW_TIME_SPACE = 1000*60*5L;

    public static final String DEFAULT_FRIEND_GROUP_NAME = "我的好友";

    public static final Long BLOCK_SIZE = 2 * 1024 * 1024L; // 2MB

    public static final Integer NETTY_PORT = 9898;

    public static final String DEFAULT_GROUP_NAME = "新群聊";

    /**
     * websocket事件码
     */
    public static class WsEvent{
        public static final int CLIENT_SEND_MSG = 101;              // 客户端发送信息
        public static final int CLIENT_MSG_ACK = 102;               // 客户端收到信息确认回执
        public static final int CLIENT_NEW_APPLY_NOTIFY_ACK = 104;  // 客户端收到新好友申请通知确认回执
        public static final int CLIENT_NEW_FRIEND_NOTIFY_ACK = 105; // 客户端收到申请被通过获得新好友的通知确认回执

        public static final int SERVER_PUSH_MSG = 201;
        public static final int SERVER_MSG_ACK_SUCCESS = 202;
        public static final int SERVER_MSG_ACK_FAIL = 203;
        public static final int SERVER_PUSH_NEW_APPLY_NOTIFY = 204;
        public static final int SERVER_PUSH_NEW_FRIEND_NOTIFY = 205;

    }

    /**
     * 路径类
     */
    public static class Path{
        public static final String TALK_WEBSOCKET_URL = "ws://121.199.10.4:9999/imsystem/talk";
        public static final String API_SERVER_IP = "121.199.10.4";
        public static final String RESOURCES_URL = "http://121.199.10.4:8088/imsystem/static/";
        public static final String AVATAR_URL = "http://121.199.10.4:8088/imsystem/static/avatar/";
        public static final String MSG_IMG_URL = "http://121.199.10.4:8088/imsystem/static/msg_img/";
        public static final String MSG_FILE_URL = "http://121.199.10.4:8088/imsystem/static/msg_file/";
        public static final String AVATAR_PATH = "/usr/resources/imsystem/static/avatar/";
        public static final String MSG_IMG_PATH = "/usr/resources/imsystem/static/msg_img/";
//        public static final String MSG_IMG_PATH = "E:/";
        public static final String MSG_FILE_PATH = "/usr/resources/imsystem/static/msg_file/";
//        public static final String MSG_FILE_PATH = "E:/";
        public static final String MSG_RECORD_PATH = "/usr/resources/imsystem/static/msg_record/";

        public static final String FILE_ICON_URL = "http://121.199.10.4:8088/imsystem/static/msg_file/file_icon.png";

        public static final String IMG_NOT_FOUND = "http://121.199.10.4:8088/imsystem/static/msg_img/img_not_found.jpg";

        public static final String DEFAULT_GROUP_AVATAR = "default/default_group_avatar.jpg";
    }

    /**
     *  用户在线状态
     */
    public static class UserStatus{
        public static final Integer OFFLINE = 0;   // 离线
        public static final Integer ONLINE = 1;    // 在线
        public static final Integer AFK = 2;       // 离开
        public static final Integer HIDE = 3;      // 隐身
    }

    /**
     * 正则表达式
     */
    public static class RegExp {

        public static final String EMAIL = "^[\\w]+@[a-zA-z\\d]+\\.[a-zA-z0-9]+$";
        public static final String PASSWORD = "^*$";
    }

    public static class MQ {
        public static final String EMAIL_MESSAGE_KEY = "EMAIL_MESSAGE_QUEUE";
    }

    /**
     * redis相关的常量
     */
    public static class Redis{
        public static final String KEY_USER_STATUS = "user_status";
        public static final String KEY_CHAT_TYPE = "chat_type";
        public static final String KEY_CHAT_REMOVE = "chat_remove";
        public static final String KEY_RECORD_BEGIN_ID = "msg_record_begin_msg_id";
        public static final String KEY_CHAT_UNREAD_COUNT = "user_chat_unread_msg_count";
        public static final String KEY_CHAT_LAST_MSG_TIME = "chat_last_msg_time";
        public static final String KEY_FRIEND_SET = "user_friend_set_";
        public static final String KET_GROUP_CHAT_MEMBERS = "group_chat_members_";
        public static final String KEY_EMAIL_CODE = "email_code_";
        public static final String KEY_IMAGE_CODE = "image_code_";
        public static final String KEY_CHAT_GID_MAP = "chat_gid_map_";
        public static final String KEY_TOKEN_BLACKLIST = "token_black_list";

    }

    public static class ChatType{
        public static final Integer UN_KNOW = -1;
        public static final Integer PRIVATE_CHAT = 0;
        public static final Integer GROUP_CHAT = 1;
    }

    public static class NotifyType{
        public static final Integer NEW_FRIEND_NOTIFY  = 1;
        public static final Integer NEW_APPLY_NOTIFY  = 2;
    }

    public static class MsgType{
        public static final int TEXT = 1;
        public static final int IMAGE = 2;
        public static final int FILE = 3;
        public static final int GROUP_NOTIFY = 4;
    }

}
