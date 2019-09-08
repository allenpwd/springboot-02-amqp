##springboot结合amqp

####RabbitTemplate
- 给RabbitMQ发送和接受消息

####AmqpAdmin
- 创建和删除 Queue，Exchange，Binding

####监听消息队列的内容
- @EnableRabbit +  @RabbitListener 

####自定义消息转换器
- 注入MessageConverter接口的实现类