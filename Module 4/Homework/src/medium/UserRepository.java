package medium;

class UserRepository implements DataSource<UserData> {
    private final CachedDataSource<UserData> cache;
    private final UserDataCloudDataSource cloudDataSource;

    public UserRepository(CachedDataSource<UserData> cache, UserDataCloudDataSource cloudDataSource) {
        this.cache = cache;
        this.cloudDataSource = cloudDataSource;
    }

    @Override
    public UserData getData() {
        UserData data = cache.getData();
        if (data == null) {
            data = cloudDataSource.getData();
            cache.saveData(data);
        }
        return data;
    }
}