package com.fiberhome.dvorak;

import com.fiberhome.dvorak.netty.server.NettyServer;

public class ProviderTest {
    public static void main(String[] args){
    	new NettyServer().start(8020);
    }
}
