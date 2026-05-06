/**
 DECLARATION LIST
 firstName        - Saves the user's first name
 lastName         - Saves the user's last name
 username         - Saves the user's chosen username
 password         - Saves the user's chosen password
 cellPhoneNumber  - Saves the user's SA cell phone number
 hasCapital       - used as flag to check if password contains a capital letter
 hasNumber        - used as flag to check if password contains a number
 hasSpecial       - used as flag to check if password contains a special character
 regex            - Regular expression pattern for validating SA phone numbers
 c                - Saves each character of the password during loop iteration
 loggedIn         - used as flag to indicate if the user is logged in
 */

public class Login {

    // Instance variables
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    // Constructor
    public Login(String firstName, String lastName, String username, String password, String cellPhoneNumber) {
        this.firstName       = firstName;
        this.lastName        = lastName;
        this.username        = username;
        this.password        = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // checkUserName - must contain underscore and be <= 5 characters
    public boolean checkUserName() {
        if (username.contains("_") && username.length() <= 5) {
            return true;
        }
        return false;
    }

    // checkPasswordComplexity
    // Must be >= 8 chars, contain capital, number, special character
    public boolean checkPasswordComplexity() {
        if (password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber  = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c))            { hasCapital = true; }
            else if (Character.isDigit(c))           { hasNumber  = true; }
            else if (!Character.isLetterOrDigit(c))  { hasSpecial = true; }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // checkCellPhoneNumber
    // South African format: +27XXXXXXXXX (regex-based)
    // Reference: https://regexr.com/39p0t (adapted for SA numbers)
    public boolean checkCellPhoneNumber() {
        String regex = "^\\+27[0-9]{9}$";
        return cellPhoneNumber.matches(regex);
    }

    // registerUser - returns registration status message
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    // loginUser - checks entered credentials match registered ones
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    // returnLoginStatus - returns appropriate login message
    public String returnLoginStatus(boolean loggedIn) {
        if (loggedIn) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Getters
    public String getUsername()        { return username; }
    public String getFirstName()       { return firstName; }
    public String getLastName()        { return lastName; }
    public String getCellPhoneNumber() { return cellPhoneNumber; }

    boolean loginUser() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}