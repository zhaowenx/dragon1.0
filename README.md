# dragon1.0
构建一个分布式项目，学习spring cloud相关内容

项目简介：
    eurekaone、eurekatwo两台注册中心，相互注册，注册到其中一台的服务能够被注册到另一台的服务所发现，实现了注册中心的高可用。
    log日志文件，微服务的日志都会存储到该文件里面，而且根据服务名区分，日期修改后启动，日志会存储到历史日志中，日志名和时间相关。
    configserver配置中心，这是一台配置中心服务端程序，通过url能够获取git上面的配置。
    config-server存储在git上面的配置文件，其中区分了版本{application}-{profile}，在配置中心客户端配置的时候有用到。
    user用户相关的服务（服务提供者）。
    resource资源相关的服务（服务提供者）。与resources提供同一服务，实现负载均衡
    resources资源相关的服务（服务提供者）。
    manage管理台，用于服务消费。
    zipkinshow服务跟踪，对每一次的请求进行记录，能够界面化显示一次请求所访问的服务
    
实现的功能点：
    spring boot相关内容。
    两台注册中心，服务注册与消费。
    mysql数据库。
    Feign支持服务的调用以及均衡负载，
    mybatis持久层框架。
    熔断器Hystrix。
    spring cloud config 配置中心。
    spring cloud bus 消息总线（学习完kafka后加上）。
    zuul 网关  请求转发
    spring cloud Sleuth和zipkin 服务追踪
    未完待续。。。
    
焦点：
    feign 适用于服务之间相互调用
    zuul 适用于外部程序调用微服务，通过网关转发给对应的微服务
    hibbon 负载均衡
    
zuul:
    filter是zuul的核心，filter的生命周期为“pre”，“routing”，“post”，“error”.
    PRE： 这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
    ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
    POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
    ERROR：在其他阶段发生错误时执行该过滤器。
    1、自定义filter：可以用于对请求进行限制，比如校验请求中是否含有token。manage项目中的TokenFilter类
    2、自动转发：网关服务注册到注册中心后，可以直接通过服务名访问微服务
    3、路由熔断：当我们的后端服务出现异常的时候，我们不希望将异常抛出给最外层，期望服务可以自动进行一降级。
                 当某个服务出现异常时，直接返回我们预设的信息。
                 manage项目中的ResourceFallback类。
    4、路由重试：因为网络或者其它原因，服务可能会暂时的不可用，这个时候我们希望可以再次对服务进行重试。
    