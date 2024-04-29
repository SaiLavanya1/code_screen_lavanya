package resources;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import dev.codescreen.BankAccount;
import dev.codescreen.BankService;

public class BankServiceTest {
    private BankService bankService;
    
    @Before
    public void setUp() {
        bankService = new BankService();
    }

    @Test
    public void testLoadTransactions() {
        BankAccount account1 = bankService.createAccount("1");
        double balance;

        // Test Load Transaction 1
        balance = account1.deposit(100.00);
        assertEquals(100.00, balance, 0.01);

        // Test Load Transaction 2
        balance = account1.deposit(3.23);
        assertEquals(103.23, balance, 0.01);
    }

    @Test
    public void testAuthorizationTransactions() {
        BankAccount account1 = bankService.createAccount("1");
        double balance;

        // Initial deposit to set the balance
        account1.deposit(103.23);

        // Test Authorization Transaction 1 (Valid)
        balance = account1.withdraw(100.00);
        assertEquals(3.23, balance, 0.01);

        // Test Authorization Transaction 2 (Denied due to insufficient funds)
        balance = account1.withdraw(10.00);
        assertEquals(3.23, balance, 0.01); // Balance should not change
    }

    @Test
    public void testAuthorizationOnEmptyAccount() {
        BankAccount account2 = bankService.createAccount("2");
        double balance;

        // Test Authorization on a new account with no initial deposit (Denied)
        balance = account2.withdraw(50.01);
        assertEquals(0.00, balance, 0.01); // Balance should remain zero
    }

    @Test
    public void testMixedTransactions() {
        BankAccount account3 = bankService.createAccount("3");
        double balance;

        // Test Load for Account 3
        balance = account3.deposit(50.01);
        assertEquals(50.01, balance, 0.01);

        // Test Authorization (Denied since no money in account 2)
        BankAccount account2 = bankService.createAccount("2");
        balance = account2.withdraw(50.01);
        assertEquals(0.00, balance, 0.01); // Balance should remain zero

        // If Account 2 had money
        account2.deposit(50.01);
        balance = account2.withdraw(50.01);
        assertEquals(0.00, balance, 0.01); // Balance should go to zero after withdrawal
    }
}
