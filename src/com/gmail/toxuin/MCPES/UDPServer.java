package com.gmail.toxuin.MCPES;
import java.net.*;
import java.io.*;

/*
 * ���� ����� ������ ����� ����� ������� �����:
 * 1. ������� ���� �� ������� ���������� �������
 * 2. ������� � ������� �� �������� � ���� ��� ���� ������ - ���������� ��.
 * 3. ???
 * 4. Profit!
 */

public class UDPServer{
	
	static Thread thread;
	static DatagramSocket socket;
	static boolean running = false;
	
	static int port = 19132;
	
	public static class StartThread implements Runnable{
		StartThread(){
			thread = new Thread(this);
			thread.start();
			running = true;
			UI.button1.setEnabled(false);
			UI.button2.setEnabled(true);
		}
	
	public void run(){
			try{
				byte[] buffer = new byte[1024];
				try{
					socket = new DatagramSocket(port);
					while(true){
						try{
							if (running) {
								
								
								// �������
								DatagramPacket dataPacket = new DatagramPacket(buffer, buffer.length );
								socket.receive(dataPacket);
								
								Packet incomingPacket = new Packet(buffer, dataPacket.getAddress(), dataPacket.getPort());
								
								Logic.doSomething(incomingPacket);
								

								// �������
								if (PacketQueue.size > 0) {
									Packet sendPacket = PacketQueue.pop();
									DatagramPacket sendDatagram = new DatagramPacket(sendPacket.payload, sendPacket.payload.length, sendPacket.client, sendPacket.clientPort);
									socket.send(sendDatagram);
							System.out.println("SENT: " + Util.getHex(sendPacket.payload));
								}
								
								
								
							
								
							}
						}
						catch(UnknownHostException ue){} catch (Exception e) {
							// TODO Auto-generated catch block
							//e.printStackTrace(); // WUBWUBWUBWUB!!!1!
						}
					}
				}
				catch(java.net.BindException b){}
			}
			catch (IOException e){
				System.err.println(e);
			}
		}
	}
}