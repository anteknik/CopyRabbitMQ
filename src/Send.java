import com.rabbitmq.client.AMQP;
import org.apache.commons.lang3.SerializationUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //String message = "Hello kk nadisa";
	        //User messageObject= new User(1l,"Anton","Pakujaya");
	        //byte[] dataObject = SerializationUtils.serialize(messageObject);
            //channel.basicPublish("", QUEUE_NAME, null, dataObject);
            //System.out.println(" [x] Sent '" + messageObject+"===>" +dataObject + "'");
            //JSONObject obj = new JSONObject();
            //obj.put("Transaction","Test value");
            //User demo = new User();
            //demo.setId(1l);
            //demo.setNama("lorem ipsum");
            //demo.setAlamat("London");

            //JSONObject obj = new JSONObject(demo);
            //channel.basicPublish("", QUEUE_NAME, null, obj.toString().getBytes());
            //System.out.println(" [x] Sent '" + obj.toString() + "'");

            //channel.exchangeDeclare(exchangeName, "direct", true);
            //channel.queueBind(queueName, exchangeName, routingKey);

            // The message to be sent
            String name="nadisa";
            String message = "<soapenv:Envelope"+
            "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope\">\n" +
            "<soapenv:Header/>\n" +
                    "<soapenv:Body>\n" +
                    "  <p:greet xmlns:p=\"http://greet.service.kishanthan.org\">\n" +
                    "     <in>" + name + "</in>\n" +
                    "  </p:greet>\n" +
                    "</soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message+ "'");

// Populate the AMQP message properties
            //AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
            //builder.messageId(messageID);
            //builder.contentType("text/xml");
            //builder.replyTo(replyToAddress);
            //builder.correlationId(correlationId);
            //builder.contentEncoding(contentEncoding);

// Custom user properties
            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("SOAP_ACTION", "greet");
            //builder.headers(headers);

// Publish the message to exchange
            //channel.basicPublish(exchangeName, queueName, builder.build(), message.getBytes());
        }
    }
}
