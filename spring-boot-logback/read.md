configuration节点
这是一个根节点，其中的各个属性如下：
1. scan ：当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。
2. scanPeriod ：设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫
秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。
3. debug ：当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。
默认值为false。
<pattern>${PATTERN}</pattern>
</encoder>
</appender>
<!--指定最基础的日志输出级别-->
<root level="DEBUG">
<!--appender将会添加到这个loger-->
<appender-ref ref="consoleLog"/>
<appender-ref ref="fileDEBUGLog"/>
<appender-ref ref="fileErrorLog"/>
</root>
<!-- 定义指定package的日志级别-->
<logger name="org.springframework" level="DEBUG"></logger>
<logger name="org.mybatis" level="DEBUG"></logger>
<logger name="java.sql.Connection" level="DEBUG"></logger>
<logger name="java.sql.Statement" level="DEBUG"></logger>
<logger name="java.sql.PreparedStatement" level="DEBUG"></logger>
<logger name="io.lettuce.*" level="INFO"></logger>
<logger name="io.netty.*" level="ERROR"></logger>
<logger name="com.rabbitmq.*" level="DEBUG"></logger>
<logger name="org.springframework.amqp.*" level="DEBUG"></logger>
<logger name="org.springframework.scheduling.*" level="DEBUG"></logger>
<!--定义com.xxx..xx..xx包下的日志信息不上传，直接输出到fileDEBUGLog和fileErrorLog这个两个appender
中，日志级别为DEBUG-->
<logger name="com.xxx.xxx.xx" additivity="false" level="DEBUG">
<appender-ref ref="fileDEBUGLog"/>
<appender-ref ref="fileErrorLog"/>
</logger>
</configuration>
logging.config=classpath:logging-config.xml
root节点
这是一个必须节点，用来指定基础的日志级别，只有一个 level 属性，默认值是 DEBUG 。
该节点可以包含零个或者多个元素，子节点是 appender-ref ，标记这个 appender 将会添加到这个logger
中。
contextName节点
标识一个上下文名称，默认为default，一般用不到
property节点
标记一个上下文变量，属性有name和value，定义变量之后可以使用 ${} 来获取。
appender节点
用来格式化日志输出节点，有两个属性 name 和 class ，class用来指定哪种输出策略，常用就是控制台
输出策略和文件输出策略。
这个节点很重要，通常的日志文件需要定义三个appender，分别是控制台输出，常规日志文件输出，异
常日志文件输出。
该节点有几个重要的子节点，如下：
1. filter ：日志输出拦截器，没有特殊定制一般使用系统自带的即可，但是如果要将日志分开，比
如将ERROR级别的日志输出到一个文件中，将除了 ERROR 级别的日志输出到另外一个文件中，此
时就要拦截 ERROR 级别的日志了。
2. encoder ： 和pattern节点组合用于具体输出的日志格式和编码方式。
3. file : 节点用来指明日志文件的输出位置，可以是绝对路径也可以是相对路径
4. rollingPolicy : 日志回滚策略，在这里我们用了TimeBasedRollingPolicy，基于时间的回滚策略,
有以下子节点fileNamePattern，必要节点，可以用来设置指定时间的日志归档。
5. maxHistory : 可选节点，控制保留的归档文件的最大数量，超出数量就删除旧文件,，例如设置为
30的话，则30天之后，旧的日志就会被删除
6. totalSizeCap : 可选节点，用来指定日志文件的上限大小，例如设置为3GB的话，那么到了这个
值，就会删除旧的日志
logger节点
可选节点，用来具体指明包的日志输出级别，它将会覆盖root的输出级别。
该节点有几个重要的属性如下：
1. name ：指定的包名
2. level ：可选，日志的级别
3. addtivity ：可选，默认为true，将此logger的信息向上级传递，将有root节点定义日志打印。
如果设置为false，将不会上传，此时需要定义一个 appender-ref 节点才会输出。