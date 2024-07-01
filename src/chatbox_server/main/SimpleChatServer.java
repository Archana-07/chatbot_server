package chatbox_server.main;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class SimpleChatServer {

	private final List<PrintWriter> clientWriters = new ArrayList<>();

	public void go() {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(5000));
			while (serverSocketChannel.isOpen()) {
				SocketChannel clientSocket = serverSocketChannel.accept();
				PrintWriter writer = new PrintWriter(Channels.newWriter(clientSocket, UTF_8));
				clientWriters.add(writer);
				threadPool.submit(new ClientHandler(clientSocket));
				System.out.println("got a connection");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void tellEveryone(String message) {
		for (PrintWriter writer : clientWriters) {
			writer.println(message);
			writer.flush();
		}
	}
}
