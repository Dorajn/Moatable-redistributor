import org.example.Redistributor;
import org.example.Transfer;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RedistributorTests {

    @Test
    public void testSimpleRedistribution() {
        Map<String, Integer> accounts = new HashMap<>();
        accounts.put("account1", 130);
        accounts.put("account2", 90);
        accounts.put("account3", 120);
        accounts.put("account4", 70);

        int threshold = 100;

        List<Transfer> expectedTransfers = List.of(
                new Transfer("account1", "account4", 30),
                new Transfer("account3", "account2", 10)
        );

        List<Transfer> result = Redistributor.redistributeMoney(accounts, threshold);

        assertEquals(expectedTransfers.size(), result.size());
        assertTrue(result.containsAll(expectedTransfers));
    }

    @Test
    public void testNoRedistributionNeeded() {
        Map<String, Integer> accounts = new HashMap<>();
        accounts.put("account1", 100);
        accounts.put("account2", 100);
        accounts.put("account3", 100);

        int threshold = 100;

        List<Transfer> result = Redistributor.redistributeMoney(accounts, threshold);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testWhenOneDonorDonatesManyTimes() {
        Map<String, Integer> accounts = new HashMap<>();
        accounts.put("account1", 100);
        accounts.put("account2", 0);
        accounts.put("account3", 1900);
        accounts.put("account4", 50);
        accounts.put("account5", 99);

        int threshold = 100;

        List<Transfer> expectedTransfers = List.of(
                new Transfer("account3", "account2", 100),
                new Transfer("account3", "account4", 50),
                new Transfer("account3", "account5", 1)
        );

        List<Transfer> result = Redistributor.redistributeMoney(accounts, threshold);

        assertEquals(expectedTransfers.size(), result.size());
        assertTrue(result.containsAll(expectedTransfers));
    }

    @Test
    public void testWhenOnlyDonorsExist() {
        Map<String, Integer> accounts = new HashMap<>();
        accounts.put("account1", 150);
        accounts.put("account2", 130);

        int threshold = 100;

        List<Transfer> result = Redistributor.redistributeMoney(accounts, threshold);

        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testWhenOnlyRecipientsExist() {
        Map<String, Integer> accounts = new HashMap<>();
        accounts.put("account1", 80);
        accounts.put("account2", 90);

        int threshold = 100;

        List<Transfer> result = Redistributor.redistributeMoney(accounts, threshold);

        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testWhenExactMatchPossible() {
        Map<String, Integer> accounts = new HashMap<>();
        accounts.put("a", 150);
        accounts.put("b", 90);
        accounts.put("c", 70);
        accounts.put("d", 90);

        int threshold = 100;

        List<Transfer> expectedTransfers = List.of(
                new Transfer("a", "c", 30),
                new Transfer("a", "b", 10),
                new Transfer("a", "d", 10)
        );

        List<Transfer> result = Redistributor.redistributeMoney(accounts, threshold);

        assertEquals(expectedTransfers.size(), result.size());
        assertTrue(result.containsAll(expectedTransfers));
    }

    @Test
    public void testMixedDonationsAndIfBalanceMatches() {
        Map<String, Integer> accounts = new HashMap<>();
        accounts.put("account1", 130);
        accounts.put("account2", 0);
        accounts.put("account3", 280);
        accounts.put("account4", 20);
        accounts.put("account5", 100);
        accounts.put("account6", 50);
        accounts.put("account7", 300);

        int threshold = 100;
        int balanceBefore = accounts.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        List<Transfer> expectedTransfers = List.of(
                new Transfer("account7", "account2", 100),
                new Transfer("account3", "account4", 80),
                new Transfer("account7", "account6", 50)
        );

        List<Transfer> result = Redistributor.redistributeMoney(accounts, threshold);
        int balanceAfter = accounts.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        assertEquals(expectedTransfers.size(), result.size());
        assertEquals(balanceAfter, balanceBefore);
        assertTrue(result.containsAll(expectedTransfers));
    }

    @Test
    public void testWithEmptyAccounts() {
        Map<String, Integer> accounts = new HashMap<>();
        int threshold = 100;

        List<Transfer> result = Redistributor.redistributeMoney(accounts, threshold);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testWithLowThreshold() {
        HashMap<String, Integer> accounts = new HashMap<>();
        accounts.put("a", Integer.MAX_VALUE);
        accounts.put("b", 10);
        accounts.put("c", 10);

        int threshold = 0;

        List<Transfer> result = Redistributor.redistributeMoney(accounts, threshold);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testWithManyAccounts() {
        Map<String, Integer> accounts = new HashMap<>();
        accounts.put("account1", 300);
        accounts.put("account2", 50);
        accounts.put("account3", 80);
        accounts.put("account4", 200);
        accounts.put("account5", 40);
        accounts.put("account6", 120);
        accounts.put("account7", 10);
        accounts.put("account8", 300);
        accounts.put("account9", 90);
        accounts.put("account10", 100);

        int threshold = 100;

        List<Transfer> expectedTransfers = List.of(
                new Transfer("account8", "account7", 90),
                new Transfer("account1", "account5", 60),
                new Transfer("account1", "account2", 50),
                new Transfer("account8", "account3", 20),
                new Transfer("account4", "account9", 10)
        );

        List<Transfer> result = Redistributor.redistributeMoney(accounts, threshold);

        assertEquals(expectedTransfers.size(), result.size());
        assertTrue(result.containsAll(expectedTransfers));
    }

}
