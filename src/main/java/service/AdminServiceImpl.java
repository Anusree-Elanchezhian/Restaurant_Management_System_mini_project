package service;


public class AdminServiceImpl extends AdminService {
    private static final String ADMIN_USERNAME = "manager@gmail.com";
    private static final String ADMIN_PASSWORD = "manager123";

    @Override
    public boolean isAdminUser(String username, String password) {
        return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }
} 
