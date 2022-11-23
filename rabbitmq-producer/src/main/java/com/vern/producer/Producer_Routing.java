package com.vern.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author 滨
 * @Date 2022/11/23 16:26
 * @Description: TODO
 * @Version 1.0
 */
public class Producer_Routing {
    public static void main(String[] args) throws Exception{
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

        //5.创建队列交换机
        String exchangeName = "test_direct";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, false, null);

        //6.创建队列Queue
        String queue1Name = "test_direct_queue1";
        String queue2Name = "test_direct_queue2";
        channel.queueDeclare(queue1Name,true,false,false,null);
        channel.queueDeclare(queue2Name,true,false,false,null);
        //7.绑定队列和交换机
        //绑定队列1
        channel.queueBind(queue1Name, exchangeName, "error");
        //绑定队列2
        channel.queueBind(queue2Name, exchangeName, "info");
        channel.queueBind(queue2Name, exchangeName, "warning");
        channel.queueBind(queue2Name, exchangeName, "error");
        //8.发送信息
        String body = "日志信息：调用了delete()方法...：日志级别：error";
        channel.basicPublish(exchangeName, "error", null, body.getBytes());
        //9.释放资源
        channel.close();
        connection.close();
    }
}
