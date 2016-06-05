package com.yadu.core

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by yadu on 5/6/16.
  */

object CustomImplicits {

  implicit class FutureImplicit(future: Future[_]) {
    def recoverFn = {
      future.recover {
        case ex => ex.printStackTrace()
      }
    }
  }

}
