/*
 
 * Description: Messages class for the webchat application.
 *              Handles message creation, validation, sending,
 *              storing, and disregarding messages.
DECLARATION LIST:
 * messageID        -  the unique 10-digit ID generated for each message
 * messageNumber    -  the number/count of the message in the session
 * recipient        - the recipient's cell phone number
 * message          -  the actual message text (payload)
 * messageHash      - the generated hash for the message
 * sentMessages     -  all sent messages as a list during the session
 * totalMessagesSent - Traces the total number of messages sent
 * rand             - Random object used to generate the message ID
 * id               - Saves the randomly generated numeric message ID
 * idPrefix         - Saves the first 2 digits of the message ID for the hash
 * words            - Array storing each word of the message after splitting
 * firstWord        -  saves the first word of the message for the hash
 * lastWord         - saves the last word of the message for the hash
 * hash             - Saves the combined hash string before converting to uppercase
 * sb               - StringBuilder used to build the list of sent messages
 * msg              - Saves each individual sent message during the loop
 * file             - FileWriter object used to write messages to the text file
 * over             - Saves how many characters the message exceeds the limit by
 
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Messages {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    private static ArrayList<String[]> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    public Messages(String recipient, String message, int messageNumber) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    public Messages() {}

    private String generateMessageID() {
        Random rand = new Random();
        long id = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.startsWith("+") && recipient.length() <= 12) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an "
                    + "international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        String idPrefix = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String hash = idPrefix + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                sentMessages.add(new String[]{messageID, messageHash, recipient, message});
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Sent Messages ---\n");
        for (String[] msg : sentMessages) {
            sb.append("Message ID: ").append(msg[0]).append("\n");
            sb.append("Message Hash: ").append(msg[1]).append("\n");
            sb.append("Recipient: ").append(msg[2]).append("\n");
            sb.append("Message: ").append(msg[3]).append("\n");
            sb.append("---------------------\n");
        }
        return sb.toString();
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void storeMessage() {
        try (FileWriter file = new FileWriter("stored_messages.txt", true)) {
            file.write("Message ID: " + messageID + "\n");
            file.write("Message Number: " + messageNumber + "\n");
            file.write("Recipient: " + recipient + "\n");
            file.write("Message: " + message + "\n");
            file.write("Message Hash: " + messageHash + "\n");
            file.write("---------------------\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public static String checkMessageLength(String msg) {
        if (msg.length() <= 250) {
            return "Message ready to send.";
        } else {
            int over = msg.length() - 250;
            return "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public int getMessageNumber() { return messageNumber; }
}
