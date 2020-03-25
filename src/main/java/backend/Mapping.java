package backend;

import java.sql.*;
import java.util.Scanner;

public class Mapping {
    public static Connection getConnection (String USERNAME, String PASSWORD) {
//        Class.forName("org.postgresql.Driver");
        Connection conn = null;
        String URL = "jdbc:postgresql://dbs.fiit.uk.to/asset_manager";
        try {
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static boolean addNew(Connection conn, Scanner scan){
        do {
            System.out.println("What are we working with? \n1 - Assets\n2 - Departments\n3 - Location");
            int input = scan.nextInt();
            if (input == 1) {
                do {
                AssetObject assetObject = new AssetObject(conn);
                System.out.println("1 - List assets\n2 - Add new\n3 - Delete asset");
                int next = scan.nextInt();
                if (next == 1) {
                    assetObject.fillSelf();
                    for (int i = 0; i < assetObject.asset_id.size(); i++) {
                        System.out.format("%10d%20s%20s%20s%10d\n", assetObject.asset_id.get(i), assetObject.name.get(i), assetObject.type.get(i), assetObject.category.get(i), assetObject.asset_department.get(i));
                    }
                } else if (next == 2) {
                    Boolean success = true;
                    String trash = scan.nextLine();
                    System.out.println("Name:");
                    String name = scan.nextLine();
                    System.out.println("Type");
                    String type = scan.nextLine();
                    System.out.println("Category");
                    String category = scan.nextLine();
                    System.out.println("Department ID");
                    int department = scan.nextInt();
                    try {
                        assetObject.addNew(conn, name, type, category, department);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        success = false;
                    }
                    if (success) {
                        System.out.println("Done");
                    }
                } else if (next == 3) {
                    Boolean success = true;
                    String trash = scan.nextLine();
                    System.out.println("Type ID of asset to delete:");
                    int id = scan.nextInt();
                    try {
                        assetObject.deleteByID(conn, id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        success = false;
                    }
                    if (success) System.out.println("Done");
                } else {
                    System.out.println("Invalid input");
                }
                System.out.println("Enter e to return, c to continue");
                }
                while (!scan.next().equals("e"));
            } else if (input == 2) {
                do {
                    DepartmentObject departmentObject = new DepartmentObject(conn);
                    System.out.println("1 - List departments\n2 - Add new\n3 - Delete department");
                    int next = scan.nextInt();
                    if (next == 1) {
                        departmentObject.fillSelf();
                        for (int i = 0; i < departmentObject.department_id.size(); i++) {
                            System.out.format("%10d%20s%10d\n", departmentObject.department_id.get(i), departmentObject.name.get(i), departmentObject.location.get(i));
                        }
                    } else if (next == 2) {
                        Boolean success = true;
                        String trash = scan.nextLine();
                        System.out.println("Name:");
                        String name = scan.nextLine();
                        System.out.println("Location ID:");
                        int location = scan.nextInt();
                        try {
                            departmentObject.addNew(conn, name, location);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            success = false;
                        }
                        if (success) {
                            System.out.println("Done");
                        }
                    } else if (next == 3) {
                        Boolean success = true;
                        String trash = scan.nextLine();
                        System.out.println("Type ID of department to delete:");
                        int id = scan.nextInt();
                        try {
                            departmentObject.deleteByID(conn, id);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            success = false;
                        }
                        if (success) System.out.println("Done");
                    } else {
                        System.out.println("Invalid input");
                    }
                    System.out.println("Enter e to return, c to continue");
                }
                while (!scan.next().equals("e"));

            } else if (input == 3) {
                do {
                LocationObject locationObject = new LocationObject(conn);
                System.out.println("1 - List locations\n2 - Add new\n3 - Delete location");
                int next = scan.nextInt();
                if (next == 1) {
                    locationObject.fillSelf();
                    for (int i = 0; i < locationObject.location_id.size(); i++) {
                        System.out.format("%10d%20s%20s%10d\n", locationObject.location_id.get(i), locationObject.state.get(i), locationObject.address.get(i), locationObject.postcode.get(i));
                    }
                } else if (next == 2) {
                    Boolean success = true;
                    String trash = scan.nextLine();
                    System.out.println("State:");
                    String state = scan.nextLine();
                    System.out.println("Address:");
                    String address = scan.nextLine();
                    System.out.println("Postcode");
                    int postcode = scan.nextInt();
                    try {
                        locationObject.addNew(conn, state, address, postcode);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        success = false;
                    }
                    if (success) {
                        System.out.println("Done");
                    }
                } else if (next == 3) {
                    Boolean success = true;
                    String trash = scan.nextLine();
                    System.out.println("Type ID of department to delete:");
                    int id = scan.nextInt();
                    try {
                        locationObject.deleteByID(conn, id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        success = false;
                    }
                    if (success) System.out.println("Done");
                } else {
                    System.out.println("Invalid input");
                }
                System.out.println("Enter e to return, c to continue");
                }
                while (!scan.next().equals("e"));
            }
            System.out.println("Type e to exit, c to continue");
        } while (!scan.next().equals("e"));
        
        return true;
    }

}