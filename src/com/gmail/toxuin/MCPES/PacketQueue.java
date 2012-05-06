package com.gmail.toxuin.MCPES;
import java.util.EmptyStackException;
import java.util.Stack;

/*
 * Это очередь пакетов на отправку. Она умеет:
 * запихивать пакет в очередь
 * высерать последний пакет
 * подсматривать пакет. Пока все.
 * Вообще она еще должна уметь отправлять пакет сразу всем, но для этого надо вводить список кто такие все.
 */

public class PacketQueue {
	
	static Stack<Packet> queue = new Stack<Packet>();
	public static int size = 0;
	
	public static void push(Packet incomingPacket) throws Exception {
	//pushes packet on top of the queue
	//does other shit if necessary
		try {
			queue.push(incomingPacket);
			size = queue.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
//System.out.println("PUSH: " + Util.getHex(incomingPacket.payload));
	}
	
	public static Packet pop() throws Exception {
	// pops out a packet on the bottom of the queue
	//does other shit if necessary
		try { 
			Packet outgoingPacket = queue.pop();
			size = queue.size();
//System.out.println("POPP: " + Util.getHex(outgoingPacket.payload));
			return outgoingPacket;
			} catch (EmptyStackException e) { 
				System.out.println("Someone had popped an empty q!");
				return null;
			}
	}
	
	public static Packet peek() throws Exception {
	// looks at packet #num, but does not remove it from the queue
	//does other shit if necessary
		return queue.peek();
	}
	
	public static void pushToAll() {
		// TODO: эта хуйня посылает пакет сразу всем
	}
}
