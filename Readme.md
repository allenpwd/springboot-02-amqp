##springboot结合amqp

####RabbitTemplate
- 给RabbitMQ发送和接受消息

####AmqpAdmin
- 创建和删除 Queue，Exchange，Binding

####监听消息队列的内容
- @EnableRabbit +  @RabbitListener 

####自定义消息转换器
- 注入MessageConverter接口的实现类


##简介
大多应用中，可通过消息服务中间件来提升系统异步通信、扩展解耦能力

#####消息服务中两个重要概念：消息代理（message broker）和目的地（destination）
- 当消息发送者发送消息以后，将由消息代理接管，消息代理保证消息传递到指定目的地。

#####消息队列主要有两种形式的目的地
- 队列（queue）：点对点消息通信（point-to-point）
- 主题（topic）：发布（publish）/订阅（subscribe）消息通信


##RabbitMQ简介：
RabbitMQ是一个由erlang开发的AMQP(Advanved Message Queue Protocol)的开源实现。

#####核心概念
- Message：
消息，消息是不具名的，它由消息头和消息体组成。消息体是不透明的，而消息头则由一系列的可选属性组成，这些属性包括routing-key（路由键）、priority（相对于其他消息的优先权）、delivery-mode（指出该消息可能需要持久性存储）等。

- Publisher：
消息的生产者，也是一个向交换器发布消息的客户端应用程序。

- Exchange：
交换器，用来接收生产者发送的消息并将这些消息路由给服务器中的队列。
Exchange有4种类型：direct(默认)，fanout(广播), topic, 和headers，不同类型的Exchange转发消息的策略有所区别
    - fanout：把所有发送到该Exchange的消息路由到所有与它绑定的Queue中
    - direct：把消息路由到那些binding key与routing key完全匹配的Queue中
    - topic：相比于direct，支持模糊匹配，“*”用于匹配一个单词，“#”用于匹配多个或0单词
    - headers：根据发送的消息内容中的headers属性进行完全匹配

- Queue：
消息队列，用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一个消息可投入一个或多个队列。消息一直在队列里面，等待消费者连接到这个队列将其取走。

- Binding：
绑定，用于消息队列和交换器之间的关联。一个绑定就是基于路由键将交换器和消息队列连接起来的路由规则，所以可以将交换器理解成一个由绑定构成的路由表。
Exchange 和Queue的绑定可以是多对多的关系。

- Connection：
网络连接，比如一个TCP连接。

- Channel：
信道，多路复用连接中的一条独立的双向数据流通道。信道是建立在真实的TCP连接内的虚拟连接，AMQP 命令都是通过信道发出去的，不管是发布消息、订阅队列还是接收消息，这些动作都是通过信道完成。因为对于操作系统来说建立和销毁 TCP 都是非常昂贵的开销，所以引入了信道的概念，以复用一条 TCP 连接。

- Consumer：
消息的消费者，表示一个从消息队列中取得消息的客户端应用程序。

- Virtual Host：
虚拟主机，表示一批交换器、消息队列和相关对象。虚拟主机是共享相同的身份认证和加密环境的独立服务器域。每个 vhost 本质上就是一个 mini 版的 RabbitMQ 服务器，拥有自己的队列、交换器、绑定和权限机制。vhost 是 AMQP 概念的基础，必须在连接时指定，RabbitMQ 默认的 vhost 是 / 。

- Broker：
表示消息队列服务器实体
