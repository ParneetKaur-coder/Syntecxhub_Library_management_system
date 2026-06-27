import java.util.*;
import java.io.*;

public class LibraryManagement {

    static ArrayList<Book> books = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "library.txt";

    public static void main(String[] args) {

        loadBooks();

        int choice;

        do {
            displayMenu();

            choice = getIntegerInput();

            switch (choice) {

                case 1:
                    addBook();
                    break;

                case 2:
                    removeBook();
                    break;

                case 3:
                    searchBook();
                    break;

                case 4:
                    displayBooks();
                    break;

                case 5:
                    saveBooks();
                    System.out.println("\nData saved successfully.");
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("\nInvalid Choice.");
            }

        } while (choice != 5);
    }

    public static void displayMenu() {

        System.out.println("\n===============================");
        System.out.println(" LIBRARY MANAGEMENT SYSTEM");
        System.out.println("===============================");
        System.out.println("1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. Search Book");
        System.out.println("4. Display All Books");
        System.out.println("5. Exit");
        System.out.print("Enter Choice : ");
    }

    public static int getIntegerInput() {

        while (true) {
            try {
                int number = Integer.parseInt(sc.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.print("Please enter numbers only: ");
            }
        }
    }

    public static void addBook() {

        try {

            System.out.print("Enter Book ID : ");
            int id = getIntegerInput();

            for (Book b : books) {
                if (b.getId() == id) {
                    System.out.println("Book ID already exists.");
                    return;
                }
            }

            System.out.print("Enter Book Title : ");
            String title = sc.nextLine().trim();

            if (title.isEmpty()) {
                System.out.println("Title cannot be empty.");
                return;
            }

            System.out.print("Enter Author Name : ");
            String author = sc.nextLine().trim();

            if (author.isEmpty()) {
                System.out.println("Author cannot be empty.");
                return;
            }

            Book newBook = new Book(id, title, author);
            books.add(newBook);

            System.out.println("\nBook Added Successfully.");

        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    public static void removeBook() {

        if (books.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }

        System.out.print("Enter Book ID to remove : ");
        int id = getIntegerInput();

        Iterator<Book> iterator = books.iterator();

        while (iterator.hasNext()) {

            Book b = iterator.next();

            if (b.getId() == id) {
                iterator.remove();
                System.out.println("Book Removed Successfully.");
                return;
            }
        }

        System.out.println("Book Not Found.");
    }

    public static void searchBook() {

        if (books.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }

        System.out.print("Enter Book Title : ");
        String title = sc.nextLine();

        boolean found = false;

        for (Book b : books) {

            if (b.getTitle().equalsIgnoreCase(title)) {

                System.out.println("\nBook Found");
                System.out.println("----------------");
                System.out.println(b);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Book Not Found.");
        }
    }

    public static void displayBooks() {

        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        System.out.println("\n========= BOOK LIST =========");

        for (Book b : books) {
            System.out.println(b);
            System.out.println("----------------------------");
        }
    }

    public static void saveBooks() {

        try {

            BufferedWriter bw =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME));

            for (Book b : books) {

                bw.write(
                        b.getId() + "," +
                        b.getTitle() + "," +
                        b.getAuthor()
                );

                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {
            System.out.println("Error while saving file.");
        }
    }

    public static void loadBooks() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(FILE_NAME));

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String title = data[1];
                String author = data[2];

                books.add(
                        new Book(id, title, author)
                );
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}
