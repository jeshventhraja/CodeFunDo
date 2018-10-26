package com.helpoffline.helpoffline;

import org.json.JSONObject;

/**
 * Created by jeshventh on 25/10/18.
 */

public class Message {

    long id;
    String message;
    String deviceName;

    Message(String mes,String name)
    {
        id = System.nanoTime();
        message = mes;
        deviceName = name;
    }

    Message(JSONObject msg)
    {
        try {
            id = Long.parseLong(msg.getString("time"));
            message = msg.getString("message");
            deviceName = msg.getString("name");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    JSONObject messageToJson()
    {
        JSONObject temp = new JSONObject();
        try {
            temp.put("time", id);
            temp.put("name",deviceName);
            temp.put("message",message);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return temp;
    }

}
