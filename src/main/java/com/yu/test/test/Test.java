package com.yu.test.test;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by koreyoshi on 2017/11/3.
 */
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
        testData.put("test",list);



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
}
