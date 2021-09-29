# Log4j2的简单使用



## 前言

简单介绍一下日志框架`log4j2`的使用, 主要通过`log4j2.xml`配置文件进行介绍

> Apache Log4j 2 is an upgrade to Log4j that provides significant improvements over its predecessor, Log4j 1.x, and provides many of the improvements available in Logback while fixing some inherent problems in Logback’s architecture.

包括日志级别,logger,appender(包括Console,File,RollingFile)等组件

> 官方文档: [Log4j – Apache Log4j 2](https://logging.apache.org/log4j/2.x/)

最后再来简单介绍一下SLF4j的使用



## 日志级别

![img](https://gitee.com/a-shy-coder/blog-images/raw/master/v2-7293bde8559e8496684637d18a29c813_720w.jpg)

`Log4j2`默认设置了8个日志级别, 级别从高到低, 日志信息也越来越简略

若日志级别为`DEBUG`, 则会打印`DEBUG`及其以下级别的日志信息



## 开始

- 引入依赖

  ```xml
  <!-- log4J2依赖, log4j-core依赖log4j-api，所以添加该依赖会传递引入log4j-api的依赖 -->
  <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-core</artifactId>
      <version>2.14.1</version>
  </dependency>
  ```

- 引入`log4j2.xml`

  > log4j2支持多种配置文件的格式.  并会按照一定顺序搜索配置文件
  
  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!--status 指定 Log4j2自身的日志级别, 通常OFF不记录-->
  <configuration status="OFF">
  
  
      <Properties>
          <!-- 可以指定磁盘或者项目路径, 从而将日志信息打印至相应位置-->
          <Property name="dir">C:\Users\12859\Desktop\logs\Logfile.log</Property>
          <Property name="srcDir">src/logs/appLog.log</Property>
          <Property name="targetDir">target/logs/appLog.log</Property>
          <Property name="baseDir">target/rolling_logs</Property>
      </Properties>
  
      <!--    Appenders 用于配置输出源-->
      <Appenders>
          <!-- Console, 即将控制台作为输出目的地-->
          <Console name="Console1" target="SYSTEM_OUT">
              <!-- PatternLayout用来格式化日志 -->
              <PatternLayout pattern="%d{yyyy/MM/dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
          </Console>
          <!--        SYSTEM_ERR表示在控制台的错误信息中打印-->
          <Console name="Console2" target="SYSTEM_ERR">
              <!-- PatternLayout用来格式化日志 -->
              <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
          </Console>
          <!-- File, 即将文件作为输出目的地, 日志一般为.log文件(也可以是其他,如html, txt)-->
          <File name="FileAppender1" fileName="${dir}">
              <!-- 格式化日志 -->
              <PatternLayout>
                  <pattern>%d %p %C{1.} [%t] %m%n</pattern>
              </PatternLayout>
          </File>
          <!--        RollingFile用于配置滚动文件的输出目的地, 可以实现日期文件的滚动备份-->
          <!--        fileName 指定最新的日志的文件的存储位置, 即appLog.log始终存放当前最新的日志-->
          <!--            filePattern 指定备份的日志文件的位置及名称格式-->
          <!--        $${date:yyyy-MM} 每月自动生成一个文件夹, 存放备份的日志(以压缩包形式存储,节省空间) -->
          <!--            文件夹里的日志按%d{MM-dd-yyyy}-%i格式命名  %d为日期  %i为序号-->
          <RollingFile name="RollingFileAppender1"
                       fileName="${baseDir}/appLog.log"
                       filePattern="${baseDir}/$${date:yyyy-MM}/C05_D02-%d{MM-dd-yyyy-HH-mm-ss}-%i.log.gz">
              <PatternLayout>
                  <!-- 格式化日志 -->
                  <Pattern>%d %p %c{1.} [%t] %m%n</Pattern>
              </PatternLayout>
  
  
              <!--            默认对同名的日志文件进行编号(默认到7, 超出则替换原文件),这里可以更改编号最大值-->
              <DefaultRolloverStrategy max="10">
                  <!-- 设置备份文件的删除策略, 同时满足-->
                  <Delete basePath="${baseDir}" maxDepth="2">
                      <IfFileName glob="*/C05_D02-*.log.gz" />
                      <!-- 备份文件最新修改时间距此超过15s -->
                      <IfLastModified age="15s" />
                  </Delete>
              </DefaultRolloverStrategy>
  
              <!-- 指定日志的备份策略, 即达到任一条件就对日志进行备份 -->
              <Policies>
                  <!-- 每隔一定的时间间隔就进行备份, 单位与日志文件的命名格式(即filePattern)(%d{MM-dd-yyyy})有关, 
    此表示每隔1天备份一次 -->
                  <TimeBasedTriggeringPolicy interval="1"
                                             modulate="true"/>
                  <!-- 日志文件达到size, 就进行备份 -->
                  <SizeBasedTriggeringPolicy size="20MB"/>
  
                  <!-- 也可以利用Cron表示式(类似于Linux中的Crontab)设置定时任务进行备份 -->
                  <CronTriggeringPolicy  schedule="0/10 * * * * ?"/>
  
              </Policies>
          </RollingFile>
      </Appenders>
  
      <!-- Loggers用于配置所有的日志记录器, 包括级别及输出位置-->
      <Loggers>
          <!-- 如果Logger没有设置级别和输出目的地, 则默认继承Root配置 -->
          <!-- 如果Logger没有设置级别和输出目的地, 且没有配置Root, 则默认在控制台打印, Error级别信息-->
          <Root level="DEBUG">
              <AppenderRef ref="Console1"/>
          </Root>
          <!-- 设置为DEBUG级别 -->
          <Logger name="com.shy.main.Test1" level="DEBUG" additivity="false">
              <!-- 为Logger指定响应的输出源, 可以配置多个, 如分别在控制台和文件中打印日志 -->
              <AppenderRef ref="Console1"/>
              <AppenderRef ref="FileAppender1"/>
          </Logger>
          <!-- 默认每一个Logger会在自己和Root的输出目的地各打印一次, additivity="false"则只在自己的输出目的地打印日志 -->
          <Logger name="com.shy.main.Test2" level="info" additivity="false">
              <AppenderRef ref="Console2"/>
          </Logger>
  
          <Logger name="com.shy.dao.StudentDao" additivity="false">
              <!-- StudentDao相关的日志打印到文件 -->
              <AppenderRef ref="FileAppender1"/>
          </Logger>
  
          <Logger name="com.shy.service.CourseService" level="trace" additivity="false">
              <AppenderRef ref="RollingFileAppender1"/>
          </Logger>
      </Loggers>
  </configuration>
  ```



## Log4j2配置文件



### Appenders

> Appenders用于配置输出源, 即日志信息的输出位置
>
> 输出源可配置多种, 包括Console(控制台), File(文件), RollingFile(滚动文件)等



#### Console

> 将日志信息打印到控制台

```xml
<Appenders>
    <!-- target 可取 SYSTEM_OUT, SYSTEM_ERR-->
    <Console name="Console1" target="SYSTEM_OUT">
        <!-- PatternLayout用来格式化日志 -->
        <PatternLayout pattern="%d{yyyy/MM/dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
    </Console>
</Appenders>
```

> SYSTEM_OUT 即调用 System.out.print() 打印日志信息
>
> ![image-20210929111312641](https://gitee.com/a-shy-coder/blog-images/raw/master/image-20210929111312641.png)
>
> SYSTEM_ERR  即调用 System.err.print() 打印日志信息
>
> ![image-20210929111253678](https://gitee.com/a-shy-coder/blog-images/raw/master/image-20210929111253678.png)
>
> PatternLayout 用于指定格式化日志, 详细[参考](https://www.jianshu.com/p/37ef7bc6d6eb)



#### File

> 将日志信息打印到文件, 默认覆盖原日志文件

```xml
<Appenders>
    <!-- fileName 用于指定日志文件的位置,文件名及类型(一般为.log)-->
    <!-- 可以指定磁盘路径: C:\Users\root\Desktop\logs\applog.log
				项目路径: target/logs/appLog.log -->
    <File name="FileAppender1" fileName="${dir}">
        <!-- 格式化日志 -->
        <PatternLayout>
            <pattern>%d %p %C{1.} [%t] %m%n</pattern>
        </PatternLayout> 	
    </File>
</Appenders>
```





#### RollingFile

> 将日志信息打印到滚动文件, 即包括一个最新的日志文件及若干个备份的日志文件

```xml
<!-- fileName 指定最新的日志信息保存的位置
	 filePattern 指定备份的日志文件的位置及命名格式 
	 可以将备份的日志文件保存为压缩包来节省空间 -->
<RollingFile name="RollingFileAppender1"
             fileName="${baseDir}/appLog.log"
             filePattern="${baseDir}/$${date:yyyy-MM}/%d{MM-dd-yyyy-HH-mm-ss}-%i.log.gz">
    <PatternLayout>
        <!-- 格式化日志 -->
        <Pattern>%d{yyyy/MM/dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n</Pattern>
    </PatternLayout>
</RollingFile>
```

此外在`<RollingFile>`中还可配置多个属性

- DefaultRolloverStrategy

  ```xml
  <DefaultRolloverStrategy max="10">
      <!-- 设置备份文件的删除策略, 条件需要同时满足-->
      <!-- 指定欲删除文件的目录, 及深度 -->
      <Delete basePath="${baseDir}" maxDepth="2">
          <!-- 文件名满足条件 -->
          <IfFileName glob="*/*.log.gz" />
          <!-- 备份文件最新修改时间距此超过15s -->
          <IfLastModified age="15s" />
      </Delete>
  </DefaultRolloverStrategy>
  ```

  > max 设置对同名日志文件的编号的最大值(默认为7)
  >
  > 在前面日志命名规则中, %i 即表示对同名文件进行编号

  

- Policies

  ```xml
  <!-- 指定日志的备份策略, 即达到某一条件就对日志进行备份 -->
  <Policies>
      <!-- 每隔一定的时间间隔就进行备份, 
  	interval的单位与日志文件的命名格式(即filePattern)
  	(${baseDir}/$${date:yyyy-MM}/%d{MM-dd-yyyy-HH-mm-ss}-%i.log.gz)的最小时间单位(ss)有关, 
  	此表示每隔10s备份一次 -->
      <TimeBasedTriggeringPolicy interval="10"
                                 modulate="true"/>
      <!-- 日志文件达到size, 就进行备份 -->
      <SizeBasedTriggeringPolicy size="20MB"/>
  
      <!-- 也可以利用Cron表示式(类似于Linux中的Crontab)设置定时任务进行备份 -->
      <CronTriggeringPolicy  schedule="0/10 * * * * ?"/>
  </Policies>
  ```

  > [Cron表达式](https://www.jb51.net/article/138900.htm)









### Loggers

> Loggers用于配置所有的日志记录器, 包括日志级别及输出位置



#### Root

```xml
<Root level="DEBUG">
    <AppenderRef ref="Console1"/>
</Root>
```

> 如果Logger没有设置级别和输出目的地, 则默认继承Root的配置
>
> Root的默认配置是在控制台打印Error日志级别信息



#### Logger

> 为指定的包, 类, 方法配置日志记录器
>
> 对于没有配置Logger的包, 类, 方法, 则默认继承Root的配置

```xml
<!-- 默认每个Logger会在自己和Root的输出目的地(Appender)各打印一次配置信息
     additivity="false", 即只在自己的输出目的地打印 
	 level="DEBUG", 设置日志级别为DEBUG, 则只打印DEBUG及以下级别的日志信息
	-->
<Logger name="com.shy.main.Test1" level="DEBUG" additivity="false">
    <!-- 为Logger指定响应的输出源, 可以配置多个, 如分别在控制台和文件中打印日志 -->
    <AppenderRef ref="Console1"/>
    <AppenderRef ref="FileAppender1"/>
    <AppenderRef ref="RollingFileAppender1"/>
</Logger>
```

- 在指定位置创建`Logger`

  ```java
  // 当然在继承框架(如Mybatis)时, 并不需要我们手动创建Logger和各级别日志信息
  public class Test1 {
  
      // 默认返回该类的logger()
  	//private static final Logger logger = LogManager.getLogger();
      // 需要与log4j2.xml中的某一个Logger对应, 否则继承Root的配置
  	private static final Logger logger = LogManager.getLogger(Test1.class);
  	public static void main(String[] args) {
          
          // 也可以为某个方法指定Logger,将日志粒度控制到方法
          // Logger logger = LogManager.getLogger("com.shy.main.Test1.main")
              
          // 分别设置该logger八个日志级别的日志信息
  		logger.log(Level.ALL, "main-all level log"); 
  		logger.trace("main-trace level log");
  		logger.debug("main-debug level log");
  		logger.info("main-info level log");
  		logger.warn("main-warn level log");
  		logger.error("main-error level log");
  		logger.fatal("main-fatal level log");
  		logger.log(Level.OFF, "main-off level log");
  		
  	}
  }
  ```

  

## 使用SLF4j



### 为什么要使用SLF4j

> [Java日志框架：slf4j作用及其实现原理 - 五月的仓颉 - 博客园 (cnblogs.com)](https://www.cnblogs.com/xrq730/p/8619156.html)

SLF4j  (Simple Logging Facade For Java) 简单日志门面, 借助`外观设计模式`实现了一组日志标准接口, 降低了项目对具体日志框架的耦合度

![image-20210929135000904](https://gitee.com/a-shy-coder/blog-images/raw/master/image-20210929135000904.png)



### 门面模式

> [JAVA设计模式之门面模式（外观模式） | 菜鸟教程 (runoob.com)](https://www.runoob.com/w3cnote/facade-pattern-3.html)
>
> [Java日志框架：slf4j作用及其实现原理 - 五月的仓颉 - 博客园 (cnblogs.com)](https://www.cnblogs.com/xrq730/p/8619156.html)

门面模式简单来说就是通过一个中介类(Facade),整合所有的模块, 由该中介类向外教提供调用这些模块的接口.

![image-20210929141523152](https://gitee.com/a-shy-coder/blog-images/raw/master/image-20210929141523152.png)

Facade知道所有子模块的功能, 但不必关心子模块的实现, 只需将客户端的请求分法到各个子模块 ( 如SpringMVC中的DispatcherServlet)

- 简单实现

  ```java
  // 子模块
  public class ModuleA {
      public void functionA(){
          System.out.println("ModuleA.functionA");
      }
  }	
  
  public class ModuleB {
      public void functionB(){
          System.out.println("ModuleB.functionB");
      }
  }
  
  public class ModuleC {
      public void functionC(){
          System.out.println("ModuleC.functionC");
      }
  }
  ```

  ```java
  // 提供门面
  public class Facade {
      private static ModuleA moduleA = new ModuleA();
      private static ModuleB moduleB = new ModuleB();
      private static ModuleC moduleC = new ModuleC();
  
      public void Function(String FunctionName){
          if("functionA".equals(FunctionName)){
              moduleA.functionA();
          } else if ("functionB".equals(FunctionName)) {
              moduleB.functionB();
          }else{
              moduleC.functionC();
          }
      }
  }
  ```

  ```java
  // 客户端直接请求门面
  public class Client {
  
      private static Facade facade = new Facade();
  
      public void request(){
          facade.Function("functionA");
          facade.Function("functionB");
          facade.Function("functionC");
      }
  }
  
  ```

  

### SLF4j的简单使用

> 这里选用log4j2作为具体的日志实现

- 引人依赖

  ```xml
  <dependency>
     <groupId>org.apache.logging.log4j</groupId>
     <artifactId>log4j-slf4j-impl</artifactId>
     <version>2.14.1</version>
  </dependency>
  ```

- 引入`Log4j2.xml`

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <configuration status="off">
      <Loggers>
          <Logger name="com.shy.main.Test1" level="warn"/>
      </Loggers>
  </configuration>
  ```

- 测试

  ```java
  // 使用SLF4J, 降低Log4j2与项目的耦合度.
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  import org.slf4j.event.Level;
  
  public class Test1 {
  
     // SLF4j提供了LoggerFactory来创建Logger
     private static final Logger logger = LoggerFactory.getLogger(Test1.class);
     public static void main(String[] args) {
        // SLF4j只提供了5个日志级别: trace, debug, info, warn, error
        //    logger.log(Level.ALL, "test-all level log");
        logger.trace("test-trace level log");
        logger.debug("test-debug level log");
        logger.info("test-info level log");
        logger.warn("test-warn level log");
        logger.error("test-error level log");
  	 //    logger.fatal("test-fatal level log");
  	//    logger.log(Level.OFF, "test-off level log");
     }
  }
  ```