package stationType3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Sensors {

	private static Random rand = new Random();

	// Port for server to listen on
	private static int port = 9876;

	public static void main(String[] theArgs) throws IOException {

		// Try to create an open socket
		try (ServerSocket listener = new ServerSocket(port)) {

			System.out.println("Sensors Online");

			// Continually run the server
			while (true) {

				// Try to accept a connection to the server
				try (Socket socket = listener.accept()) {

					// Send the data to the connected client
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println(getWindSpeed() + " " + getTemperature() + " " + getHumidity() + " " + getBar() + " "
							+ getRain());
				} catch (IOException e) {
					System.exit(1);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static int getWindSpeed() {
		return rand.nextInt(30) + 10;
	}

	public static int getTemperature() {
		return rand.nextInt(50) + 30;
	}

	public static int getHumidity() {
		return rand.nextInt(30) + 20;
	}

	public static int getBar() {
		return rand.nextInt(100);
	}

	public static int getRain() {
		return rand.nextInt(2);
	}

	public static long getTime() {
		return System.currentTimeMillis();
	}
}
