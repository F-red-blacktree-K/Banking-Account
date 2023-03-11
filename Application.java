/*****************************************************************
    CS4001301 Programming Languages                   
    
    Programming Assignment #1
    
    Java programming using subtype, subclass, and exception handling
    
    To compile: %> javac Application.java
    
    To execute: %> java Application
******************************************************************/

import java.util.*;

public class Application {

    public static void main(String args[]) {
        Account a;
        Date d;
        double ret;
        
        /* put your own tests here ....... */
        System.out.println("Basic Test:");
        System.out.println("-----------------------------------------------------------\n");

        Account[] AccountsList;
        AccountsList = new Account[4];
         
        // buid 4 different accounts in the same array
        AccountsList[0] = new CheckingAccount("John Smith", 1500.0);
        AccountsList[1] = new SavingAccount("William Hurt", 1200.0);
        AccountsList[2] = new CDAccount("Woody Allison", 1000.0);
        AccountsList[3] = new LoanAccount("Judi Foster", -1500.0);
        
        for(int i = 0; i < 4;i++)
        {
            System.out.println("Account name: " + AccountsList[i].name() + "Account balance: " + AccountsList[i].balance());
            System.out.println("\n");
        }
        System.out.println("-----------------------------------------------------------\n");
       


        //CheckingAccount test
        
        System.out.println("CheckingAccount Test:");
        System.out.println("-----------------------------------------------------------\n");
        a = new CheckingAccount("John Smith", 1500.0);
    
        try {
            ret = a.withdraw(100.00);
            System.out.println ("Account <" + a.name() + "> now has $" + ret + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, a.balance());	
        }
    
        a = new CheckingAccount("John Smith", 1500.0);
    
        try {
            ret = a.withdraw(600.00);
            System.out.println ("Account <" + a.name() + "> now has $" + ret + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, a.balance());	
        }

        try {
            ret = a.deposit(1000.00);
            System.out.println ("Account <" + a.name() + "> now has $" + ret + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, a.balance());	
        }

        a = new CheckingAccount("CheckingAccount_A", 1500.0);

        try {
            ret = a.withdraw(800.0);
            System.out.println ("Account <" + a.name() + "> now has $" + ret + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, a.balance());	
        }

        System.out.println("-----------------------------------------------------------\n");



         //SavingAccount test
          System.out.println("SavingAccount Test:");
          System.out.println("-----------------------------------------------------------\n");

          a = new SavingAccount("SavingAccount_A", 8000);

          try {

			for(int i = 0; i < 3; i++)
            {
                a.withdraw(100);
            }
            for(int i = 0; i < 3; i++)
            {
                a.deposit(100);
            }
			System.out.println("Account <" + a.name() + "> now has $"
					+ a.balance() + " balance\n");
		  } catch (Exception e) {
			    stdExceptionPrinting(e, a.balance());
		  }

        System.out.println("-----------------------------------------------------------\n");


        //LoanAccount test
        System.out.println("LoanAccount Test:");
        System.out.println("-----------------------------------------------------------\n");

        a = new LoanAccount("LoanAccount_A", -1000);
		try {
            for(int i = 0; i < 5; i++)
            {
                a.deposit(100);
            }
			System.out.println("Account <" + a.name() + "> now has $"
					+ a.balance() + " balance\n");
			a.deposit(100);
		} catch (Exception e) {
			stdExceptionPrinting(e, a.balance());
		}
        System.out.println("-----------------------------------------------------------\n");


        //CDAccount test
        System.out.println("CDAccount Test:");
        System.out.println("-----------------------------------------------------------\n");
 
		a = new CDAccount("CDAccount_A", 5000);
		try {

            for(int i = 0; i < 3; i++)
            {
                a.withdraw(1000); 
            }
			System.out.println("Account <" + a.name() + "> now has $"
					+ a.balance() + " balance\n");

		} catch (Exception e) {
			stdExceptionPrinting(e, a.balance());
		}
        System.out.println("-----------------------------------------------------------\n");


        
        // compute interest for all accounts
        System.out.println("compute interest Test:");
        System.out.println("-----------------------------------------------------------\n");
		for (int count = 0; count < AccountsList.length; count++) {
			double newBalance;
			try {
				newBalance = AccountsList[count].computeInterest();
				System.out.println("Account <" + AccountsList[count].name()
						+ "> now has $" + newBalance + " balance\n");
			} catch (Exception e) {
				stdExceptionPrinting(e, AccountsList[count].balance());
			}
		}
        System.out.println("-----------------------------------------------------------\n");


        // compute past interest for all accounts
        System.out.println("compute past interest Test:");
        System.out.println("-----------------------------------------------------------\n");
        
		for (int count = 0; count < AccountsList.length; count++) {
			double newBalance;
			d = new Date(50);
			try {
				newBalance = AccountsList[count].computeInterest(d); // Exception
				System.out.println("Account <" + AccountsList[count].name()
						+ "> now has $" + newBalance + " balance\n");
			} catch (Exception e) {
				stdExceptionPrinting(e, AccountsList[count].balance());
			}
		}
        System.out.println("-----------------------------------------------------------\n");

        //wrong amount test
        System.out.println("wrong amount Test:");
        System.out.println("-----------------------------------------------------------\n");


        a = new CheckingAccount("CheckingAccount_B", 2000.0);

        try {
            ret = a.withdraw(-1);
            System.out.println ("Account <" + a.name() + "> now has $" + ret + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e);	
        }


        a = new SavingAccount("SavingAccount_B", 2000);

        try {
            ret = a.deposit(-1);
            System.out.println ("Account <" + a.name() + "> now has $" + ret + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e);	
        }

        System.out.println("-----------------------------------------------------------\n");


    }

   
    
    static void stdExceptionPrinting(Exception e, double balance) {
        System.out.println("EXCEPTION: Banking system throws a " + e.getClass() +
                " with message: \n\t" +
                "MESSAGE: " + e.getMessage());
        System.out.println("\tAccount balance remains $" + balance + "\n");
    }

    static void stdExceptionPrinting(Exception e) {
        System.out.println("EXCEPTION: Banking system throws a " + e.getClass() +
                " with message: \n\t" +
                "MESSAGE: " + e.getMessage());
    }
    
}