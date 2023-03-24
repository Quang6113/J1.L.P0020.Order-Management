package utils;

import entities.Customers;
import entities.Orders;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import services.CustomerServices;
import services.OrderServices;

public class Validation {

    public static Scanner sc = new Scanner(System.in);
    public static DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    public static boolean checkPhone(String phone) {
        return phone.matches("\\d{10,12}");
    }

    public static boolean checkCustomerIdAnyMatch(String id, List<Customers> list) {
        return list.stream().anyMatch((e) -> (e.getCustomerID().equals(id)));
    }

    public static boolean checkOrderIdAnyMatch(String id, List<Orders> list) {
        return list.stream().anyMatch((e) -> (e.getOrderID().equals(id)));
    }

    public static boolean checkCustomerID(String customerID) {
        return customerID.matches("C\\d{3}");
    }

    public static boolean checkOrderID(String orderID) {
        return orderID.matches("D\\d{3}");
    }
    
    public static boolean hasNumber(String s) {
        return s.matches(".*\\d+.*");
    }

    public static boolean containsSpecialCharacters(String s) {
        return s.matches("[A-Za-z0-9]+");
    }

    public static <T> T getInput(T input) {
        boolean cont = true;
        do {
            try {
                String inputString = sc.nextLine();

                if (input instanceof Integer) {
                    Integer intOut = Integer.parseInt(inputString);
                    return (T) intOut;
                }
                if (input instanceof Double) {
                    Double doubleOut = Double.parseDouble(inputString);
                    return (T) doubleOut;
                }
                if (input instanceof String) {
                    String stringOut = inputString;
                    return (T) stringOut;
                }
            } catch (NumberFormatException ne) {
                System.err.print("Wrong format, enter again: ");
            } catch (Exception e) {
                System.out.print("Enter again: ");
            }
        } while (cont);

        return input;
    }

    public static <T> T getUpdateInput(T input) {
        boolean cont = true;
        do {
            try {
                String inputString = sc.nextLine();
                if (inputString.isEmpty()) {
                    return input;
                }
                if (input instanceof Integer) {
                    Integer intOut = Integer.parseInt(inputString);
                    return (T) intOut;
                }
                if (input instanceof Double) {
                    Double doubleOut = Double.parseDouble(inputString);
                    return (T) doubleOut;
                }
                if (input instanceof String) {
                    String stringOut = inputString;
                    return (T) stringOut;
                }
            } catch (NumberFormatException ne) {
                System.err.print("Wrong format, enter again: ");
            } catch (Exception e) {
                System.out.print("Enter again: ");
            }
        } while (cont);

        return input;
    }

    public static LocalDate getDate() {
        while (true) {
            try {
                String input = sc.nextLine();
                dateFormat.setLenient(false);
                Date date = dateFormat.parse(input);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                if (year < 1900 || year > 9999) {
                    throw new ParseException("Year must be between 1900 and 9999.", 0);
                }
                if (month < 1 || month > 12) {
                    throw new ParseException("Month must be between 1 and 12.", 3);
                }
                if (day < 1 || day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    throw new ParseException("Day must be between 1 and " + cal.getActualMaximum(Calendar.DAY_OF_MONTH) + ".", 6);
                }
                return LocalDate.of(year, month, day);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                System.err.println("Invalid date format. Please enter a valid date in m/d/yyyy format.");
                System.out.print("Enter again: ");
            }
        }
    }

    public static LocalDate getUpdateDate(LocalDate objDate) {
        while (true) {
            try {
                String input = sc.nextLine();
                if (input.isEmpty()) {
                    return objDate;
                }
                dateFormat.setLenient(false);
                Date date = dateFormat.parse(input);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                if (year < 1900 || year > 9999) {
                    throw new ParseException("Year must be between 1900 and 9999.", 0);
                }
                if (month < 1 || month > 12) {
                    throw new ParseException("Month must be between 1 and 12.", 3);
                }
                if (day < 1 || day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    throw new ParseException("Day must be between 1 and " + cal.getActualMaximum(Calendar.DAY_OF_MONTH) + ".", 6);
                }
                return LocalDate.of(year, month, day);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                System.err.println("Invalid date format. Please enter a valid date in m/d/yyyy format.");
                System.out.print("Enter again: ");
            }
        }
    }

    public static int getUserChoice(int firstOpt, int lastOpt) {
        while (true) {
            try {
                int userChoice = Byte.parseByte(sc.nextLine());
                if (userChoice < firstOpt || userChoice > lastOpt) {
                    throw new Exception();
                }
                return userChoice;
            } catch (Exception e) {
                System.err.print("Please choose the correct option above: ");
            }
        }
    }

    public static int backToMainMenu(int mainChoice, boolean checkSave) {
        System.out.println("\nDo you want to go back to the main menu?");
        System.out.print("Your choice (1. Yes || 0. No): ");
        int choice = getUserChoice(0, 1);
        if (choice == 0) {
            if (checkSave == false) {
                saveBeforeLeaving();
                return 0;
            } else {
                return 0;
            }
        } else {
            return mainChoice;
        }
    }

    public static boolean getUserConfirmation(String information) {
        while (true) {
            System.out.print(information + " (Y/N): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }

    public static void saveBeforeLeaving() {
        System.out.println("\nWant to save to the file before leaving?");
        System.out.println("If you choose \"No\", your changes will not be saved");
        System.out.print("Your choice (1. Yes || 0. No): ");
        int subChoice = getUserChoice(0, 1);
        if (subChoice == 1) {
            CustomerServices.getInstance().saveCustomersToTheFile();
            OrderServices.getInstance().saveOrdersToTheFile();
        }
    }
}
