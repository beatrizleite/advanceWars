package edu.ufp.inf.sd.rabbit.advanceWars.producer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Objects;

public class Send {
    private static int nplayers = 0;
    Connection conn;
    Channel channel;

    private static String rpcqname = "rpc_queue";
    private static String fanout = "fan_out";
    private static String wqueue = "queue_w";
    private TokenRing tokenRing;

    public Send() throws Exception {
        //fazer só para 2 jogadores (smallvs map)
        this.tokenRing = new TokenRing(2);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        this.conn = factory.newConnection();
        this.channel = conn.createChannel();
        channel.queueDeclare(rpcqname, false, false, false, null);
        channel.queuePurge(rpcqname);
        channel.basicQos(1);
    }

    public static void main(String[]argv) throws Exception {
        Send send = new Send();
        System.out.println("[x] Awaiting RPC requests");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            AMQP.BasicProperties replyproperties = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();
            String response = Integer.toString(nplayers);
            try {
                String msg = new String(delivery.getBody(), "UTF-8");
                if (msg.equals("Hello")) {
                    send.channel.basicPublish("", delivery.getProperties().getReplyTo(), replyproperties, response.getBytes("UTF-8"));
                    send.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    nplayers++;
                }
            } catch (RuntimeException e) {
                System.out.println(" [.] " + e);
            }
        };
        send.channel.basicConsume(rpcqname, false, deliverCallback, (consumerTag -> {}));
        send.channel.exchangeDeclare(fanout, "fanout");
        send.channel.queueDeclare(wqueue, false, false, false, null);
        System.out.println("we're in Send.java");
        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String []msg = new String(delivery.getBody(), "UTF-8").split(";");
            String action = msg[0];
            int observer = Integer.parseInt(msg[1]);
            if(Objects.equals(action, "buy")) {
                action = "buy "+msg[2];
                System.out.println(action);
            }

            if(send.tokenRing.currentHolder() == observer) {
                send.channel.basicPublish(fanout, "", null, action.getBytes("UTF-8"));
                if(action.equals("endturn")){
                    send.tokenRing.nextHolder();
                }
            }
        };
        try {
            send.channel.basicConsume(wqueue, true, deliverCallback1, consumerTag ->{});
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }
}
