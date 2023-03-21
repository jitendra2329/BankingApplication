package com.knoldus
import scala.collection.mutable
import scala.util.Random

case class Transactions(transactionId: Long, accountNumber: Long, transactionType: String, amount: Double)

class BankingApplication {
  private val listOfAccountDetails: mutable.Map[Long, Double] = mutable.Map()

  //Creating new Account
  def createAccount(openingBalance: Double): mutable.Map[Long, Double] = {
    val accountNumber = Random.nextLong().abs
    listOfAccountDetails += (accountNumber -> openingBalance)
    listOfAccountDetails
  }

  // List all accounts with balance
  def listAccounts(): mutable.Map[Long, Double] = {
    listOfAccountDetails
  }

  // Fetch account balance using account number
  def fetchAccountBalance(accountNumber: Long): Double = {
    listOfAccountDetails.getOrElse(accountNumber, 0)
  }

  // Update account balance based on list of transactions
  def updateBalance(transactions: List[Transactions]): mutable.Map[Long, Double] = {
    transactions.map { transaction =>
      val currentBalance = fetchAccountBalance(transaction.accountNumber)
      val updatedBalance = transaction.transactionType match {
        case "credit" => currentBalance + transaction.amount
        case "debit" => if (currentBalance >= transaction.amount) {
                            currentBalance - transaction.amount
                        } else throw new IllegalArgumentException("Insufficient balance")
        case _ => throw new IllegalArgumentException("Invalid transaction")
      }
      listOfAccountDetails += (transaction.accountNumber -> updatedBalance)
    }
    listOfAccountDetails
  }

  // Delete account using account number
  def deleteAccount(accountNumber: Long): String = {
    if (listOfAccountDetails.contains(accountNumber)) {
      listOfAccountDetails -= accountNumber
      "Deleted!"
    } else {
      "Account does not exist!"
    }
  }

}
