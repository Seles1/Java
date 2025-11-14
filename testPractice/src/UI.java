import Domain.BMI;
import Domain.BP;
import Domain.MedicalAnalysis;
import Service.Service;

import java.util.Scanner;

public class UI {
    private Service service=new Service();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        Boolean running = true;
        while (running) {
            System.out.println("1.add");
            System.out.println("2.view");
            System.out.println("3.filterByYear");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addAnalysis();
                    break;
                case "2":
                    viewAnalysises();
                    break;
                case "3":
                    filterByYear();
                    break;
            }
        }
    }

    public void addAnalysis() {
        MedicalAnalysis n;
        int id = Integer.parseInt(scanner.nextLine());
        String date = scanner.nextLine();
        String status = scanner.nextLine();
        String type = scanner.nextLine();
        if (type.equals("BMI")) {
            float bmi = Float.parseFloat(scanner.nextLine());
            n = new BMI(id, date, status, bmi);
        } else {
            int systolic = Integer.parseInt(scanner.nextLine());
            int diastolic = Integer.parseInt(scanner.nextLine());
            n = new BP(id, date, status, systolic, diastolic);
        }
        service.add(n);
    }

    public void viewAnalysises(){
        System.out.println(service.toString());
    }

    public void filterByYear(){
        String year=scanner.nextLine();
        System.out.println(service.filterByYear(year));
    }
}
