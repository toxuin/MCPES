package com.gmail.toxuin.MCPES;
import java.net.InetAddress;

/*
 * Это класс пакета. Он описывает структуру объекта Packet. Я даж незнаю чо тут еще написать.
 */

public class Packet {

	int readIndex = 0; //The read index.
	
	byte[] payload = null;
	InetAddress client;
	int clientPort;

	/** Initialise the packet.
	 * @param payload
	 */
	public Packet(byte[] payload, InetAddress client, int port) {
		this.payload = payload;
		this.client = client;
		this.clientPort = port;
	}

	/** Get a byte from packet.
	 * @return
	 */
	public int get() {
		return payload[readIndex++] & 0xff;
	}

	/** Get byte array from packet.
	 * @param buffer
	 * @param offset
	 * @param length
	 * @return
	 */
	public byte[] getBytes(byte[] buffer, int offset, int length) {
		if (length == 0) length = payload.length;
		for(int i = offset; i < length; i++) {
			buffer[i] = (byte) get();
		}
		return buffer;
	}

	/** Get an integer from packet.
	 * @return
	 */
	public int getInt() {
		return (getShort() << 16) | getShort();
	}

	/** Get a short from packet.
	 * @return
	 */
	public int getShort() {
		return (get() << 8) | get();
	}

	/** Get a boolean from packet.
	 * @return
	 */
	public boolean getBoolean() {
		return get() == 1;
	}

	/** Get a string from packet.
	 * @return
	 */
	public String getString() {
		int data = '\n';
		StringBuilder builder = new StringBuilder();
		while ((data = get()) != '\n') {
			builder.append((char) data);
		}
		return builder.toString();
	}

	/** Get a long from packet.
	 * @return
	 */
	public long getLong() {
		return ((long) getInt() << 32L) | getInt();
	}

	/** Get remaining data to be read.
	 * @return
	 */
	public int remaining() {
		return payload.length - readIndex;
	}

	/** Get the length of the packet.
	 * @return
	 */
	public int getLength() {
		return payload.length;
	}

	/** Get the payload.
	 * @return
	 */
	public byte[] getPayload() {
		return payload;
	}

}
