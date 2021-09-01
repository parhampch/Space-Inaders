package sample;

import java.util.ArrayList;

public class Account {
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    private static Account loggedInAccount;
    private String userName;
    private String password;
    private int highScore;


    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public static void setLoggedInAccount(Account loggedInAccount) {
        Account.loggedInAccount = loggedInAccount;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static Account getLoggedInAccount() {
        return loggedInAccount;
    }

    private static Account getAccountWithUsername(String userName)
    {
        for (Account account : allAccounts) {
            if(userName.equalsIgnoreCase(account.getUserName()))
            {
                return account;
            }
        }
        return null;
    }

    public static boolean isAccountWithName(String userName)
    {
        for (Account account : allAccounts) {
            if (userName.equalsIgnoreCase(account.getUserName()))
            {
                return true;
            }
        }
        return false;
    }

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        allAccounts.add(this);
    }

    public static boolean checkLogin(String userName, String password)
    {
        Account account = getAccountWithUsername(userName);
        if (account == null)
        {
            return false;
        }
        if(!password.equals(account.getPassword()))
        {
            return false;
        }
        Account.setLoggedInAccount(account);
        return true;
    }

    private int compare(Account account)
    {
        if(this.getHighScore() != account.getHighScore())
        {
            return this.getHighScore() - account.getHighScore();
        }
        return account.getUserName().compareTo(this.getUserName());
    }

    public void changeUsername(Account account, String username)
    {
        account.setUserName(userName);
    }

    private static ArrayList<Account> sortAccounts()
    {
        ArrayList<Account> temp = (ArrayList<Account>) allAccounts.clone();
        ArrayList<Account> sorted = new ArrayList<>();
        while (!temp.isEmpty())
        {
            Account minAccount = temp.get(0);
            for (Account account : temp) {
                if(minAccount.compare(account) < 0)
                {
                    minAccount = account;
                }
            }
            sorted.add(minAccount);
            temp.remove(minAccount);
        }
        return sorted;
    }

    public static ArrayList<String> sortedAccounts()
    {
        ArrayList<Account> sorted = sortAccounts();
        ArrayList<String> finalSort = new ArrayList<>();
        for (Account account : sorted) {
            finalSort.add(account.getUserName() + " : " + account.getHighScore());
        }
        return finalSort;
    }

}
