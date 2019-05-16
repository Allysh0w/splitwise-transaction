package com.ally.handlers

import com.ally.contracts.TrxContract.ResultTrx
import com.ally.helper.DefinedTypes.Trx

trait HandlerTransactions {

  //receive the list of transactions and calc the dividend by each user using groupBy.
  // Total cost = O(n) => Linear time operation
  protected def transformTransaction[T <: Trx](transactions: List[T]) = {
    val aggregatedValueByUser = transactions.groupBy(_._1) // O(n) |//paralleled groupBy = O(n log n)
      .map(p => p._1 -> p._2.map(_._2)  // O(2n)
      .foldLeft(0.0)(_ + _))
      .toList // O(n)

    //processBalanceTrx(aggregatedValueByUser)
    aggregatedValueByUser
  }

  //Total cost O(n^2) | (Iterator = O(n) | maxBy: O(n)) => O(n^2)
  protected def processBalanceTrx[T <: Trx](listOfTrxToProcess: List[T]): List[ResultTrx] = {
    var listToProcess: List[Trx] = listOfTrxToProcess

    new Iterator[ResultTrx] { //O(n)
      override def hasNext: Boolean = listToProcess.nonEmpty

      override def next(): ResultTrx = {
        //value to discount
        val valueTrx = math.abs(math.abs(listToProcess.maxBy(_._2)._2) // search the max value to each iteration O(n) linear Operation
          .max(math.abs(listToProcess.minBy(_._2)._2)))

        //(e.g: (u1,u2,10) => u1 = user1 | u2 = user2 | 10 = value to pay
        val discountUserTrx1 = (listToProcess.maxBy(_._2)._1,
          listToProcess.maxBy(_._2)._2 - valueTrx) //calc trx for user1 => ._1 | by max value

        val discountUserTrx2 = (listToProcess.minBy(_._2)._1,
          listToProcess.minBy(_._2)._2 + valueTrx) //calc trx for user1 => ._1 () | by min value

        val TrxListToProcess = discountUserTrx1 +:
          discountUserTrx2 +:
          listToProcess.filter(user => user._1 != listToProcess.maxBy(_._2)._1 && user._1 != listToProcess.minBy(_._2)._1) //remove users already processed

        listToProcess = TrxListToProcess.filter(x => x._2 != 0.0) // filter elements != 0.0

        ResultTrx(discountUserTrx2._1,
          discountUserTrx1._1, valueTrx) //append Trx to list of Iterator
      }
    }.toList
  }

}
