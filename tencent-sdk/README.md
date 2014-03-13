简介
-----
腾讯微博SDK。


使用方式
-----
添加mvn依赖

```
<dependency>
  <groupId>cc.pp.dataserver</groupId>
  <artifactId>tencent-sdk</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

如果需要修改api服务器地址（比如使用api-proxy），则在classpath中增加配置文件 `tencent-sdk-config.properties`，并根据实际情况具体配置

```
api_v1_base_url=http://open.t.qq.com/api
api_v2_base_url=https://open.t.qq.com/api
```
