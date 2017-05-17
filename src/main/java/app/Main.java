package app;

import scanner.ScannerSingleton;
import service.BalanceService;

/**
 * Created by Hazard on 17.05.2017.
 */
public class Main {

    public static  void main(String[] args){
        System.out.println("Commands:");
        System.out.println("add ");
        System.out.println("clear ");
        System.out.println("list");
        System.out.println("total ");
        System.out.println("exit ");
        BalanceService balanceService = new BalanceService();

        String command = "";
        do {
            System.out.println("Please enter command:\n");
            command = ScannerSingleton.getSc().next();
            if(command.startsWith("add")){
                String[] params = command.split(" ");
                balanceService.add(params[1],params[2],params[3],params[4]);
            }else if(command.startsWith("clear")){
                String[] params = command.split(" ");
                balanceService.delete(params[1]);
            }else if (command.startsWith("list")){
                balanceService.total(null);
            }
        }while (!command.equals("exit"));

    }
}
