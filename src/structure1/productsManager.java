
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

   
        public void addProduct() {// 14 Nov
        System.out.println("Enter product ID:");
        int pId = input.nextInt();
        while (checkProductID(pId)) {
            System.out.println("This product ID is already taken. Please enter another one:");
            pId = input.nextInt();
        }

        System.out.println("Enter product name:");
        String name = input.next();

        // ---------- price ----------
        double price = 0;
        boolean validPrice = false;

        while (!validPrice) {
            System.out.println("Price:");
            try {
                price = input.nextDouble();
                if (price < 0) {
                    System.out.println("Invalid price. Please enter a non-negative value:");
                } else {
                    validPrice = true;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid price. Please enter a numeric value:");
                input.nextLine();
            }
        }

        // ---------- stock ----------
        int stock = 0;
        boolean validStock = false;

        while (!validStock) {
            System.out.println("Stock:");
            try {
                stock = input.nextInt();
                if (stock < 0) {
                    System.out.println("Invalid stock value. Please enter a non-negative number:");
                } else {
                    validStock = true;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid stock value. Please enter a numeric value:");
                input.nextLine();
            }
        }

        Product product = new Product(pId, name, price, stock);
        products.findFirst();
        products.insert(product);
        System.out.println("Product '" + name + "' with ID " + pId + " has been added successfully.");
    }

   

    
    public Product searchProducID() {
        if (products.empty()) {
            System.out.println("Empty Products data");
            
        } else {
            System.out.println("Enter product ID: ");
            int productID = input.nextInt();

            boolean found = false;

            products.findFirst();
            while (!products.last()) {
                if (products.retrieve().getProductId() == productID) {
                    found = true;
                    break;
                }
                products.findNext();
            }
            if (products.retrieve().getProductId() == productID)
                found = true;

            if (found)
                return products.retrieve();
        }
        System.out.println("No such product ID");
        return null;
    }
    
    public Product searchProducName()
{
    if (products.empty()) {
        System.out.println("empty Products data");
    } else {
        System.out.println("Enter product Name: ");
        String name = input.nextLine();
        //name = input.nextLine(); //
        
        boolean found = false;
        
        products.findFirst();
        while (!products.last()) {
            if (products.retrieve().getName().compareToIgnoreCase(name) == 0) {
                found = true;
                break;
            }
            products.findNext();
        }

        if (products.retrieve().getName().compareToIgnoreCase(name) == 0)
            found = true;

        if (found)
            return products.retrieve();
    }

    System.out.println("No such product Name");
    return null;
}


      
    public Product removeProduct() {
        if (products.empty()) {
            System.out.println("empty Products data");
        } else {
            System.out.println("Enter product ID: ");
            int productID = input.nextInt();

            boolean found = false;

            products.findFirst();
            while (!products.last()) {
                if (products.retrieve().getProductId() == productID) {
                    found = true;
                    break;
                }
                products.findNext();
            }
            if (products.retrieve().getProductId() == productID)
                found = true;

            if (found) {
                Product p = products.retrieve();
                products.remove();
                p.setStock(0);
                products.insert(p);
                System.out.println("Product with ID " + productID + " has been removed successfully.");
                return p;
            }
        }
        System.out.println("No such product ID");
        return null;
    }

       
    public Product updateProduct() {
    if (products.empty()) {
        System.out.println("empty Products data");
        return null;
    }

    System.out.println("Enter product ID: ");
    int productID = input.nextInt();

    boolean found = false;

    products.findFirst();
    while (true) {
        if (products.retrieve().getProductId() == productID) {
            found = true;
            break;
        }

        if (products.last()) {
            break;
        }

        products.findNext();
    }

    if (!found) {
        System.out.println("No such product ID found");
        return null;
    }

 
    Product p = products.retrieve();
    products.remove();

    System.out.println("1. Update Name");
    System.out.println("2. Update price");
    System.out.println("3. Update stock");
    System.out.println("Enter your choice");
    int choice = input.nextInt();

    switch (choice) {
        case 1:
            System.out.println("Enter new product name:");
            p.setName(input.next());
            break;

        case 2:
            System.out.println("Enter new product price:");
            double newPrice = input.nextDouble();
            while (newPrice < 0) {
                System.out.println("Price cannot be negative. Please enter a non-negative value:");
                newPrice = input.nextDouble();
            }
            p.setPrice(newPrice);
            break;

        case 3:
            System.out.println("Enter new stock quantity:");
            int newStock = input.nextInt();
            while (newStock < 0) {
                System.out.println("Stock cannot be negative. Please enter a valid value:");
                newStock = input.nextInt();
            }
            p.setStock(newStock);
            break;

        default:
            System.out.println("Bad Choice");
    }

    products.insert(p);
    System.out.println("Product with ID " + productID + " has been updated successfully.");
    return p;
}


      
      
    
    public void Out_Of_Stock_Products() {
    if (products.empty()) {
        System.out.println("empty Products data");
    } else {
        boolean foundOutOfStock = false; 
        products.findFirst();
        for (int i = 0; i < products.size(); i++) {
            if (products.retrieve().getStock() == 0) {
                System.out.println(products.retrieve());
                foundOutOfStock = true; 
            }
            products.findNext();
        }
        
        
        if (!foundOutOfStock) {
            System.out.println("No products are out of stock.");
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