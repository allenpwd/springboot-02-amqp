package pwd.allen.amqp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 需要有无参构造器，否则自定义的json转换器转换Book时会报异常：
 * com.fasterxml.jackson.databind.JsonMappingException: Can not construct instance of pwd.allen.amqp.bean.Book: no suitable constructor found, can not deserialize from Object value (missing default constructor or creator, or perhaps need to add/enable type information?)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String bookName;
    private String author;

}
