package com.helpoffline.helpoffline;

/**
 * Created by jeshventh on 25/10/18.
 */

public class Message {

    long id;
    String message;

    Message(String mes)
    {
        id = System.nanoTime();
        message = mes;
    }

}
