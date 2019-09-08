package pwd.allen.amqp.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pwd.allen.amqp.bean.Book;

@Service
public class BookService {

    /**
     * 监听名为allen.news的quene，如果获取到消息会调用该方法；若quene中保存有消息，启动时会立刻执行
     * @param obj
     */
    @RabbitListener(queues = "allen.news")
    public void receive(Book obj){
        System.out.println("收到消息：" + obj);
    }

    @RabbitListener(queues = "allen")
    public void receive02(Message message){
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
