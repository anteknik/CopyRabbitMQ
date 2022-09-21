import org.apache.commons.lang3.SerializationUtils;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
	/*Consumer consumer = new DefaultConsumer(channel) {

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                throws IOException {

            String message = new String(body, "UTF-8");
            //byte [] data = new byte[];

            Object object =    SerializationUtils.deserialize(body);

            System.out.println(" [x] Received '" + object + "'");
         }
      };
    channel.basicConsume(QUEUE_NAME, true, consumer);
     
    }*/
}
