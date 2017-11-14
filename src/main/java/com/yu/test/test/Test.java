package com.yu.test.test;

import com.mongodb.*;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by koreyoshi on 2017/11/3.
 */
@Component
public class Test {
    @org.junit.Test
    public void test() {
        int i = 10;
        int i1 = 1000009;
        System.out.println(i1 % 10);
    }

    @org.junit.Test
    public void MongoTest() throws UnknownHostException {

        String dbName = "yu";
        String tableName = "test_yu";
        MongoClient mongoClient = null;
        ServerAddress ssAddress = new ServerAddress("127.0.0.1", 40000);
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).
                threadsAllowedToBlockForConnectionMultiplier(50).build();

        mongoClient = new MongoClient(ssAddress, options);

        DB mongoDatabase = mongoClient.getDB(dbName);
        DBCollection collection = mongoDatabase.getCollection(tableName);
        List list = new ArrayList();
        list.add("yu");
        list.add("yu1");
        list.add("yu2");
        list.add("yu3");
        Map testData = new HashMap();
        testData.put("test", list);


        DBObject dbObject = new BasicDBObject(testData);
        WriteResult res = collection.insert(dbObject);
        System.out.println(res);
        int i = 2;
//        MongoOperations

//        try{
//            // 连接到 mongodb 服务
//            Mongo mongo = new Mongo("127.0.0.1", 40000);
//            //根据mongodb数据库的名称获取mongodb对象 ,
//            DB db = mongo.getDB( "yu" );
//            Set<String> collectionNames = db.getCollectionNames();
//            // 打印出test中的集合
//            for (String name : collectionNames) {
//                System.out.println("collectionName==="+name);
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }

    }

    /**
     * 中国标准时间转化成时间戳
     *
     * @throws ParseException
     */
    @org.junit.Test
    public void testTime() throws ParseException {
        String dt = "Thu May 28 18:23:17 CST 2015";

        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = sdf2.format(sdf1.parse(dt));
        System.out.println(dateTimeString);
        Long time = sdf2.parse(dateTimeString).getTime();
        System.out.println(time);
    }

    @org.junit.Test
    public void test1(){
        String docText = "<a href=\"http://app.weibo.com/t/feed/3zgJda\" rel=\"nofollow\">前后2000万 OPPO R11s</a>";
        String repDocText = docText.replaceAll("<a.*?>|</a>", "");
        System.out.println(docText);
        System.out.println(repDocText);
    }

    @org.junit.Test
    public void test2(){
        String key = "storedata.dsname.cache04";
        int start = key.indexOf(".", 1);
        System.out.println(start);
    }

    public void test4(){
        System.out.println("in test4");
    }



}
