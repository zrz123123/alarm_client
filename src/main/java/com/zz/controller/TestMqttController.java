package com.zz.controller;

import com.alibaba.druid.util.StringUtils;
import com.zz.mqtt.MQTTConnect;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//控制层，导入Service层
@RestController
public class TestMqttController {

    @PostMapping("/send/{message}")
    public static void init(@PathVariable String message) {
        MQTTConnect MQTTConnect = new MQTTConnect();
        MQTTConnect.init("tcp://101.43.160.204:11883", "123123","Gqu3st@!");

        MQTTConnect.subTopic("/mqtt", 0);
        processCommand(MQTTConnect, message);
        // MQTTConnect.publishMessage("/topic", "i am comming...", 1);
        MQTTConnect.closeConnect();
    }

    private static void processCommand(MQTTConnect MQTTConnect, String line) {
        try {
            if (StringUtils.isEmpty(line)) {
                return;
            }

            String[] arr = line.split(" ");//StrUtil.split(line, " ");
            String command = arr[0];
            System.out.println("command:" + command);
            if ("send".equalsIgnoreCase(command)) {
                MQTTConnect.publishMessage(arr[1], arr[2], 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}