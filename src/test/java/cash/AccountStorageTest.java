package cash;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountStorageTest {

    @Test
    void whenAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenAddSameAccountTwoTimes() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        assertThat(storage.add(new Account(1, 100))).isTrue();
    }

    @Test
    void whenUpdateAbsentAccount() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        assertThat(storage.update(new Account(2, 100))).isFalse();
    }

    @Test
    void whenTransferFromNotExistingAccount() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        assertThat(storage.transfer(3, 2, 100)).isFalse();
    }

    @Test
    void whenTransferToNotExistingAccount() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        assertThat(storage.transfer(1, 3, 100)).isFalse();
    }

    @Test
    void whenSourceAccountBecomesNegative() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 0));
        storage.add(new Account(2, 100));
        assertThat(storage.transfer(1, 2, 100)).isFalse();
    }
}