package edu.ufp.inf.sd.rabbit.advanceWars.consumer;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rabbit.advanceWars.consumer.aw_game.engine.Game;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Recv {
    private Connection conn;
    private Channel channel;
    private String reqqname = "rpc_queue";

    public Recv() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        conn = factory.newConnection();
        channel = conn.createChannel();
    }

    public static void main(String[] args) throws Exception {
        Recv recv = new Recv();
        String id  = recv.getReply();
        System.out.println("recv.java here");
        new Game("SmallVs", Integer.parseInt(id));
    }

    public String getReply() throws Exception {
        final String id = UUID.randomUUID().toString();
        String replyqname = channel.queueDeclare().getQueue();
        AMQP.BasicProperties properties = new AMQP.BasicProperties
                .Builder()
                .correlationId(id)
                .replyTo(replyqname)
                .build();
        channel.basicPublish("", reqqname, properties, "Hello".getBytes("UTF-8"));
        final CompletableFuture<String> responde = new CompletableFuture<>();
        String channeltag = channel.basicConsume(replyqname, true, (consumerTag, delivery) -> {
            if(delivery.getProperties().getCorrelationId().equals(id)) {
                responde.complete(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });
        String res;
        try {
            res = responde.get(20, TimeUnit.SECONDS);
        } catch (TimeoutException tex) {
            throw new TimeoutException();
        } finally {
            channel.basicCancel(channeltag);
        }
        return res;
    }

}
