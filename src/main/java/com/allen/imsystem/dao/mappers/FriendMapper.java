package com.allen.imsystem.dao.mappers;

import com.allen.imsystem.model.dto.FriendApplicationDTO;
import com.allen.imsystem.model.dto.FriendGroup;
import com.allen.imsystem.model.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendMapper {

    List<String> selectFriendId(String uid);

    List<String> selectAquiredId(String uid);

    Integer  selectApplyGroupId(@Param("fromUid") String fromUid, @Param("toUid") String toUid);

    List<FriendApplicationDTO> selectLatestApply(@Param("uid") String uid,@Param("limit") Integer limit);

    List<UserInfoDTO> selectFriendList(@Param("uid")String uid);

    UserInfoDTO selectFriendInfo(@Param("friendId")String friendId);

    Integer checkIsFriend(@Param("uid")String uid, @Param("friendId")String friendId);

    Integer addFriendApply(@Param("fromUid") String fromUid,@Param("toUid")String toUid
            ,@Param("groupId")Integer groupId,@Param("reason")String reason);

    Integer updateFriendApplyPass(@Param("pass") boolean pass,@Param("fromUid") String fromUid,@Param("toUid") String toUid);

    Integer insertNewFriend(@Param("uidA") String aUid,@Param("uidB") String bUid,
                            @Param("abGroupId") Integer abGroupId,@Param("baGroupId") Integer baGroupId);

    List<FriendGroup> selectFriendGroupListWithSize(@Param("uid")String uid);

    Integer insertNewFriendGroup(@Param("uid")String uid, @Param("groupName")String groupName);

    Integer deleteFriend(@Param("uid")String uid, @Param("friendId")String friendId);
}
