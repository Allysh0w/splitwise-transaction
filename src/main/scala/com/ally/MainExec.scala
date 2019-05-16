package com.ally

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.RunnableGraph
import com.ally.contracts.TrxContract.ResultTrx
import com.ally.handlers.GraphHandler

object MainExec extends App with GraphHandler {

  implicit val system = ActorSystem()
 implicit val ec = system.dispatcher
  implicit val mat = ActorMaterializer()
  val listInput = List("u1" -> -10.0, "u2" -> 10.0, "u2" -> -3.0, "u3" -> 3.0, "u2" -> -1.0, "u1" -> 1.0)

  val results = RunnableGraph.fromGraph(buildGraph(listInput))
    .run()

 val t = System.currentTimeMillis()
 results.map{resp =>
  if (resp == List(ResultTrx("u1", "u2", 9.0), ResultTrx("u2", "u3", 3.0))) {
   println(resp)
   println("OK")
   println(System.currentTimeMillis() - t)
  }else
   throw new Exception("Assertion validation failed.")
 }


 system.terminate()


//  // Test
//  println(transformTransaction(listInput))




}
