package medium;

class UserDataCloudDataSource implements DataSource<UserData> {
    @Override
    public UserData getData() {
        return new UserData(1, "Imya Familya", "Imya.Familya@gmail.com");
    }
}