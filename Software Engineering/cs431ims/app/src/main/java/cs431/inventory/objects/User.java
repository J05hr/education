package cs431.inventory.objects;

public class User {
    private String name;

    //User Permission Level: 0:customer, 1:employee, 2:admin
    public static int CUSTOMER = 0;
    public static int EMPLOYEE = 1;
    public static int ADMIN = 2;

    private int permission;

    public User(String name, int permission) {
        this.name= name;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    boolean hasPermission(int target) {
        if (permission >= target) {
            return true;
        }
        return false;
    }
}
