package pwd.allen.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import pwd.allen.amqp.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02AmqpApplicationTests {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	AmqpAdmin amqpAdmin;

	/**
	 * 使用AmqpAdmin创建amqp的exchange、quene
	 */
	@Test
	public void createExchange(){

		//创建direct的exchange
		System.out.println("创建exchange");
		amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));

		//创建队列
		amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));

		//创建绑定规则
		amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqp.haha",null));

	}

	/**
	 * 1、单播（点对点）
	 */
	@Test
	public void testDirect() {
		//Message需要自己构造一个;定义消息体内容和消息头
		//rabbitTemplate.send(exchange,routeKey,message);

		//object默认当成消息体，只需要传入要发送的对象，自动序列化发送给rabbitmq；
		//rabbitTemplate.convertAndSend(exchange,routeKey,object);
		Map<String,Object> map = new HashMap<>();
		map.put("msg","这是第一个消息");
		map.put("data", Arrays.asList("helloworld",123,true));
		map.put("book", new Book("西游记","吴承恩"));
		//对象被默认序列化以后发送出去
		//如果是Book对象，headers会加上：__TypeId__:pwd.allen.amqp.bean.Book
		//如果传的是map，headers会加上：__ContentTypeId__:java.lang.Object	__KeyTypeId__:java.lang.Object	__TypeId__:java.util.HashMap
		rabbitTemplate.convertAndSend("exchange.direct","allen.news", new Book("西游记","吴承恩"));

	}

	/**
	 * 接受数据,如何将数据自动的转为json发送出去
	 */
	@Test
	public void receive(){
		Object o = rabbitTemplate.receiveAndConvert("allen.news");
		System.out.println(o.getClass());
		System.out.println(o);
	}

	/**
	 * 广播
	 */
	@Test
	public void testFanout(){
		rabbitTemplate.convertAndSend("exchange.fanout","",new Book("红楼梦","曹雪芹"));
	}

}
