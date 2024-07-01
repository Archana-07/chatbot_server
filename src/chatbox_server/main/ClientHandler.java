package chatbox_server.main;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;

public class ClientHandler implements Runnable {
	BufferedReader reader;
	SocketChannel socket;

	public ClientHandler(SocketChannel clientSocket) {
		socket = clientSocket;
		reader = new BufferedReader(Channels.newReader(socket, UTF_8));
	}

	@Override
	public void run() {
		SimpleChatServer chatServer = new SimpleChatServer();
		String message;
		try {
			while ((message = reader.readLine()) != null) {
				System.out.println("read " + message);
				chatServer.tellEveryone(message);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


}
