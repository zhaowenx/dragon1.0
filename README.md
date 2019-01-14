# dragon1.0
构建一个分布式项目，学习spring cloud相关内容

项目简介：
    eurekaone、eurekatwo两台注册中心，相互注册，注册到其中一台的服务能够被注册到另一台的服务所发现，实现了注册中心的高可用。
    log日志文件，微服务的日志都会存储到该文件里面，而且根据服务名区分，日期修改后启动，日志会存储到历史日志中，日志名和时间相关。
    configserver配置中心，这是一台配置中心服务端程序，通过url能够获取git上面的配置。
    config-server存储在git上面的配置文件，其中区分了版本{application}-{profile}，在配置中心客户端配置的时候有用到。
    user用户相关的服务（服务提供者）。
    resource资源相关的服务（服务提供者）。
    manage管理台，用于服务消费。
    
实现的功能点：
    spring boot相关内容。
    两台注册中心，服务注册与消费。
    mysql数据库。
    mybatis持久层框架。
    熔断器Hystrix。
    spring cloud config 配置中心。
    spring cloud bus 消息总线（学习完kafka后加上）。
    未完待续。。。
    