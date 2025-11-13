package structure1;

import java.io.File;
import java.util.Scanner;

public class reviewsManager {

    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Review> reviews = new LinkedList<Review> ();

    //==============================================================
    public LinkedList<Review> getAllReviews() {
        return reviews;
    }

    //==============================================================
    public reviewsManager(String fileName) {
        try {
            File file = new File(fileName);
            Scanner read = new Scanner(file);
            String line = read.nextLine();

           
            

              while(read.hasNext())
                {
                    line = read.nextLine();
                    String [] data = line.split(","); 
                    int rID = Integer.parseInt(data[0]);
                    int pID = Integer.parseInt(data[1]);
                    int cID = Integer.parseInt(data[2]);
                    int rating =  Integer.parseInt(data[3]);
                    String comment =  data[4];

                
                Review newReview = new Review(rID, pID, cID, rating, comment);
                reviews.insert(newReview);
            }
            read.close();

        } catch (Exception ex) {
            System.out.println("Error loading reviews: " + ex.getMessage());
        }
    }

    //==============================================================
    public Review addNewReview(int customerNum, int productNum) {//1
        System.out.println("Enter a new Review ID:");//2
        int newID = input.nextInt();//3

        while (checkReviewID(newID)) {//4
            System.out.println("This ID already exists. Try another one:");//5
            newID = input.nextInt();//6
        }

        System.out.println("Rate the product (1 to 5):");//7
        int rate = input.nextInt(); //8

        System.out.println("Write your comment:");//9
        input.nextLine(); //10
        String comment = input.nextLine();//11

        
        Review r = new Review(newID, productNum, customerNum, rate, comment);//12

        reviews.findFirst();//13
        reviews.insert(r);//14

        System.out.println("Review added successfully!");//15
        return r;//16
    }

    //==============================================================
    public void updateReview() {
        if (reviews.empty()) {//1
            System.out.println("No reviews available to edit.");//2
            return;//3
        }

        System.out.println("Enter the Review ID you want to modify:");//4
        int targetID = input.nextInt();//5

        while (!checkReviewID(targetID)) {//6
            System.out.println("Review not found! Enter a valid ID:");//7
            targetID = input.nextInt();//8
        }

        reviews.findFirst();//9
        while (!reviews.last()) {//10
            if (reviews.retrieve().getReviewId() == targetID) break;//11
            reviews.findNext();//12
        }

        if (reviews.retrieve().getReviewId() == targetID) {//13
            Review chosen = reviews.retrieve();//14
            reviews.remove();//15

            System.out.println("Choose what you want to update:");//16
            System.out.println("1. Change Rating");//17
            System.out.println("2. Edit Comment");//18
            int option = input.nextInt();//19

            switch (option) {//20
                case 1: {//21
                    System.out.println("Enter a new rating (1 to 5):");//22
                    int newRating = input.nextInt();   //23
                    chosen.setRating(newRating);      //24 
                } break;//25

                case 2: {//26
                    System.out.println("Enter your new comment:");//27
                    input.nextLine();//28
                    String newComment = input.nextLine();//29
                    chosen.setComment(newComment);//30
                } break;//31

                default://32
                    System.out.println("Invalid choice! No changes applied.");//33
            }

            reviews.insert(chosen);//34
            System.out.println("Review (" + chosen.getReviewId() + ") updated successfully:");//35
            System.out.println(chosen);//37
        }
    }

    //==============================================================
    public boolean checkReviewID(int id) {
        if (!reviews.empty()) {
            reviews.findFirst();
            for (int i = 0; i < reviews.size(); i++) {
                if (reviews.retrieve().getReviewId() == id)
                    return true;
                reviews.findNext();
            }
        }
        return false;
    }
}