# CommonToolkit



## 简介
CommonToolkit是一个公共基础工具包，封装了一些通用类和工具，提供了一些开发者能力，帮助您快速启动一个轻量、通用的java-web项目

<br>

## 导入

**SpringBoot项目导入：**
```
  引入普通工具包
  <dependency>
       <groupId>io.github.eternalstone</groupId>
       <artifactId>common-toolkit</artifactId>
       <version>${version}</version>
  </dependency>
   
  引入web项目的一些基础能力
  <dependency>
       <groupId>io.github.eternalstone</groupId>
       <artifactId>common-toolkit-web</artifactId>
       <version>${version}</version>
  </dependency>
```
<br>


## 用法
提供了统一返回对象、全局异常定义与捕获、常用工具类、常用算法等能力

   

### 基础用法

	#### 统一返回对象和通用模型

    WebApiRes：提供了一些快捷方法以直接返回数据
        return WebApiRes.success()
        return WebApiRes.failure()
        
    PageResult || PageList: 分页统一返回对象，用于mysql分页操作，若后续需要操作其他存储库，可扩展此类
    TablePage: 流式分页返回对象，用户下拉翻页的分页形式，不适用于跳页



#### 全局异常类和全局异常码

```
io.github.eternalstone.common.toolkit.exception：
    GlobalException：全局异常，自定义异常基类，所有自定义异常继承此类
    BizException：业务异常
io.github.eternalstone.common.toolkit.enums.GlobalErrorCode: 全局异常状态码
```



#### 业务断言工具类

```
io.github.eternalstone.common.toolkit.asserts.BizAssert: 方便实现业务的判断与异常抛错
```



#### 常用算法包

```
io.github.eternalstone.common.toolkit.security:
	aes:  AES算法加解密
	mac:  mac算法
	md5:  md5算法
	rsa:  rsa算法
	sha： sha256算法
```



#### 常用工具包

```
DateUtil: 时间操作工具
JsonUtil：json序列化
ObjectUtils： 对象操作
RegexUtil： 常用正则
SmsUtil： 短信赋值操作
SnowFlakeUtil： 雪花算法
VersionUtil：版本比对操作
...
```





### web层用法

#### 全局http返回json自定义格式

    @EnableCustomJsonConverter：
        注解到启动类或某个配置类上开启全局返回json的格式化，详情如下：
            1.List类型字段为null时输出[]而非null
            2.显示空字段
            3.字符串类型字段为null时间输出""而非null
            4.Boolean类型字段为null时输出false而null
            5.数值字段如果为null,输出为0,而非null
            6.时间格式yyyy-MM-dd HH: mm: ss



#### 全局跨域配置

```
@EnableGlobalCrossOrigin：  注解到启动类或某个配置类上开启全局跨域配置
	//允许请求源
    String[] origins() default {};
	//允许请求头
    String[] allowedHeaders() default {};
	//暴露请求头
    String[] exposedHeaders() default {};
	//允许请求方式
    String[] allowedMethods() default {};
```



#### 全局异常捕获

    @EnableGlobalExceptionHandler： 注解到启动类或某个配置类上开启全局异常捕获，返回统一错误码



#### 全局请求日志打印

```
@EnableRequestLog： 注解到启动类或某个配置类上, 并且需要在配置文件配置切点等信息

需要依赖Spring AOP
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
```

```yaml
controller:
  log:
    enabled: true
    level: FULL
    pointcut:
      expression: execution(* com.test.controller..*.*(..))
    warn:
      rt: 2000
```



#### 全局Xss防护

```
@EnableXssDefence： 注解到启动类或某个配置类上
	//是否全局trim文本
    boolean trim() default false;
    //xss清理模式
    XssMode mode() default XssMode.CLEAR;
	//[clear 专用] 是否保留换行
    boolean prettyPrint() default false;
	//[clear 专用] 使用转义，默认关闭
    boolean escape() default false;
	//拦截xss请求路径
    String[] includes() default { "/**" };
	//放行xss请求路径
    String[] excludes() default {};
```

