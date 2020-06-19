package com.allen.imsystem.message.service.pipeline;

import com.allen.imsystem.chat.service.ChatService;
import com.allen.imsystem.chat.service.PrivateChatService;
import com.allen.imsystem.common.Const.GlobalConst;
import com.allen.imsystem.common.cache.impl.ChatCache;
import com.allen.imsystem.common.utils.FormatUtil;
import com.allen.imsystem.chat.mappers.PrivateChatMapper;
import com.allen.imsystem.chat.model.vo.ChatSession;
import com.allen.imsystem.message.model.vo.OneToOneMsgPushPacket;
import com.allen.imsystem.message.model.vo.OneToOneMsgSendPacket;
import com.allen.imsystem.message.model.vo.PushMessageDTO;
import com.allen.imsystem.message.model.vo.SendMsgDTO;
import com.allen.imsystem.chat.model.pojo.PrivateChat;
import com.allen.imsystem.common.redis.RedisService;
import com.allen.imsystem.message.service.impl.MessageCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Scope("prototype")
public class PriMsgTalkDataHandler extends PrivateMsgHandler {

    @Autowired
    private PrivateChatService privateChatService;

    @Autowired
    private PrivateChatMapper privateChatMapper;

    @Autowired
    private MessageCounter messageCounter;

    @Override
    public void handleMsg(OneToOneMsgSendPacket<String, SendMsgDTO> msgSendPacket, OneToOneMsgPushPacket<String, PushMessageDTO> msgPushPacket) {
        SendMsgDTO sendMessage = msgSendPacket.getSendMessage();
        PushMessageDTO pushMessage = msgPushPacket.getPushMessage();
        Long chatId = pushMessage.getChatId();
        // 2、判断是否是新会话（收到信息的用户，该用户的会话是否处于有效状态)
        boolean isNewTalk = !privateChatService.isOpen(sendMessage.getDestId(), chatId);
        pushMessage.setIsNewTalk(isNewTalk);
        if(isNewTalk){
            privateChatService.open(sendMessage.getDestId(), sendMessage.getSrcId());
        }
        // 3、填充会话信息
        ChatSession talkData = privateChatMapper.selectNewMsgPrivateChatData(chatId, sendMessage.getDestId(), sendMessage.getSrcId());
        talkData.setLastMessageTime(FormatUtil.formatChatSessionDate(talkData.getLastMessageDate()));
        talkData.setNewMessageCount(messageCounter.getPrivateChatNewMsgCount(sendMessage.getDestId(), chatId));
        pushMessage.setTalkData(talkData);
        // 4、是否显示时间 消息发送时间 - 会话上一条消息发送时间 > 固定时间 即显示时间
        long chatLastMsgTime = talkData.getLastMessageDate().getTime();
        long msgSendTimestamp = Long.parseLong(sendMessage.getTimeStamp());
        boolean showTime = msgSendTimestamp - chatLastMsgTime > GlobalConst.MAX_NOT_SHOW_TIME_SPACE;
        pushMessage.getMessageData().setShowMessageTime(showTime);
        pushMessage.setLastTimeStamp(chatLastMsgTime);
    }

}
