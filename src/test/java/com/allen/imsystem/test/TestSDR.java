package com.allen.imsystem.test;

import com.allen.imsystem.common.Const.GlobalConst;
import com.allen.imsystem.common.utils.RedisUtil;
import com.allen.imsystem.dao.mappers.ChatMapper;
import com.allen.imsystem.dao.mappers.GroupChatMapper;
import com.allen.imsystem.model.pojo.PrivateChat;
import com.allen.imsystem.model.pojo.User;
import com.allen.imsystem.model.pojo.UserChatGroup;
import com.allen.imsystem.service.IChatService;
import com.allen.imsystem.service.IFriendService;
import com.allen.imsystem.service.impl.ChatService;
import com.allen.imsystem.service.impl.RedisService;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/springmvc.xml","classpath*:spring/applicationContext.xml", "classpath*:spring/applicationContext-*.xml"})
public class TestSDR {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    IChatService chatService;

    @Autowired
    IFriendService friendService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    GroupChatMapper groupChatMapper;

    @Test
    public void testInject(){
        Assert.assertNotNull(redisTemplate);
        Assert.assertNotNull(stringRedisTemplate);
        Assert.assertNotNull(redisService);
        Assert.assertNotNull(jedisConnectionFactory);
    }
    @Test
    public void test1(){
        ListOperations<String,Object> listOperations = redisTemplate.opsForList();
        Assert.assertEquals("temp",listOperations.rightPop("temp"));
//        listOperations.leftPush("email_task","123");
    }
    @Test
    public void test2(){
        ListOperations<String,String> listOperationsForString = stringRedisTemplate.opsForList();
        listOperationsForString.leftPush("email_task","123");
        Assert.assertEquals("123",listOperationsForString.rightPopAndLeftPush("email_task","temp"));
        ListOperations<String,Object> listOperations = redisTemplate.opsForList();
        Assert.assertEquals("123",listOperationsForString.rightPop("temp"));
    }
    @Test
    public void test3(){
//        HashOperations<String,String,Object> hashOperations = redisTemplate.opsForHash();
//        User user = new User();
//        user.setId(1);
//        user.setUid("123");
//        hashOperations.put("user","allen",user);
//        User user1 = (User) hashOperations.get("user","allen");
//        Assert.assertNotNull(user1);
//        Assert.assertEquals("123",user1.getUid());
    }

    @Test
    public void test4(){
        System.out.println(redisUtil.hget(GlobalConst.Redis.KEY_USER_STATUS,"28661270"));
//        System.out.println(redisTemplate.opsForHash().get(GlobalConst.Redis.KEY_USER_STATUS,"28661270"));;
    }

    @Test
    public void testPubSub() throws ClassNotFoundException, InterruptedException {
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void testRedisService() throws InterruptedException {
        redisService.set("key1",1);
        redisService.set("key2",2,10);
        Assert.assertEquals(1,redisService.get("key1"));
        Assert.assertEquals(2,redisService.get("key2"));

        User user = new User();
        user.setUid("123");
        user.setCreatedTime(new Date());
        redisService.hset("hkey1","allen",user);
        User user1 = (User) redisService.hget("hkey1","allen");
        Assert.assertEquals("123",user1.getUid());
        System.out.println(user1);


        Object user2 = redisService.hget("hkey1","allen");
        System.out.println(user2);

    }

    @Test
    public void testRedisService2(){
        redisService.hset("hkey2","count1",1);
        redisService.hincr("hkey2","count1",1L);
//        redisService.hincr("hkey2","count1",1);
//        redisService.hincr("hkey2","count1",-1);
        Assert.assertEquals(2,redisService.hget("hkey2","count1"));
        redisService.del("hkey1","hkey2","key1","key2");
    }

    @Test
    public void testType() {
        Set<Object> fields =  redisTemplate.opsForHash().keys(GlobalConst.Redis.KEY_CHAT_UNREAD_COUNT);
        for(Object field : fields){
            System.out.println(field.toString());
            System.out.println(redisService.hget(GlobalConst.Redis.KEY_CHAT_UNREAD_COUNT, (String) field));
        }
    }

    @Test
    public void set1(){
        redisService.hset(GlobalConst.Redis.KEY_USER_STATUS,"28661270",1);
        redisService.hset(GlobalConst.Redis.KEY_USER_STATUS,"10547348",1);
        redisService.hset(GlobalConst.Redis.KEY_USER_STATUS,"81309655",1);
        redisService.hset(GlobalConst.Redis.KEY_USER_STATUS,"64920737",0);
        redisService.hset(GlobalConst.Redis.KEY_USER_STATUS,"97554417",1);
    }
    @Test
    public void set2(){
        redisService.hset(GlobalConst.Redis.KEY_CHAT_REMOVE,"10547348635251900281700352",true);
        redisService.hset(GlobalConst.Redis.KEY_CHAT_REMOVE,"28661270633786567424475138",true);
        redisService.hset(GlobalConst.Redis.KEY_CHAT_REMOVE,"66666666635069071375646720",true);
    }

    @Test
    public void incr(){
//        redisService.hincr(GlobalConst.Redis.KEY_CHAT_UNREAD_COUNT,"81309655"+"635245355485544448",-1L).intValue();
//        Integer num = chatService.incrUserChatNewMsgCount("81309655",635245355485544448L);
//        System.out.println(num);
    }

    @Test
    public void test(){

        redisService.lSet("1234",new User1("1","a"));
        redisService.lSet("1234",new User1("2","b"));
        redisService.lSet("1234",new User1("3","c"));
        List<Object> list = redisService.lGet("1234",0,-1);
        redisService.del("1234");
        System.out.println(list);
    }

    @Test
    public void fix(){
        List<UserChatGroup> list = groupChatMapper.fix();
        for(UserChatGroup u:list){
            redisService.hset(GlobalConst.Redis.KEY_CHAT_REMOVE,u.getUid()+u.getGid(),!u.getShouldDisplay());
        }
    }

    @Test
    public void fix1(){
        List<PrivateChat> privateChatList = chatMapper.fix1();
        for(PrivateChat privateChat:privateChatList){
            redisService.hset(GlobalConst.Redis.KEY_CHAT_REMOVE,
                    privateChat.getUidA()+privateChat.getChatId(),!privateChat.getUserAStatus());
            redisService.hset(GlobalConst.Redis.KEY_CHAT_REMOVE,
                    privateChat.getUidB()+privateChat.getChatId(),!privateChat.getUserBStatus());

        }
    }

    @Test
    public void t(){
        redisService.zSetAdd("1234","a",1);
        redisService.zSetAdd("1234","b",2);
        redisService.zSetAdd("1234","c",3);
        System.out.println(redisService.zSetHasMember("1234","d"));
        System.out.println(redisService.zRemoveRangeByScore("1234",3));
    }
}
@Data
class User1 implements Serializable {
    String id;
    String name;

    public User1() {
    }

    public User1(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o==null){
            return false;
        }
        if(! (o instanceof User1)){
            return false;
        }
        User1 obj = (User1)o;
        return this.id.equals(obj.id);
    }


}