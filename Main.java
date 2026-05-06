/*
 * Author: Lenylcia Moholi Lopes
 * Date: 02/05/2026
 * Description: Main class for the Webchat application.
 *              Handles user registration, login, and the message sending menu.
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Registration
        System.out.println("Webchat Registration");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Cell Phone Number (e.g. +27XXXXXXXXX): ");
        String cellNumber = scanner.nextLine();

        // Constructor order matches Login.java: (firstName, lastName, username, password, cellPhoneNumber)
        Login user = new Login(firstName, lastName, username, password, cellNumber);
        System.out.println(user.registerUser());

        // Login
        System.out.println("Webchat Login ===");
        System.out.print("Enter Username: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter Password: ");
        String loginPassword = scanner.nextLine();

        boolean loggedIn = user.loginUser(loginUsername, loginPassword);
        System.out.println(user.returnLoginStatus(loggedIn));

        if (!loggedIn) {
            System.out.println("Exiting application.");
            scanner.close();
            return;
        }

        // Webchat Menu
        System.out.println("Get fimiliar with Webchat.");
        System.out.print("How many messages do you want to send? ");
        int numMessages = Integer.parseInt(scanner.nextLine());

        int messagesSent = 0;
        boolean running = true;

        while (running) {
            System.out.println(" Menu");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (messagesSent >= numMessages) {
                        System.out.println("You have reached your message limit of " + numMessages + ".");
                        break;
                    }

                    System.out.print("Enter recipient cell number (e.g. +27XXXXXXXXX): ");
                    String recipient = scanner.nextLine();

                    System.out.print("Enter your message: ");
                    String messageText = scanner.nextLine();

                    System.out.println(Messages.checkMessageLength(messageText));
                    if (messageText.length() > 250) {
                        break;
                    }

                    Messages msg = new Messages(recipient, messageText, messagesSent);

                    System.out.println(msg.checkRecipientCell());
                    if (!msg.checkRecipientCell().contains("successfully")) {
                        break;
                    }

                    System.out.println("Message ID: " + msg.getMessageID());
                    System.out.println("Message Hash: " + msg.getMessageHash());

                    System.out.println("What would you like to do?");
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message");
                    System.out.print("Choose: ");
                    int action = Integer.parseInt(scanner.nextLine());

                    System.out.println(msg.sentMessage(action));

                    if (action == 1) {
                        messagesSent++;
                    }
                    break;

                case "2":
                    Messages summary = new Messages();
                    System.out.println(summary.printMessages());
                    System.out.println("Total messages sent: " + summary.returnTotalMessages());
                    break;

                case "3":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}