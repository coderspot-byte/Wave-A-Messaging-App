package com.gpp.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gpp.Algorithms.AES;

public class Chat {

    final String secretKey = "ssshhhhhhhhhhh!!!!";
    private String sender;
    private String receiver;
    private String message;
    private boolean isseen;
    int i;

    public void getans(int ans)
    {
        i = ans;
    }
    public Chat(String sender, String receiver, String message,boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getMessage()
    {

        if(i == 1)
        {
            if(message.length() == 24)
            {
                return AES.decrypt(message , secretKey);
            }
            else
            {
                return "FED ENCRYPTED";
            }

        }
        else if(i == 2)
        {
            if(message.length() == 24)
            {
                return "AES ENCRYPTED";
            }
            return message;
        }

        return "ENCRYPTED";
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
