package com.allen.imsystem.service;

import com.allen.imsystem.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 好友查询相关业务逻辑的接口
 */
public interface IFriendQueryService {

    /**
     * 搜索陌生人
     * @param uid
     * @param keyword
     * @return
     */
    List<UserSearchResult> searchStranger(String uid, String keyword);

    /**
     * 检查对方是否是自己的好友
     * @param uid
     * @param friendId
     * @return
     */
    Boolean checkIsMyFriend(String uid, String friendId);

    Boolean checkIsTwoWayFriend(String uid, String friendId);

    /**
     * 检查对方是否已经把自己删除
     * @param uid
     * @param friendId
     * @return
     */
    Boolean checkIsDeletedByFriend(String uid, String friendId);

    /**
     * 按分组获取好友列表
     */
     Map<String,Object> getFriendListByGroup(String uid);

    /**
     * 获取好友列表
     */
    Set<UserInfoDTO> getFriendList(String uid);

    List<FriendInfoForInvite> getFriendListForInvite(String uid, String gid);

    /**
     * 获取好友信息
     */
    UserInfoDTO getFriendInfo(String uid, String friendId);

}