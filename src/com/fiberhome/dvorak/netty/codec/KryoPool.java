package com.fiberhome.dvorak.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.fiberhome.dvorak.netty.serialize.kryo.KryoSerialization;
import com.fiberhome.dvorak.netty.serialize.kryo.KyroFactory;




public class KryoPool {
	
	private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
	
	private KyroFactory kyroFactory;
	

	private int maxTotal=100;
	

	private int minIdle=10;
	

	private long maxWaitMillis=-1;
	
	private long minEvictableIdleTimeMillis=600000;
	
	@PostConstruct
	public void init() {
		kyroFactory = new KyroFactory(maxTotal, minIdle, maxWaitMillis, minEvictableIdleTimeMillis);
	}
	
	public void encode(final ByteBuf out, final Object message) throws IOException {
		ByteBufOutputStream bout = new ByteBufOutputStream(out);
		bout.write(LENGTH_PLACEHOLDER);
		KryoSerialization kryoSerialization = new KryoSerialization(kyroFactory);
		kryoSerialization.serialize(bout, message);
	}
	
	public Object decode(final ByteBuf in) throws IOException {
		KryoSerialization kryoSerialization = new KryoSerialization(kyroFactory);
		return kryoSerialization.deserialize(new ByteBufInputStream(in));
	}
}
