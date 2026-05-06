/*
 * Description: JUnit unit tests for the Messages class.
 *              Tests message length, recipient formatting,
 *              message hash, message ID, and sentMessage options.
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class MessagesTest {

    // ---- Test Data (Task 1) ----
    // Message: "Hi tonight"
    // Recipient: +27838968976

    // ---- Test Data (Task 2 / Message 2) ----
    // Recipient: 08575975889 (incorrectly formatted)
    // Message: "Hi Keegan, did you receive the payment?"
    // Action: Discard

    // ============================================================
    // Message Length Tests
    // ============================================================

    @Test
    public void testMessageLengthSuccess() {
        String shortMessage = "Hi tonight";
        assertEquals("Message ready to send.", Messages.checkMessageLength(shortMessage));
    }

    @Test
    public void testMessageLengthFailure() {
        // Build a message longer than 250 characters
        String longMessage = "This is a very long message that exceeds the two hundred and fifty "
                + "character limit set by the QuickChat application and should therefore trigger "
                + "the failure message indicating how many characters over the limit this message "
                + "actually is, which is quite a lot.";
        int over = longMessage.length() - 250;
        String expected = "Message exceeds 250 characters by " + over + "; please reduce the size.";
        assertEquals(expected, Messages.checkMessageLength(longMessage));
    }

    // Recipient Cell Number Tests

    @Test
    public void testRecipientCellSuccess() {
        Messages msg = new Messages("+27838968976", "Hi tonight", 0);
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientCellFailure() {
        // 08575975889 has no international code and is 11 digits
        Messages msg = new Messages("08575975889", "Hi Keegan, did you receive the payment?", 1);
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. "
            + "Please correct the number and try again.",
            msg.checkRecipientCell()
        );
    }

    // Message Hash Test
    
    @Test
    public void testMessageHashFormat() {
        // We can't predict the auto-generated ID, so we check the format:
        // First 2 chars of ID + ":" + message number + ":" + FIRSTLASTWORD (all caps)
        Messages msg = new Messages("+27838968976", "Hi tonight", 0);
        String hash = msg.getMessageHash();

        // Hash should be all uppercase
        assertEquals(hash, hash.toUpperCase());

        // Hash should contain two colons
        int colonCount = hash.length() - hash.replace(":", "").length();
        assertEquals(2, colonCount);

        // Hash should end with "HITONIGHT"
        assertTrue("Hash should end with HITONIGHT", hash.endsWith("HITONIGHT"));
    }

    // Message ID Test

    @Test
    public void testMessageIDCreated() {
        Messages msg = new Messages("+27838968976", "Hi tonight", 0);
        assertTrue("Message ID should not be null or empty",
                msg.getMessageID() != null && !msg.getMessageID().isEmpty());
        System.out.println("Message ID generated: " + msg.getMessageID());
    }

    @Test
    public void testMessageIDLength() {
        Messages msg = new Messages("+27838968976", "Hi tonight", 0);
        assertTrue("Message ID should be 10 characters or less", msg.checkMessageID());
    }

    // sentMessage Tests (Send / Disregard / Store)
    
    @Test
    public void testSentMessageSend() {
        Messages msg = new Messages("+27838968976", "Hi tonight", 0);
        assertEquals("Message successfully sent.", msg.sentMessage(1));
    }

    @Test
    public void testSentMessageDisregard() {
        Messages msg = new Messages("+27838968976", "Hi tonight", 0);
        assertEquals("Press 0 to delete the message.", msg.sentMessage(2));
    }

    @Test
    public void testSentMessageStore() {
        Messages msg = new Messages("+27838968976", "Hi tonight", 0);
        assertEquals("Message successfully stored.", msg.sentMessage(3));
    }
}
