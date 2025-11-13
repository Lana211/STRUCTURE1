
package structure1;

import java.io.File;
import java.util.Scanner;

public class productsManager {
    
    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Product> products = new LinkedList<Product>();

    
    public LinkedList<Product> getProducts() {
        return products;
    }

    
    public productsManager(String fileName) {
        try {
            File docsfile = new File(fileName);
            Scanner reader = new Scanner(docsfile);
            String line = reader.nextLine();

            while (reader.hasNext()) {
                line = reader.nextLine();
                String[] data = line.split(",");

                int pid = Integer.parseInt(data[0]);
                String name = data[1].trim();
                double price = Double.parseDouble(data[2]);
                int stock = Integer.parseInt(data[3]);

                Product product = new Product(pid, name, price, stock);
                products.insert(product);
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

   
    public void addProduct() {
        System.out.println("Enter product ID:"); //1
        int pId = input.nextInt(); //2
        while (checkProductID(pId)) { //3
           System.out.println("This product ID is already taken. Please enter another one:");//4
            pId = input.nextInt();//5
        }

        System.out.println("Enter product Name:");//6
        String name = input.next();//7

        
        System.out.println("price:");//8
        double price = input.nextDouble();//9
        while (price < 0) {//10
           System.out.println("Invalid price. Please enter a non-negative value:");//11
            price = input.nextDouble();//12
        }

       
        System.out.println("stock:");//13
        int stock = input.nextInt();///14
        while (stock < 0) {//15
            System.out.println("Invalid stock value. Please enter a non-negative number:");//16
            stock = input.nextInt();//17
        }

        Product product = new Product(pId, name, price, stock);//18
        products.findFirst();//19
        products.insert(product);//20
        System.out.println("Product '" + name + "' with ID " + pId + " has been added successfully.");//21
    }

    
    public Product searchProducID() {
        if (products.empty()) {//1
            System.out.println("Empty Products data");//2
            
        } else {//3
            System.out.println("Enter product ID: ");//4
            int productID = input.nextInt();//5

            boolean found = false;//6

            products.findFirst();//7
            while (!products.last()) {//8
                if (products.retrieve().getProductId() == productID) {//9
                    found = true;//10
                    break;//11
                }
                products.findNext();//12
            }
            if (products.retrieve().getProductId() == productID)//13
                found = true;//14

            if (found)//15
                return products.retrieve();//16
        }
        System.out.println("No such product ID");//17
        return null;//18
    }
    
    public Product searchProducName(){
        
    if (products.empty()) {//1
        System.out.println("empty Products data");//2
    } else {//3
        System.out.println("Enter product Name: ");//4
        String name = input.nextLine();//5
        
        
        boolean found = false;//6
        
        products.findFirst();//7
        while (!products.last()) {//8
            if (products.retrieve().getName().compareToIgnoreCase(name) == 0) {//9
                found = true;//10
                break;//11
            }
            products.findNext();//12
        }

        if (products.retrieve().getName().compareToIgnoreCase(name) == 0)//13
            found = true;//14

        if (found)//15
            return products.retrieve();//16
    }

    System.out.println("No such product Name");//17
    return null;//18
}


      
    public Product removeProduct() {
        if (products.empty()) {//1
            System.out.println("empty Products data");//2
        } else {//3
            System.out.println("Enter product ID: ");//4
            int productID = input.nextInt();//5

            boolean found = false;//6

            products.findFirst();//7
            while (!products.last()) {//8
                if (products.retrieve().getProductId() == productID) {//9
                    found = true;//10
                    break;//11
                }
                products.findNext();//12
            }
            if (products.retrieve().getProductId() == productID)//13
                found = true;//14

            if (found) {//15
                Product p = products.retrieve();//16
                products.remove();//17
                p.setStock(0);//18
                products.insert(p);//19
                System.out.println("Product with ID " + productID + " has been removed successfully.");//20
                return p;//21
            }
        }
        System.out.println("No such product ID");//22
        return null;//23
    }

       
    public Product updateProduct() {
    if (products.empty()) {//1
        System.out.println("empty Products data");//2
        return null;//3
    }

    System.out.println("Enter product ID: ");//4
    int productID = input.nextInt();//5

    boolean found = false;//6

    products.findFirst();//7
    while (true) {//8
        if (products.retrieve().getProductId() == productID) {//9
            found = true;//10
            break;//11
        }

        if (products.last()) {//12
            break;//13
        }

        products.findNext();//14
    }

    if (!found) {//15
        System.out.println("No such product ID found");//16
        return null;//17
    }

 
    Product p = products.retrieve();//18
    products.remove();//19

    System.out.println("1. Update Name");//20
    System.out.println("2. Update price");//21
    System.out.println("3. Update stock");//22
    System.out.println("Enter your choice");//23
    int choice = input.nextInt();//24

    switch (choice) {//25
        case 1://26
            System.out.println("Enter new product name:");//27
            p.setName(input.next());//28
            break;//29

        case 2://30
            System.out.println("Enter new product price:");//31
            double newPrice = input.nextDouble();//32
            while (newPrice < 0) {//33
                System.out.println("Price cannot be negative. Please enter a non-negative value:");//34
                newPrice = input.nextDouble();//35
            }
            p.setPrice(newPrice);//36
            break;//37

        case 3://38
            System.out.println("Enter new stock quantity:");//39
            int newStock = input.nextInt();//40
            while (newStock < 0) {//41
                System.out.println("Stock cannot be negative. Please enter a valid value:");//42
                newStock = input.nextInt();//43
            }
            p.setStock(newStock);//44
            break;//45

        default://46
            System.out.println("Bad Choice");//47
    }

    products.insert(p);//48
    System.out.println("Product with ID " + productID + " has been updated successfully.");//48
    return p;//50
}


      
      
    
    public void Out_Of_Stock_Products() {
    if (products.empty()) {//1
        System.out.println("empty Products data");//2
    } else {//3
        boolean foundOutOfStock = false; //4
        products.findFirst();//5
        for (int i = 0; i < products.size(); i++) {//6
            if (products.retrieve().getStock() == 0) {//7
                System.out.println(products.retrieve());//8
                foundOutOfStock = true; //9
            }
            products.findNext();//10
        }
        
        
        if (!foundOutOfStock) {//11
            System.out.println("No products are out of stock.");//12
        }
    }
}

   

    //========================================================added method 
    public boolean checkProductID(int PID) {
        if (!products.empty()) {
            products.findFirst();
            for (int i = 0; i < products.size(); i++) {
                if (products.retrieve().getProductId() == PID)
                    return true;
                products.findNext();
            }
        }
        return false;
    }

    
    public Product getProducts(int PID) {
        if (!products.empty()) {
            products.findFirst();
            while (!products.last()) {
                if (products.retrieve().getProductId() == PID)
                    return products.retrieve();
                products.findNext();
            }
            if (products.retrieve().getProductId() == PID)
                return products.retrieve();
        }
        return null;
    }
}
///////updated