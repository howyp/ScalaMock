// Copyright (c) 2011 Paul Butcher
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.borachio

private[borachio] class OrderedExpectations extends Expectations with Handler {

  private[borachio] def handle(mock: MockFunction, arguments: Array[Any]): Option[Any] = {
    for (i <- currentIndex until handlers.length) {
      val handler = handlers(i)
      val r = handler.handle(mock, arguments)
      if (r.isDefined) {
        currentIndex = i
        return r
      }
      if (!handler.satisfied)
        return None
    }
    None
  }
  
  private[borachio] def satisfied = handlers.forall { _.satisfied }
  
  override def toString = handlers.mkString("inSequence {\n  ", "\n  ", "\n}")

  private var currentIndex = 0
}