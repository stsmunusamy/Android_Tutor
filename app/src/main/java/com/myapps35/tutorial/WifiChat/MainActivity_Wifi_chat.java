package com.myapps35.tutorial.WifiChat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class MainActivity_Wifi_chat extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_main_activity);

        ((Button) findViewById(R.id.btnServer)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity_Wifi_chat.this, ServerActivity.class));
            }
        });

        ((Button) findViewById(R.id.btnClient)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity_Wifi_chat.this, ClientActivity.class));
            }
        });


        main();

//        new RetrieveFeedTask().execute();

    }

    public void main()
    {
        try
        {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();

            while (nis.hasMoreElements())
            {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration ias = ni.getInetAddresses();
                while (ias.hasMoreElements())
                {
                    InetAddress ia = (InetAddress) ias.nextElement();
                    Log.i("Ip_Address_List", ia.getHostAddress());
                }
            }
        }
        catch (SocketException ex)
        {
            ex.printStackTrace();
        }
    }

    public static String getSubnet(InetAddress address)
    {
        byte[] a = address.getAddress();
        String subnet = "";
        for(int i = 0; i < a.length - 1; i++)
        {
            if(a[i] != 0)
                subnet += (256 + a[i]) + ".";
            else
                subnet += 0 + ".";
        }
        return subnet;
    }

    public static ArrayList<InetAddress> checkHosts(int socket, int timeout)
    {
        ArrayList<InetAddress> al = new ArrayList<InetAddress>();
        try
        {
            byte[] buffer = {1};
            DatagramSocket ds = new DatagramSocket(socket);
            ds.setSoTimeout(timeout);
            DatagramPacket dp;
            InetAddress local = InetAddress.getLocalHost();
            String subnet = getSubnet(local);
            System.out.println(subnet);
            for(int i = 1; i <= 255; i++)
            {
                String host = subnet + i;
                try {
                    ds.send(new DatagramPacket(buffer, 1, InetAddress.getByName(host), 3333));
                    ds.receive(dp = new DatagramPacket(buffer, 1));
                    if(dp.getPort() == socket && !dp.getAddress().equals(local))
                        al.add(dp.getAddress());
                } catch(Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }

    public class RetrieveFeedTask extends AsyncTask<String, Void, Void>
    {
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                InetAddress localhost = InetAddress.getLocalHost();
                // this code assumes IPv4 is used
                byte[] ip = localhost.getAddress();
                for (int i = 1; i <= 254; i++)
                {
                    ip[3] = (byte)i;
                    InetAddress address = InetAddress.getByAddress(ip);
                    if (address.isReachable(1000))
                    {
                        Log.i("IP_Address_List", "HostName" + address.getHostName() + " \t" + address.getHostAddress() + "\t");
                        // machine is turned on and can be pinged
                    }
                    else if (!address.getHostAddress().equals(address.getHostName()))
                    {
                        Log.i("Ip_Address_List", "machine is known in a DNS lookup");
                    }
                    else
                    {
                        Log.i("Ip_Address_List", "Host name could not resolved");
                        // the host address and host name are equal, meaning the host name could not be resolved
                    }
                }
            }
            catch (NetworkOnMainThreadException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }
}