package app;

import entity.Balance;
import enums.Currency;
import scanner.ScannerSingleton;
import service.BalanceService;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Hazard on 17.05.2017.
 */
public class Main {

    public static  void main(String[] args){
        System.out.println("Commands:");
        System.out.println("add yyyy-mm-dd amount CUR product");
        System.out.println("clear yyyy-mm-dd");
        System.out.println("list");
        System.out.println("total CUR");
        System.out.println("exit ");
        BalanceService balanceService = new BalanceService();

        String command = "";
        do {
            System.out.println("Please enter command:\n");
            command = ScannerSingleton.getSc().nextLine();
            command = command.replaceAll("\\s+"," ");
            System.out.println(command);
            if(command.startsWith("add")){
                String[] params = command.split(" ");
                if(params.length==5) {
                    balanceService.add(params[1], params[2], params[3], params[4]);
                }
            }else if(command.startsWith("clear")){
                String[] params = command.split(" ");
                balanceService.delete(params[1]);
            }else if (command.startsWith("total")) {
                String[] params = command.split(" ");
                double res = balanceService.total(Currency.valueOf(params[1]));
                NumberFormat formatter = new DecimalFormat("#0.00");
                System.out.println("Total spends: " + formatter.format(res) + " " + params[1]);
            }else if (command.startsWith("list")){
                List<Balance> result = balanceService.list();
                for (Balance balance : result) {
                    System.out.println(balance.toString());
                }

            }
        }while (!command.equals("exit"));

    }
}
