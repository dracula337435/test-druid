# 试验数据库操作

## 主要目的

1. 试验druid，加上filter
1. 测试中的事务

## 踩坑记录

1. mysql连接报错
```
TACKTRACE:
 
javax.net.ssl.SSLException: closing inbound before receiving peer's close_notify
    at java.base/sun.security.ssl.Alert.createSSLException(Alert.java:129)
    at java.base/sun.security.ssl.Alert.createSSLException(Alert.java:117)
    at java.base/sun.security.ssl.TransportContext.fatal(TransportContext.java:308)
    at java.base/sun.security.ssl.TransportContext.fatal(TransportContext.java:264)
    at java.base/sun.security.ssl.TransportContext.fatal(TransportContext.java:255)
    at java.base/sun.security.ssl.SSLSocketImpl.shutdownInput(SSLSocketImpl.java:645)
    at java.base/sun.security.ssl.SSLSocketImpl.shutdownInput(SSLSocketImpl.java:624)
    at com.mysql.cj.protocol.a.NativeProtocol.quit(NativeProtocol.java:1312)
    at com.mysql.cj.NativeSession.quit(NativeSession.java:182)
    at com.mysql.cj.jdbc.ConnectionImpl.realClose(ConnectionImpl.java:1750)
    at com.mysql.cj.jdbc.ConnectionImpl.close(ConnectionImpl.java:720)
    at com.zaxxer.hikari.pool.PoolBase.quietlyCloseConnection(PoolBase.java:135)
    at com.zaxxer.hikari.pool.HikariPool.lambda$closeConnection$1(HikariPool.java:441)
    at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
    at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
    at java.base/java.lang.Thread.run(Thread.java:834)
 
 
** END NESTED EXCEPTION **
```
解决方法为在url上加上
```
useSSL=false
```

1. 报错
```
Table '（数据库名）.hibernate_sequence' doesn't exist
```
给实体的id字段加注解
```
@GeneratedValue(strategy = GenerationType.IDENTITY)
```
另外在数据库相应的字段上
```
AUTO_INCREMENT PRIMARY KEY
```
如果没在数据库上没有主键和自增，则会报错
```
The database returned no natively generated identity value;
```

1. 测试方法上可用```javax```的```@Transactional```，也可用```spring```的，方法执行后自动回滚
```
INFO 4875 --- [           main] o.s.t.c.transaction.TransactionContext   : Rolled back transaction for test: （略）
```

1. 碰到```Public Key Retrieval is not allowed```在url后加上```allowPublicKeyRetrieval=true```

1. ```DruidDataSourceWrapper```用```@Autowired```的方式加载了各```Filter```  
各```Filter```在```DruidFilterConfiguration```中配置。  
利用这两点，```MyFilter extends ConfigFilter```且不实例化```ConfigFilter```，在其中换自己的逻辑。  
这样一来，```MyFilter```可使用```spring```基础设施