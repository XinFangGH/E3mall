import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class ActionMQTest {

    @Test
    public void sendMessage() throws Exception {
//        生产者
//        1、创建一个ConnectionFactory对象。使用ActivemqConnectionFactory类创建对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");

//        2、使用ConnectionFactory创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
//        3、开启连接，调用Connection的start方法
        connection.start();
//        4、使用Connection对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        5、使用Session创建一个Destnation，两种形式queue、topic，应该创建一个Queue对象。
        Queue queue = session.createQueue("test");
//        6、使用Session对象创建一个消息的生产者。
        MessageProducer messageProducer = session.createProducer(queue);
//        7、创建一个TextMessage对象
        TextMessage textMessage = session.createTextMessage("你摊上事了1");
//        8、发送消息
        messageProducer.send(textMessage);
//        9、关闭资源
        messageProducer.close();
        session.close();
        connection.close();
    }

    @Test
    public void getMessage() throws Exception {
//        消费者
//        1、创建一个ConnectionFactory对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
//        2、使用ConnectionFactory创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
//        3、开启连接
        connection.start();
//        4、使用Connection对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        5、使用Session对象创建一个destnation对象
        Queue queue = session.createQueue("test");
//        6、创建一个消息的消费者。
        MessageConsumer consumer = session.createConsumer(queue);
//        7、设置消息的监听器。
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;

//        8、在监听器中接收消息并打印
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("系统已经启动");
//        9、系统需要阻塞。
        System.in.read();
//        10、系统关闭，关闭资源
        System.out.println("系统关闭");
        consumer.close();
        session.close();
        connection.close();

    }

    @Test
    public void springTest() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        JmsTemplate jmsTemplate = ac.getBean(JmsTemplate.class);
        Destination destination = (Destination) ac.getBean("queueDestination");
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage("摊上事了吗");
            }
        });

    }
}
