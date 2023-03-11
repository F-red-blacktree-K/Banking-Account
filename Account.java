import java.util.*;

import java.util.Calendar;

class BankingException extends Exception 
{
    BankingException () { super(); }
    BankingException (String s) { super(s); }
} 

interface BasicAccount 
{
    String name();
    double balance();	
}

interface WithdrawableAccount extends BasicAccount 
{
    double withdraw(double amount) throws BankingException;	
}

interface DepositableAccount extends BasicAccount 
{
    double deposit(double amount) throws BankingException;	
}

interface InterestableAccount extends BasicAccount 
{
    double computeInterest() throws BankingException;	
}

interface FullFunctionalAccount extends WithdrawableAccount,DepositableAccount,InterestableAccount 
{   

}

public abstract class Account 
{
	
    // protected variables to store commom attributes for every bank accounts	
    protected String accountName;
    protected double accountBalance;
    protected double accountInterestRate;
    protected Date openDate;
    protected Date lastInterestDate;
    
    //Account constructor


    Account(String name, double firstDeposit)
    {
        this(name, firstDeposit, new Date());
    }

    

    Account(String name, double firstDeposit, Date firstDate) 
    {
        accountName = name;
        accountBalance = firstDeposit;
        openDate = firstDate;
        lastInterestDate = openDate;
        accountInterestRate = 0.12;
    }


    // public methods for every bank accounts
    public String name() 
    {
    	return(accountName);	
    }	
    
    public double balance() 
    {
        return(accountBalance);
    }
    
    public double deposit(double amount) throws BankingException
    {
        accountBalance += amount;
        return(accountBalance);
    } 
    public double deposit(double amount, Date depositdate) throws BankingException
    {
        accountBalance += amount;
        return(accountBalance);
    } 
    

    public double withdraw(double amount) throws BankingException 
    {

        Date withdrawDate = new Date();
        return(withdraw(amount, withdrawDate));
    }

    public double withdraw(double amount, Date withdrawDate) throws BankingException 
    {
        accountBalance -= amount;
        return accountBalance;
    }

    abstract double computeInterest(Date interestDate) throws BankingException;
    
    public double computeInterest() throws BankingException 
    {
        Date interestDate = new Date();
        return(computeInterest(interestDate));
    }
    
    
}

/*
 *  Derived class: CheckingAccount
 *
 *  Description:
 *      Interest is computed daily; there's no fee for
 *      withdraw; there is a minimum balance of $1000.
 */
                          
class CheckingAccount extends Account implements FullFunctionalAccount 
{

    CheckingAccount(String name, double firstDeposit) 
    {
        super(name, firstDeposit);
    }
    
    CheckingAccount(String name, double firstDeposit, Date firstDate) 
    {
        super(name, firstDeposit, firstDate);
    }

    @Override
    public double withdraw(double amount, Date withdrawDate) throws BankingException 
    {
        if(amount < 1)
        {
            throw new BankingException ("Please enter the correct amount");
        }
        // minimum balance is 1000, raise exception if violated
        if ((accountBalance  - amount) < 1000) 
        {
            throw new BankingException ("Underfraft from checking account name:" +
                                         accountName);
        }
        
        return super.withdraw(amount, withdrawDate);
                                    	
    }

    public double computeInterest (Date interestDate) throws BankingException 
    {
        if (interestDate.before(lastInterestDate)) 
        {
            throw new BankingException ("Invalid date to compute interest for account name" +
                                        accountName);                            	
        }
        
        int numberOfDays = (int) ((interestDate.getTime() - lastInterestDate.getTime())
                                   / 86400000.0);
        System.out.println("Number of days since last interest is " + numberOfDays);
        double interestEarned = (double) numberOfDays / 365.0 *
                                      accountInterestRate * accountBalance;
        System.out.println("Interest earned is " + interestEarned); 
        lastInterestDate = interestDate;
        accountBalance += interestEarned;
        return(accountBalance);                            
    }  	
    
}       


class SavingAccount extends Account implements FullFunctionalAccount
{
    private int remainingFreetimes = 3;
    private Date transactionDate = new Date();

    SavingAccount(String name, double firstDeposit) 
    {
        super(name, firstDeposit);
    }

    SavingAccount(String name, double firstDeposit, Date firstDate) 
    {
        super(name, firstDeposit, firstDate);
    }

    private boolean NewMonth(Date date)
    {
        Calendar today = Calendar.getInstance();
        Calendar previousTradingday = Calendar.getInstance();
        today.setTime(date);
        previousTradingday.setTime(transactionDate);
        
        if(today.get(Calendar.MONTH) + 1 == previousTradingday.get(Calendar.MONTH) + 1
        && today.get(Calendar.YEAR) + 1 == previousTradingday.get(Calendar.YEAR) + 1)
            return false;
        else
            return true;
    }

    @Override
    public double deposit(double amount) throws BankingException
    {
        if(amount < 1)
        {
            throw new BankingException ("Please enter the correct amount");
        }
        formalities(new Date());
        return super.deposit(amount);
    }

    @Override
    public double withdraw(double amount, Date withdrawDate) throws BankingException 
    {
        if(amount < 1)
        {
            throw new BankingException ("Please enter the correct amount");
        }

        formalities(withdrawDate);
        return super.withdraw(amount, withdrawDate);

    }

    private void formalities(Date date)
    {
        
        if(NewMonth(date))
        {
            remainingFreetimes = 3;
        }

        transactionDate = date;
        remainingFreetimes--;

        //handling fee
        if(remainingFreetimes < 1)
        {
            accountBalance -=1;
        }
    }

    public double computeInterest(Date interestDate) throws BankingException 
    {
		if (interestDate.before(lastInterestDate)) 
        {
			throw new BankingException("Invalid date to compute interest for account name" + 
                                        accountName);
		}

		int numberOfMonths = (int) ((interestDate.getTime() - lastInterestDate.getTime()) 
                                    / 86400000.0 / 30.0);
		System.out.println("Number of months since last interest is "
				+ numberOfMonths);
		double interestEarned = (double) numberOfMonths / 12.0
				* accountInterestRate * accountBalance;
		System.out.println("Interest earned is " + interestEarned);
		lastInterestDate = interestDate;
		accountBalance += interestEarned;
		return (accountBalance);
	}


}


class CDAccount extends Account implements FullFunctionalAccount
{
    
    CDAccount(String name, double firstDeposit) 
    {
        super(name, firstDeposit);
    }

    CDAccount(String name, double firstDeposit, Date firstDate) 
    {
        super(name, firstDeposit, firstDate);
    }

    @Override
    public double deposit(double amount, Date depositdate) throws BankingException 
    {
        if(amount < 1)
        {
            throw new BankingException ("Please enter the correct amount");
        }
        int accountOpentime = (int) ((depositdate.getTime() - openDate.getTime()) 
        / 86400000.0 / 30.0);

        if(accountOpentime > 12)
        {
            throw new BankingException("You cannot make deposits during the interest period.");
        }

        return super.deposit(amount);
    }

    @Override
    public double withdraw(double amount, Date withdrawDate)throws BankingException 
    {
        if(amount < 1)
        {
            throw new BankingException ("Please enter the correct amount");
        }
		int numberOfMonths = (int) ((withdrawDate.getTime() - openDate.getTime()) 
                                    / 86400000.0 / 30.0);
		
        double handlingFee = 0;
        if(numberOfMonths < 12)
            handlingFee = 250;
        else
            handlingFee = 0;
		
		if ((accountBalance - amount) < handlingFee) 
        {
			throw new BankingException("Underfraft from CD account name:"
					                    + accountName);
		} 
        else 
        {
			accountBalance -= (amount + handlingFee);
			return (accountBalance);
		}
	}



    @Override
    double computeInterest(Date interestDate) throws BankingException 
    {
        if (interestDate.before(lastInterestDate)) 
        {
			throw new BankingException("Invalid date to compute interest for account name"
							            + accountName);
		}

        int accountOpentime = (int) ((interestDate.getTime() - openDate.getTime()) 
                                    / 86400000.0 / 30.0);
  
        if(accountOpentime <= 12)
        {
            int numberOfMonths = (int) ((interestDate.getTime() - lastInterestDate.getTime()) 
                                    / 86400000.0 / 30.0);

		    System.out.println("Number of months since last interest is "+ numberOfMonths);

            double interestEarned = (double) numberOfMonths / 12.0
				* accountInterestRate * accountBalance;

		    System.out.println("Interest earned is " + interestEarned);

		    lastInterestDate = interestDate;
		    accountBalance += interestEarned;
        }
        else
        {
            System.out.println("Note that this account is no longer eligible for interest");
        }

        return (accountBalance);
    }


}


class LoanAccount extends Account implements DepositableAccount 
{

    LoanAccount(String name, double firstDeposit) 
    {
        super(name, firstDeposit);
    }

    LoanAccount(String name, double firstDeposit, Date firstDate) 
    {
        super(name, firstDeposit, firstDate);
    }

    @Override
    public double withdraw(double amount, Date withdrawDate) throws BankingException 
    {
        throw new BankingException("LoanAccount can't withdraw.");
    }

    public double deposit(double amount) throws BankingException 
    {
        if(amount < 1)
        {
            throw new BankingException ("Please enter the correct amount");
        }
        return super.deposit(amount);
    }

    
    public double computeInterest(Date interestDate) throws BankingException 
    {
		if (interestDate.before(lastInterestDate)) 
        {
			throw new BankingException("Invalid date to compute interest for account name" + 
                                        accountName);
		}

		int numberOfMonths = (int) ((interestDate.getTime() - lastInterestDate.getTime()) 
                                    / 86400000.0 / 30.0);
		System.out.println("Number of months since last interest is "
				+ numberOfMonths);
		double interestEarned = (double) numberOfMonths / 12.0
				* accountInterestRate * accountBalance;
		System.out.println("Interest earned is " + interestEarned);
		lastInterestDate = interestDate;
		accountBalance += interestEarned;
		return (accountBalance);
	}
    

}