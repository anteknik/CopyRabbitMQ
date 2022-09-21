import com.rabbitmq.client.*;

import java.io.IOException;

public class RecieverAsync {

    private final static String QUEUE_NAME="mastering.rabbitmq";
    public static void main(String[]argv)throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicConsume(QUEUE_NAME, false, new
                DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)throws IOException {
                        String msg = new String(body);
                        System.out.println("Received Message: " + msg);
                    }
                });
    }
}
