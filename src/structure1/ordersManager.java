package structure1;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ordersManager {
    
    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Order> orders = new LinkedList<Order>();
    
//==============================================================
    public LinkedList<Order> getordersData() {
        return orders;
    }
    
//==============================================================
    public ordersManager(String fName) {
        try {
            File docsfile = new File(fName);
            Scanner reader = new Scanner(docsfile);
            String line = reader.nextLine();
            
            while(reader.hasNext()) {
                line = reader.nextLine();
                String [] data = line.split(","); 
                int oid = Integer.parseInt(data[0]);
                int cid = Integer.parseInt(data[1]);
                
                String pp = data[2].replaceAll("\"", "");
                String [] p = pp.split(";");
                Integer [] pids = new Integer [p.length];
             
                int i = 0;
                while (i < pids.length) {
                    pids[i] = Integer.parseInt(p[i].trim());
                    i++;
                }
                double price = Double.parseDouble(data[3]);
                String date = data[4];
                String status = data[5];
                        
                Order order = new Order(oid, cid, pids, price, date, status);
                orders.insert(order);
            }
            reader.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //==============================================================
    public int cancelOrder(int oid) {
        orders.findFirst();
        int i = 0;
        while (i < orders.size()) {
            if (orders.retrieve().getoId() == oid) {
                if (orders.retrieve().status.compareToIgnoreCase("cancelled") == 0) {
                    System.out.println("Order " + orders.retrieve().getoId() + " was cancelled before");
                    return 2;
                }
                    
                Order ordera = orders.retrieve(); 
                ordera.status = "cancelled";
                orders.insert(ordera);
                return 1; 
            }
            else
                orders.findNext();
            
            i++;
        }
        return 0;
    }
    
    //==============================================================
 public boolean UpdateOrder(int orderID) {
    boolean found = false;
    orders.findFirst();
    while (!orders.last()) {
        if (orders.retrieve().getoId() == orderID) {
            found = true;
            break;
        }
        orders.findNext();
    }
    if (orders.retrieve().getoId() == orderID)
        found = true;
        
    if (found) {
        if (orders.retrieve().getStatus().compareToIgnoreCase("cancelled") == 0) {
            System.out.println("Cannot update status for cancelled orders");
            return false;
        }
        else {
            Order obj = orders.retrieve();
            System.out.println("Status of order is " + orders.retrieve().getStatus());
            System.out.print("Please enter new status (pending/shipped/delivered/cancelled): ");
            String str = input.next().toLowerCase();
            
            while (!str.equals("pending") && !str.equals("shipped") && 
                   !str.equals("delivered") && !str.equals("cancelled")) {
                System.out.print("Invalid status. Enter (pending/shipped/delivered/cancelled): ");
                str = input.next().toLowerCase();
            }
            
            orders.remove();
            obj.status = str;
            orders.insert(obj);
            return true;
        }
    }
    System.out.println("Invalid order ID");
    return false;
}
    //==================================================================    
    public Order searchOrderID(int orderID) {
        boolean found = false;

        orders.findFirst();
        while (!orders.last()) {
            if (orders.retrieve().getoId() == orderID) {
                found = true;
                break;
            }
            orders.findNext();
        }
        if (orders.retrieve().getoId() == orderID)
            found = true;

        if (found)
            return orders.retrieve();

        System.out.println("Invalid order ID");
        return null;
    }

    //==================================================================    
    public LinkedList<Order> BetweenTwoDates(String date1, String date2) {
        LinkedList<Order> ordersbetweenDates = new LinkedList<Order>();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate Ldate1 = LocalDate.parse(date1, formatter);
        LocalDate Ldate2 = LocalDate.parse(date2, formatter);
        
        if (!orders.empty()) {
            orders.findFirst();
            
            for (int i = 0; i < orders.size(); i++) {
                if (orders.retrieve().getDate().isAfter(Ldate1) && 
                    orders.retrieve().getDate().isBefore(Ldate2)) {
                    ordersbetweenDates.insert(orders.retrieve());
                    System.out.println(orders.retrieve());
                }
                orders.findNext();
            }
        }
        return ordersbetweenDates;
    }
    
    //==================================================================    
    public boolean Checkorderid(int oid) {
        if (orders.empty()) {
            return false;
        }
        
        orders.findFirst();
        int i = 0;
        while (i < orders.size()) {
            if (orders.retrieve().getoId() == oid) {
                orders.findFirst();
                return true;
            }
            if (!orders.last()) {
                orders.findNext();
            }
            i++;
        }
        
        orders.findFirst();
        return false;
    }
}