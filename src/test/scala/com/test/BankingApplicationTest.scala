package com.test
import com.knoldus.{BankingApplication, Transactions}
import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Random

class BankingApplicationTest extends AnyFlatSpec {

  val bankingApplication = new BankingApplication

  // creating some accounts
  bankingApplication.createAccount(1000.0)
  bankingApplication.createAccount(2000.0)
  bankingApplication.createAccount(3000.0)
  bankingApplication.createAccount(4000.0)

  //list of accounts with account balance before transaction
  val accountDetailsBeforeTransaction = bankingApplication.listAccounts()

  //account numbers in List
  val accountNumbersBeforeTransaction = accountDetailsBeforeTransaction.keySet.toList

  //TEST CASES
  "Account balance " should " show after transactions " in {

      val balanceBeforeTransaction = bankingApplication.fetchAccountBalance(accountNumbersBeforeTransaction(0))

      //In case of Debit
      val transactions = Transactions(Random.nextInt().abs,accountNumbersBeforeTransaction(0),"debit",1000.0)
      val accountDetailsAfterTransaction = bankingApplication.updateBalance(List(transactions))
      val accountNumbersAfterTransaction: List[Long] =  accountDetailsAfterTransaction.keySet.toList
      val balanceAfterTransaction = bankingApplication.fetchAccountBalance(accountNumbersAfterTransaction(0))

      assert(balanceBeforeTransaction > balanceAfterTransaction)
    }

  "Test cases  " should " show  equal after transactions " in {

    val balanceBeforeTransaction = bankingApplication.fetchAccountBalance(accountNumbersBeforeTransaction(0))

    //In case of Debit
    val transactions = Transactions(Random.nextInt().abs, accountNumbersBeforeTransaction(0), "debit", 1000.0)
    val accountDetailsAfterTransaction = bankingApplication.updateBalance(List(transactions))
    val accountNumbersAfterTransaction: List[Long] = accountDetailsAfterTransaction.keySet.toList
    val balanceAfterTransaction = bankingApplication.fetchAccountBalance(accountNumbersAfterTransaction(0))

    assert(balanceBeforeTransaction == balanceAfterTransaction + 1000.0 )
  }

  "Account balance after transaction in case of credit " should " show  " in {

      val balanceBeforeTransaction = bankingApplication.fetchAccountBalance(accountNumbersBeforeTransaction(0))

      //In case of Credit
      val transactions = Transactions(Random.nextInt().abs,accountNumbersBeforeTransaction(0),"credit",10000.0)
      val accountDetailsAfterTransaction = bankingApplication.updateBalance(List(transactions))
      val accountNumbersAfterTransaction: List[Long] =  accountDetailsAfterTransaction.keySet.toList
      val balanceAfterTransaction = bankingApplication.fetchAccountBalance(accountNumbersAfterTransaction(0))

      assert(balanceBeforeTransaction < balanceAfterTransaction)
    }

  "deletion of account " should "be successful " in {

    assert(bankingApplication.deleteAccount(accountNumbersBeforeTransaction(2)) == "Deleted!")
  }


}
