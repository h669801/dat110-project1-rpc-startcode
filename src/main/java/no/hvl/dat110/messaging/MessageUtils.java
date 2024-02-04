package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {
		
		byte[] segment = null;
		byte[] data;
		
		// TODO - START
		
		// encapulate/encode the payload data of the message and form a segment
		// according to the segment format for the messaging layer
		
//		if (true)
//			throw new UnsupportedOperationException(TODO.method());

//		data = message.getData();
//		segment = new byte[SEGMENTSIZE];
//		System.arraycopy(data, 0, segment, 0, Math.min(SEGMENTSIZE, data.length));

		data = message.getData();
		if (data.length > SEGMENTSIZE - 1) {
			throw new IllegalArgumentException("Data length cannot be longer than " + SEGMENTSIZE + " bytes");
		}
		segment = new byte[SEGMENTSIZE];
		segment[0] = (byte) data.length;
		for (int i = 0; i < data.length; i++) {
			segment[i + 1] = data[i];
		}


		// TODO - END
		return segment;
		
	}

	public static Message decapsulate(byte[] segment) {

		Message message = null;
		
		// TODO - START
		// decapsulate segment and put received payload data into a message


		int size = segment[0] & 0xFF;

		// Opprett en ny byte-array for meldingsdata
		byte[] data = new byte[size];
//		if (true)
//			throw new UnsupportedOperationException(TODO.method());
//		byte[] data = new byte[segment.length];
//		System.arraycopy(segment, 0, data, 0, segment.length);
//		message = new Message(data);

		int actualLength;
		for (actualLength = 0; actualLength < segment.length; actualLength++) {
			if (segment[actualLength] == 0) {
				break; // Assuming that 0 marks the end of payload
			}
		}

		// Create a new byte array to hold the actual payload data
//		byte[] data = Arrays.copyOfRange(segment, 0, actualLength);

		for (int i = 0; i < size; i++) {
			data[i] = segment[i + 1];
		}
		// Create a new Message object using the received payload data
		message = new Message(data);
		
		// TODO - END
		
		return message;
		
	}
	
}
