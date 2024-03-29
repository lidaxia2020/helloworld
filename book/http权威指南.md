# http权威指南
### Web基础
#### 1.Http概述
- 媒体类型(MIME type):是一种文本标记，表示一种主要的对象类型和一个特定的子类型，中间由一条斜杠来分隔
- URL(统一资源定位符):有2种形式，分别称为URL和URN
    - URL:描述了一台特定服务器上某资源的特定位置.一般包含三个部分:
        - URL的第一部分被称为方案(scheme),说明了访问资源所使用的额协议类型
        - 第二部分是服务器的因特网地址
        - 其余部分指定了Web服务器上的某个资源
    - URN:作为特定内容的唯一名称使用，与目前的资源所在地无关.
- 事务
    - 方法
    - 状态码
- 报文
- 连接
    - a)浏览器从URL中解析出服务器的主机名
    - b)浏览器将服务器的主机名转换成服务器的IP地址
    - c)浏览器将端口号从URL中解析出来
    - d)浏览器建立一条与web服务器的TCP连接
    - e)浏览器像服务器发送一条HTTP请求报文
    - f)服务器向浏览器发送一条HTTP响应报文
    - g)关闭连接，浏览器显示文档
- web的结构化组件
    - 代理
    - 缓存
    - 网关
    - 隧道：建立起来之后，就会再2条连接之间对原始数据进行盲转发的HTTP应用程序
    - Agent代理
    

#### 2.URL与资源
- URL语法格式： 
~~~~
<scheme>://<user>:<password>@<host>:<port>/<path>;<params>?<query>#<frag>
~~~~



#### 3.Http报文
- 报文流：Http报文是http应用程序之间发送得数据块。这些数据块以一些文本得形式元信息开头，这些信息描述了报文的内容及含义。术语“流入”“流出”“上游”“下游”都是用来描述报文方向的。
- 报文格式
~~~~
请求报文：
    <method> <request-URL> <version>
    <entity-body>
响应报文：
    <version> <status> <reason-phrase>
    <status>
    <entity-body>
方法（method）:客户端希望服务器对资源执行的动作。
请求URL(request-URL):命名了所有请求资源，或者URL路径组件的完整URL.
版本(version):报文所使用的http版本。格式：HTTP/<major>.<minor>
状态码(status-code):这三位数字描述了请求过程中所发生的情况。
原因短语(reason-phrase):数字状态码的可读版本，包含行终止序列之前的所有文本。
首部(header):可以有0个或多个首部。每个名字后面都跟着一个冒号(:),然后是一个可选的空格，接着是一个值，最后是一个CRLF。
实体的主体部分(entity-body):实体的主体部分包含一个由任意数据组成的数据块。
~~~~
- 状态码
    - 100-199 信息状态码
    - 200-299 成功状态码
    - 300-399 重定向状态码
    - 400-499 客户端错误状态码
    - 500-599 服务器错误状态码
- 首部
    - 通用首部
    - 请求首部
    - 响应首部
    - 实体首部
    - 扩展首部
    
    
    
#### 4.连接管理
- Tcp连接
    - Tcp的可靠数据管道
    - Tcp流是分段的由IP分组传送
    - 保持TCP连接正确运行
    - 用TCP套接字编程
- 对TCP性能的考虑
    - Http事务的时延
        - 客户端首先需要根据URI确定Web服务器的IP地址和端口号。如果最近没有对URI中的主机名进行访问，通过DNS解析系统将URI中的主机名转换成一个IP地址可能需要花费数十秒的时间。
        - 接下来，客户端会向服务器发送一条TCP连接请求，并等待服务器回送一个请求接受应答。每条新的TCP连接都会建立时延。这个值通常最多只有1，2秒钟，但如果有数百个Http事务的话，这个值会快速叠加上去。
        - 一旦连接建立起来了，客户端就会通过新建立的TCP管道来发送Http请求。数据到达时，Web服务器会从TCP连接中读取请求报文，并对请求进行处理。
        - 然后，web服务器会回送Http响应，这也需要花费时间。
    - 性能聚焦区域
        - Tcp连接建立握手
        - Tcp慢启动拥塞控制
        - 数据聚集的Nagle算法
        - 用于捎带确认的Tcp延迟确认算法
        - Time_wait时延和端口耗尽
    - TCP连接的握手时延
    - 延迟确认
    - TCP慢启动
    - Nagle算法与TCP_NODELAY
     ~~~~
    Nagle算法鼓励发送全尺寸（Lan上的最大尺寸分组约是1500字节，在因特尔网上是几百字节的段）。只有当所有其他分组都被确认之后，Nagle算法才被允许发送非全尺寸的分组；
    http应用常常会在自己的栈中设置TCP_NODELAY,禁用Nagle算法，提高性能
    ~~~~
    - TIME_WAIT累计与端口耗尽
- Http连接的处理
    - 常常被误解的Connection首部
    - 串行事务处理时延
        - 并行连接:通过多条TCP连接发起并发的Http请求
        - 持久连接:重用TCP连接,以消除连接及关闭时延
        - 管道化连接:通过共享的TCP连接发起并发的HTTP请求
        - 复用的连接:交替传送和响应报文(实验阶段)
- 并行连接
    - 并行连接可能会提高页面的加载速度
    - 并行连接不一定更快
    ~~~~
    浏览器是通过一个28.8kbps 的modem连接到因特网上去的
    每个事务都会打开/关闭一条新的连接，会耗费时间和带宽
    由于TCP慢启动特性的存在，每条新连接的性能都会有所降低
    可打开的并行连接数量实际上是有限的
    ~~~~
- 持久连接
     - 持久以及并行连接
       - 持久连接降低了时延和连接建立的开销,将连接保持在已调谐状态，而且减少了打开连接的潜在数量。
     - Http/1.0 + keep-alive连接
       ~~~~
       Keep-Alive首部完全是可选的。但只有在提供Connection: Keep-Alive 时能使用它。
       Keep-Alive参数timeout是在keep-Alive响应首部发送的。它估计了服务器希望连接保持在活跃状态的时间。这并不是一个承诺值。
       参数max,估计了服务器还希望为多少个事务保持此连接的活跃状态
       ~~~~
     - http/1.1 持久连接(persistent connection)
- 管道化连接
- 关闭连接的奥秘
    - "任意"解除连接
    - Content-Length及截尾操作
    - 连接关闭容限、重试以及幂等性
    - 正常关闭连接
         
  
### Http结构        
#### 5.Web服务器
- web服务器实际步骤（有图：基本web服务器请求的步骤.png）
    - 建立连接
    ~~~~
    1、处理新连接
        客户端请求到Web服务器的TCP连接时，会建立连接，从TCP连接种将ip地址解析出来。
    一旦新连接建立起来并被接受，服务器就会将新连接添加到其现存的Web服务器列表种，做好监视连接上数据传输的准备
    2、客户端主机名识别
        可以用“反向DNS”对大部分Web服务器进行配置，以便将客户端Ip地址转换成客户端主机名。
    3、通过ident确定客户端用户
    ~~~~
    - 接收请求
    ~~~~
        解析请求行，查找请求方法、指定的资源标识符(URI)以及版本号，各项之间由一个空格分隔，并以一个回车换行(CRLF)序列作为结束
        读取以CRLF结尾的报文首部
        检测到以CRLF结尾的、标识首部结束的空行(如果有的话)
        如果有的话(长度由Content-Length首部指定)，读取请求主体
    ~~~~
    - 处理请求
    - 访问资源
    ~~~~
    1、docroot:文档的根目录
    2、目录列表
    3、动态内容资源的映射
    4、服务端包含项
    5、访问控制
    ~~~~
    - 构建响应
    ~~~~
    1、响应实体
    2、MIME类型
    3、重定向
    ~~~~ 
    - 发送响应
    - 记录事务处理的过程


#### 6.代理
- web 的中间实体
138


#### 7.缓存
#### 8.集成点：网关、隧道及中继
#### 9.Web机器人
#### 10.Http-NG
#### 11.客户端识别与cookie机制
#### 12.基本认证机制
#### 13.摘要机制
#### 14.安全Http
