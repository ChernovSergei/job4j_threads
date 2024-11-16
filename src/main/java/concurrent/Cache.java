package concurrent;

public final class Cache {
    private static Cache cache;

    public synchronized static Cache getCache() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
