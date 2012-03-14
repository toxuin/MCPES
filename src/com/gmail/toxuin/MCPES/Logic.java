package com.gmail.toxuin.MCPES;
import java.io.ByteArrayOutputStream;

/*
 * Этот класс - мозги сервера. Всю игровую логику надо писать сюда.
 * Метод doSomething определяет тип пакета и направляет его дальше, на обработчик. 
 */

public class Logic {
	
	static String serverID = "FFFFFFFFE964C38D";
	static String serverName = "OMG LOL THAT WORKS";
	static String magic = "00FFFF00FEFEFEFEFDFDFDFD12345678";
	
	static int loginResponseHeader = 0x1C;
	static int mtuResponseHeader = 6;
	
	public static void doSomething(Packet incoming) throws Exception {
System.out.println("RECV: " + Util.getHex(incoming.getPayload()));
		int packetHeader = incoming.get();
//System.out.println("TYPE: " + packetHeader);
		
			switch (packetHeader) {
			case 2:
				UI.log("Получен запрос логина от "+incoming.client);
				login(incoming);
				break;
			case 5:
				UI.log("Получен запрос MTU от "+incoming.client);
				MTUCheck(incoming);
				break;
			case 7:
				UI.log("Семерочка от "+incoming.client);
				replyEight();
				break;
			default:
				UI.log("Неизвестный пакет типа "+packetHeader+" от "+incoming.client);
				break;
			}
	}

	private static void login(Packet incoming) throws Exception{
		byte[] clientTimestamp = Util.splitBytes(incoming.payload, 1, 8);

		String totalServerName = "MCCPP;Demo;"+serverName;
		
		ByteArrayOutputStream sendBuffer = new ByteArrayOutputStream();
		
		sendBuffer.write(loginResponseHeader);
		sendBuffer.write(clientTimestamp);
		sendBuffer.write(Util.hexStringToByteArray(serverID));
		sendBuffer.write(Util.hexStringToByteArray(magic));
		sendBuffer.write(0);
		sendBuffer.write(totalServerName.length());
		sendBuffer.write(totalServerName.getBytes("US-ASCII"));
		
		byte sendData[] = sendBuffer.toByteArray();
		PacketQueue.push(new Packet(sendData, incoming.client, incoming.clientPort));
	}
	
	private static void MTUCheck(Packet incoming) throws Exception{
		
		int lng = incoming.getLength() - 14;
		
		ByteArrayOutputStream sendBuffer = new ByteArrayOutputStream();
		
		sendBuffer.write(mtuResponseHeader);
		sendBuffer.write(Util.hexStringToByteArray(magic));
		sendBuffer.write(Util.hexStringToByteArray(serverID));
		sendBuffer.write(0);
		sendBuffer.write(lng);
		
		//sendBuffer.write(Util.hexStringToByteArray("0600FFFF00FEFEFEFEFDFDFDFD12345678FFFFFFFFCB2E0F320005D4"));
		
		byte sendData[] = sendBuffer.toByteArray();
		PacketQueue.push(new Packet(sendData, incoming.client, incoming.clientPort));
	}
	
	private static void replyEight() {
		// TODO Auto-generated method stub
		
	}
}
