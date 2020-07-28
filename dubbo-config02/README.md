#### 1. XML配置
![配置依赖](http://dubbo.apache.org/docs/zh-cn/user/sources/images/dubbo-config.jpg)

|标签	|用途	|解释|
| :--- |:---|:---|
|\<dubbo:service/>	|服务配置	|用于暴露一个服务，定义服务的元信息，一个服务可以用多个协议暴露，一个服务也可以注册到多个注册中心|
|\<dubbo:reference/> |引用配置	|用于创建一个远程服务代理，一个引用可以指向多个注册中心|
|\<dubbo:protocol/>	|协议配置	|用于配置提供服务的协议信息，协议由提供方指定，消费方被动接受|
|\<dubbo:application/>	|应用配置	|用于配置当前应用信息，不管该应用是提供者还是消费者|
|\<dubbo:module/>	|模块配置	|用于配置当前模块信息，可选|
|\<dubbo:registry/>	|注册中心配置	|用于配置连接注册中心相关信息|
|\<dubbo:monitor/>	|监控中心配置	 |用于配置连接监控中心相关信息，可选|
|\<dubbo:provider/>	|提供方配置	|当 ProtocolConfig 和 ServiceConfig 某属性没有配置时，采用此缺省值，可选|
|\<dubbo:consumer/>	|消费方配置	|当 ReferenceConfig 某属性没有配置时，采用此缺省值，可选|
|\<dubbo:method/>	|方法配置	|用于 ServiceConfig 和 ReferenceConfig 指定方法级的配置信息|
|\<dubbo:argument/>	|参数配置	|用于指定方法参数配置
- 配置的覆盖关系（timeout,retries,loadbalance,actives等）
    - 方法级优先，接口级次之，全局配置再次之。
    - 如果级别一样，则消费方优先，提供方次之。
- reference配置默认是延迟加载只有引用被注入到其他bean或getBean时才加载，如需要饥饿加载则可以配置\<dubbo:reference ... init="true" /> ︎
#### 2. 属性配置
- 优先级从高到低：
    - JVM -D参数，当你部署或者启动应用时，它可以轻易地重写配置，比如，改变dubbo协议端口；
    - XML, XML中的当前配置会重写dubbo.properties中的；
    - Properties，默认配置，仅仅作用于以上两者没有配置时。
- 如果classpath下有多个dubbo.properties，比如多个jar包中都有则dubbo随机选择一个加载，并大一闹错误日志
- 如果没有配置id则使用name作为默认属性
#### 3. API配置
#### 4. 注解配置
#### 5. 动态配置中心
#### 6. 配置加载流程
#### 7. 自动加载环境变量