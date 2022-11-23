package com.vern.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author 滨
 * @Date 2022/11/23 16:26
 * @Description: TODO
 * @Version 1.0
 */
public class Producer_WorkQueues {
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
        channel.queueDeclare("work_queues",true,false,false,null);
        for (int i = 0; i < 10; i++) {
            String body = "hello RabbbitMQ~~~   " + i;
            //6.发送消息
            channel.basicPublish("","work_queues", null, body.getBytes());
        }
        //7.释放资源
        channel.close();
        connection.close();

    }
}
