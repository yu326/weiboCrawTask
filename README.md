# weiboCrawTask（微博抓取任务）
## 1.简介

主要是调用微博api接口,拿到微博数据入库到指定mongodb中.有不同的任务类型,现阶段只用到了抓取指定任务的接口.还有其他的几种（关键词，转发，评论）.

## 2.开发环境

- springMvc 4.3.6
- mysql 5.7.18

## 3.设计说明  

因为业务需要对抓取的数据进行分类处理（也就是针对不同的项目,有不同的column,column1）

主要是基于v2的task表参数来设计的新的task表.从task表中拿到待启动的微博抓取任务.新增project_id字段,关联project表,project中则存储了,该项目所对应的column,column1,以及他要入的mongo表名配置字段(cache_name).

1,对应otaApi中的cache01,依次类推到4.若此项为空,则默认入库到cache03所对应的mongodb表中。

启动基于两种，一种是springmvc的crond，另一种是接口调用。

抓取微博的那块，是集成的微博的sdk代码。抓取下来的数据有一个方法对那进行需要字段统一字段处理。

存储mongodb的过程，是调用otaApi来实现的。配置在mongoService.properties中。里面有默认的cache_name为cache03

## 4.接口说明

对外提供 /Weibo-api/crawData 接口，来手动启动抓取任务

#### 接口格式说明

支持的格式

​	json

http请求方式

​	post

请求参数

​        无

- 结果

  > success
  > {"result":true}
  >
  > false
  >
  > {"result":false}

## 5.todo

1.抓取关键词接口开发

2.手动调用处,区分这几种类型.

