import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
    private final static String QUEUE_NAME = "mastering.rabbitmq";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //1
        Connection connection = factory.newConnection(); //2
        Channel channel = connection.createChannel(); //3
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);//4
        String message = "Hello Mastering RabbitMQ  2 !";
        channel.basicPublish
                ("", QUEUE_NAME, null, message.getBytes());//5
        System.out.println("Following Message Sent: " + message);
        channel.close();
        connection.close();
    }
}