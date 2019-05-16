package com.ally.handlers

import akka.stream.{ClosedShape, Graph}
import akka.stream.scaladsl.{Flow, GraphDSL, Keep, Sink, Source}
import com.ally.contracts.TrxContract.ResultTrx
import com.ally.helper.DefinedTypes.Trx

import scala.concurrent.Future

trait GraphHandler extends HandlerTransactions {

  protected def buildGraph[T <: Trx](inputList: List[T]): Graph[ClosedShape.type, Future[List[ResultTrx]]] = {

    GraphDSL.create(Sink.head[List[ResultTrx]]) { implicit builder =>sink =>

      import GraphDSL.Implicits._

      val flowTransfor = Flow.fromFunction(transformTransaction)
      val flowProcessTrx = Flow.fromFunction(processBalanceTrx)

      Source.single(inputList)  ~> flowTransfor ~> flowProcessTrx ~> sink.s

      ClosedShape
    }
  }

}
