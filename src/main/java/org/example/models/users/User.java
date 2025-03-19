package org.example.models.users;

public class User {
    private final String username;
    private final String password;
    private final String role;
    private int rentedVehicleId;

    public User(String username, String password, String role, int rentedVehicleId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.rentedVehicleId = rentedVehicleId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getRole() {
        return role;
    }

    public int getRentedVehicleId() {
        return rentedVehicleId;
    }

    public void setRentedVehicleId(int rentedVehicleId) {
        this.rentedVehicleId = rentedVehicleId;
    }

    public static User fromCSV(String[] parts) {
        String login = parts[0];
        String password = parts[1];
        String role = parts[2];
        int id = Integer.parseInt(parts[3]);

        return new User(login, password, role, id);
    }

    public String toCSV() {
        return username + ";" + password + ";" + role + ";" + rentedVehicleId;
    }

    @Override
    public String toString() {
        return "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", rentedVehicleId=" + rentedVehicleId;
    }
}
