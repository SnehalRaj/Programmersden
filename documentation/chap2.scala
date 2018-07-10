  // Exercise 1: Write a function to compute the nth fibonacci number

  def fib(n: Int): Int = {
    @annotation.tailrec
    def go(n: Int,prev: Int, cur: Int):Int =
  if(n==1) prev
  else go(n-1,cur,prev+cur)

  go(n,0,1)
  }

   // Exercise 2: Implement a polymorphic function to check whether
  // an `Array[A]` is sorted
  def isSorted[A](as: Array[A], gt: (A,A) => Boolean): Boolean = {
    @annotation.tailrec
    def go(n: Int): Boolean = {
      if(n==as.length-1) true
      else if(gt(as[n],as[n+1])) go(n+1)
      else false
    }
    go(0)
  }


  // Exercise 3: Implement `curry`.

  
  def curry[A,B,C](f: (A, B) => C): A => (B => C) =
    (a: A) => ((b: B) => f(a,b))

     // Exercise 4: Implement `uncurry`
  def uncurry[A,B,C](f: A => B => C): (A, B) => C =
    (a: A , b: B) => f(a)(b)

// Exercise 5: Implement `compose`

  def compose[A,B,C](f: B => C, g: A => B): A => C =
    (a: A) => f(g(a))
}