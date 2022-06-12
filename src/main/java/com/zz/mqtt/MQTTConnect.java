package com.zz.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class MQTTConnect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String url;
    private String username = null;
    private String password =null;

    public static MqttClient mqttClient = null;
    private static MemoryPersistence memoryPersistence = null;
    private static MqttConnectOptions mqttConnectOptions = null;

    public MQTTConnect() {
        logger.info("AlarmMqttClient come int");
    }

    public void init(String url, String username,String passwd) {
        if (!StringUtils.hasLength(username)) {
            logger.warn("MQTT username is null");
            return;
        }
        this.url = url;
        this.username = username;
        this.password = passwd;
        // 初始化连接设置对象
        mqttConnectOptions = new MqttConnectOptions();
        // 设置账号密码
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        // true可以安全地使用内存持久性作为客户端断开连接时清除的所有状态
        mqttConnectOptions.setCleanSession(true);
        // 设置连接超时
        mqttConnectOptions.setConnectionTimeout(30);
        // 设置持久化方式
        memoryPersistence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient(url, username, memoryPersistence);
        } catch (MqttException e) {
            e.printStackTrace();
            logger.warn("MQTT new MqttClient() 异常:{}", e);
            return;
        }

        // 设置连接和回调
        if (!mqttClient.isConnected()) {
            // 客户端添加回调函数
            mqttClient.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectionLost(Throwable throwable) {
                    try {
                        logger.info("MQTT 连接已断开, 60秒后重新连接");
                        Thread.sleep(60 * 1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    reConnect();
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Client 接收消息主题 : " + topic);
                    System.out.println("Client 接收消息Qos : " + message.getQos());
                    System.out.println("Client 接收消息内容 : " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    logger.info("deliveryComplete");
                }

                @Override
                public void connectComplete(boolean b, String s) {
                    logger.info("connectComplete:{}, s:{}", b, s);
                }
            });
            //    创建连接
            try {
                mqttClient.connect(mqttConnectOptions);
                logger.info("MQTT 连接状态: {}", (mqttClient.isConnected() ? "已连接" : "未连接"));
            } catch (MqttException e) {
                e.printStackTrace();
                logger.warn("MQTT 连接异常:{}", e);
            }
        } else {
            logger.info("MQTT 连接状态已经连接..");
        }
    }

    //关闭连接
    public void closeConnect() {
        // 关闭存储方式
        if (null != memoryPersistence) {
            try {
                memoryPersistence.close();
            } catch (MqttPersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            logger.info("memoryPersistence is null");
        }

        // 关闭连接
        if (null != mqttClient) {
            if (mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                    mqttClient.close();
                } catch (MqttException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                logger.info("mqttClient is not connect");
            }
        } else {
            logger.info("mqttClient is null");
        }
    }

    //    发布消息
    public void publishMessage(String pubTopic, String message, int qos) {
        if (null != mqttClient && mqttClient.isConnected()) {
            logger.info("发布消息   " + mqttClient.isConnected());
            logger.info("id:" + mqttClient.getClientId());
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(qos);
            mqttMessage.setPayload(message.getBytes());

            MqttTopic topic = mqttClient.getTopic(pubTopic);
            if (null != topic) {
                try {
                    MqttDeliveryToken publish = topic.publish(mqttMessage);
                    if (!publish.isComplete()) {
                        logger.info("消息发布成功");
                    } else {
                        logger.info("消息发布失败");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        } else {
            reConnect();
        }

    }

    //    重新连接
    public void reConnect() {
        if (null != mqttClient) {
            if (!mqttClient.isConnected()) {
                if (null != mqttConnectOptions) {
                    try {
                        mqttClient.connect(mqttConnectOptions);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.info("MQTT 重连 mqttConnectOptions is null");
                }
            } else {
                logger.info("MQTT 重连 mqttClient is null or connect");
            }
        } else {
            init(url, username,password);
        }
    }

    //    订阅主题
    public void subTopic(String topic, int qos) {
        if (null != mqttClient && mqttClient.isConnected()) {
            try {
                mqttClient.subscribe(topic, qos);
            } catch (MqttException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            logger.info("mqttClient is error");
        }
    }

    //    清空主题
    public void cleanTopic(String topic) {
        if (null != mqttClient && !mqttClient.isConnected()) {
            try {
                mqttClient.unsubscribe(topic);
            } catch (MqttException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            logger.info("mqttClient is error");
        }
    }
}