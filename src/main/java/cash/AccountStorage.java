package cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        if (getById(account.id()).isEmpty()) {
            return accounts.put(account.id(), account) != null;
        }
        return false;
    }

    public synchronized boolean update(Account account) {
        if (getById(account.id()).isPresent()) {
            return accounts.replace(account.id(), accounts.get(account.id()), account);
        }
        return false;
    }

    public synchronized void delete(int id) {
        if (getById(id).isPresent()) {
            accounts.remove(id);
        }
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (getById(fromId).isEmpty() || getById(toId).isEmpty()) {
            return false;
        }
        if (accounts.get(fromId).amount() - amount < 0) {
            return false;
        }
        boolean from = update(new Account(fromId, accounts.get(fromId).amount() - amount));
        boolean to = update(new Account(toId, accounts.get(toId).amount() + amount));
        return from && to;
    }
}
