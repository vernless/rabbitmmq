package com.vern.consumer.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * @Author 滨
 * @Date 2022/11/23 16:57
 * @Description: TODO
 * @Version 1.0
 */
public class Consumer_HelloWorld {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2.设置参数
        factory.setHost("192.168.32.128");// 虚拟机ip
        factory.setPort(5672);// 端口号
        factory.setVirtualHost("/itcast");//虚拟机 默认值
        factory.setUsername("vern");//用户名 默认值
        factory.setPassword("123456");//密码 默认值
        //3.创建连接 Connection
        Connection connection = factory.newConnection();
        //4.创建Channel
        Channel channel = connection.createChannel();
        //5.创建队列Queue
        channel.queueDeclare("hello_world",true,false,false,null);
        //6.接受消息
        Consumer consumer = new DefaultConsumer(channel){
            //回调方法，当收到消息后会自动执行该方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag:" + consumerTag);
                System.out.println("envelope:" + envelope.getExchange());
                System.out.println("RoutingKey:" + envelope.getRoutingKey());
                System.out.println("properties:" + properties);
                System.out.println("body:" + new String(body));
            }
        };
        channel.basicConsume("hello_world", true, consumer);
        //消费者不用关闭资源
    }
}
