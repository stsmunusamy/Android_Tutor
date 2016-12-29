package com.myapps35.tutorial.WifiPrinter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myapps35.tutorial.R;
import com.myapps35.tutorial.SplashMainIndex.Utility.UtilsClass;

import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WifiPrinterActivity1 extends AppCompatActivity
{

    Button btnMethod1;
    EditText et_printIpAddress;
    EditText et_printPortAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_printer1);

        initialize();

        setOnClickListener();
    }

    private void initialize()
    {
        btnMethod1 = (Button) findViewById(R.id.btnMethod1);

        et_printIpAddress = (EditText) findViewById(R.id.et_printIpAddress);

        et_printPortAddress = (EditText) findViewById(R.id.et_printPortAddress);
    }

    private void setOnClickListener()
    {
        btnMethod1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                methodOne();
            }
        });
    }

    private void methodOne()
    {
        try
        {
            Socket objSocket = new Socket();
            String sIP = UtilsClass.validateString(et_printIpAddress.getText().toString());
            String sPort = UtilsClass.validateString(et_printPortAddress.getText().toString());
            InetSocketAddress objEndPoint = new InetSocketAddress(sIP, Integer.parseInt(sPort));
            DataOutputStream objOutputStream;

            objSocket.connect(objEndPoint, 3000);

            objOutputStream = new DataOutputStream(objSocket.getOutputStream());
            objOutputStream.write(("Test Print").getBytes());

            objOutputStream.close();
            objSocket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}