## 执行器开发 关注点

### 优势

资源隔离：通过独立执行器分担主服务压力
部署灵活：执行器可独立扩缩容

### 调用流程

1. 定时器采用的是 thinglinks-xxx-boot-impl 的实现类，所以需要引入 thinglinks-xxx-boot-impl 依赖(
   不会走FeignClient调用已注册的微服务)；
2. 定时任务采用boot-impl 的实现方式，主要是考虑到，
   单独启动iot-executor服务作为执行器，目的就是为了减轻xxx服务的压力，所以定时任务也应该在iot-executor中执行，而不是在xxx微服务中执行。
3. JobHandler中的业务涉及到的调用其他服务，thinglinks-xxx-boot-impl FacadeImpl 必须要实现，并且需要依赖此 微服务的
   Nacos配置,在 bootstrap.yml 中配置,否则会报错；具体原因如上。