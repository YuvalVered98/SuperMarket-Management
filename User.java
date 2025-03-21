public abstract class User {
    private final String userName;
    private String password;

    public User (String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    //Getters//
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    //Setters//
    public void setPassword(String password) {
        this.password = password;
    }

    //Print and toStrings//
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'';

    }
}
