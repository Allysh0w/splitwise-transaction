package com.ally.contracts

object TrxContract {
  case class ResultTrx(fromUser: String, toUser: String, value: Double)
}
