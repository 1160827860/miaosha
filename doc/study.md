# 对返回的数据进行包装
例如CodeMsg和Result就是对返回数据进行包装：
- CodeMsg代表的是错误代码
- Result是返回格式，出现错误会将CodeMsg对象的值传入Result中，再发送
# @Transactional
开启事务管理功能，如果这个方法部分执行成功，就回滚，例如向一个数据库插入主键一样的记录，之前的操作必然回滚。
# 使用redis连接池
使用原生的连接操作：get、set，需要自己实现包括：get、set、stringToBean、beanToString。即对象转字符串，字符串转对象。
# 循环依赖
例如RedisService文件中，有以下这个文件，但是之前的自动注入，需要这个bean对象，在类加载阶段，注入需要已经加载的bean方法，发现没有加载就去加载这个bean方法所在的文件，所有就出现了循环依赖
```java
    @Autowired
    JedisPool jedisPool;
    @Bean
    public JedisPool JedisPoolFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait());
        JedisPool jp = new JedisPool(poolConfig,redisConfig.getHost(),redisConfig.getPort(),redisConfig.getTimeout(),
            redisConfig.getPassword(),0);
        return jp;
    }
```
# Redis读取不到配置
java.util.NoSuchElementException: Timeout waiting for idle object
这个问题是因为config对象的属性和配置文件之中的属性名不一致导致的，新版本已经更新了配置文件可以使用@value来指定配置
# Redis多人开发，避免别人覆盖你的key，添加前缀
实现之后的具体格式是：UserKey:id1
给key前面加上前缀避免其他人覆盖
书写前缀用到了接口到抽象类到实现类的封装，接口定义了两个方法，抽象类里面定义了获取当前类的名称的方法（获得了UserKey）和prefix
的具体值，在具体实现类中封装了":"后面的类型是id还是name（获得id），key传入就是（1）。具体的拼接过程是在RedisService中get和set
方法中，将前缀和key拼接，UserKey:id的拼接是在BasePrefix，UserKey:id和1的拼接是在RedisService中。
# 明文密码两次MD5
两次MD5，
用户端：PASS = MD5（明文 + 固定Salt）
服务端：PASS = MD5(用户输入 + 随机Salt)
# JSR303参数检验 + 全局异常处理器
首先每次都要对用户传入的参数进行验证格式是否正确消耗时间，所以需要导入JSR303
使用方式如下：
给需要验证的对象加上@Valid，然后给属性加上@NotNull或者其他自定义的。
- 普通异常直接抛出，然后被异常拦截器拦截之后，回显给用户
- 绑定异常和上面一样
拦截绑定异常给用户显示。
# 分布式Session
为了兼容手机浏览器上传cookie需要使用@RequestParam
# WebMvcConfigurerAdapter 已经过期
使用WebMvcConfigurer接口
# 为什么要使用自定义springmvc的功能
因为例如传入一个user类型的对象，springmvc自动注入，不能检查是否user为空，所以自定义config和UserAraumentResolvers在注入的时候
检查是不是空。同时可以消除Controller层的重复代码。
# 商品ID
使用snowflake算法
# 压力测试：
没有优化过的商品展示页面QBS
2020/2/21 每秒1000线程循环10次， load：5 1749次/s
# 页面优化：
页面缓存 + URL 缓存 + 对象缓存
页面静态化，前后端分离
## 页面缓存：
将页面转为字符串存redis中，这样就可以直接从缓存中取页面，速度会提升很多,这种办法是为了防止某一个时间访问量过大，所以过期时间不
能太长
解决Sping5中SpringWebContext方法过时:
```java
IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());
```
## URL缓存：
和页面缓存一样，url缓存只是为了将详情页面中多个商品的页面加入缓存中
## 对象缓存：
例如token之中粒度最小，也被叫做对象缓存，getById，例如登陆的时候将数据库查询的对象存入redis中
```java
  public MiaoshaUser getById(String id){
        //将user的信息存入redis，实现对象缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById,id,MiaoshaUser.class);
        if(user != null){
            return user
        }
        user = miaoshaUserDao.getById(id);
        if (user != null){
            redisService.set(MiaoshaUserKey.getById,""+id,user);
        }
        return user;
    }
```
**更新数据库信息一定要更新缓存，这也是为什么每一个service最好引用自己的dao，需要用到别的dao时候，就直接引入service，不要直接
使用dao，这样会导致缓存不一致**
2020/2/21 1000个线程 循环 10 次是 load：5 QPS：4419次/s
为什么要先存数据库再让缓存失效，这是因为如果直接删除，用户做一个读操作，这样会将旧缓存再次出现，但是数据库已经是新的信息了。
查看5-4的博客
# 页面静态化
这种技术据我了解不使用页面静态化，例如使用thmeleaf这种，还是在服务器端渲染了数据，再发送给客户端，使用页面静态化就不发送渲染好
的页面，发送数据，然后让客户端进行渲染。
 相当于前后端分离，vue等框架的技术，直接将数据返回给客户端，跳转也要改成客户端跳转，不需要像后面这样：
 /goods/to_detail/'+${goods.id}
 # get post 区别
 GET幂等，从服务端获取数据，多次请求也不会改变服务器的数据
 POST向服务端提交数据，会改变服务端数据
 例如：
 <a href = "delete?id=1212">”搜索引擎遍历的时候会帮你执行这段代码，导致问题
 # 配置静态文件
 让浏览器从本地的静态文件中读取数据，不再进行请求，需要对配置文件写入一段配置
 
 

