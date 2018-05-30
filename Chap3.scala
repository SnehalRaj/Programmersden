// EXERCISE 1: What will the result of the following match expression be?
// val x = List(1,2,3,4,5) match {
// case Cons(x, Cons(2, Cons(4, _))) => x
// case Nil => 42
// case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
// case Cons(h, t) => h + sum(t)
// case _ => 101
// }
Answer : 1 + 2 = 3

// EXERCISE 2: Implement the function tail for "removing" the first element
// of a List . Notice the function takes constant time. What are different choices you
// could make in your implementation if the List is Nil ? We will return to this
// question in the next chapter.
def tail[A](l: List[A]) : List[A]= {
   l match{
    case Nil => Nil
    case Cons(_,t) => t
  }
} 
// EXERCISE 3: Using the same idea, implement the function setHead for
// replacing the first element of a List with a different value.
def setHead[A](l: List[A] , h : Int) : List[A] = l match{
  case Nil => Nil
  case Cons(_,t)=> Cons(h,t)
}
// EXERCISE 4: Generalize tail to the function drop , which removes the first
// n elements from a list. Notice this function takes time proportional only to the
// number of elements being dropped—we do not need to make a copy of the entire
// List .
// def drop[A](l: List[A], n: Int): List[A]

def drop[A](l: List[A] ,n : Int) : List[A] = {
    if(n<=0)  l
    else {
      l match{
        case Nil => Nil
        case Cons(h,t)   => drop(t,n-1)
      }
    }

}


// EXERCISE 5: Implement dropWhile , which removes elements from the
// List prefix as long as they match a predicate.
// def dropWhile[A](l: List[A], f: A => Boolean): List[A]

def dropWhile[A](l: List[A] , f: A => Boolean) : List[A] = l match{
  case Nil => Nil
  case Cons(h,t)  if(f(h)) => dropWhile(t,f)
  case _ => l
}


// EXERCISE 6: Not everything works out so nicely. Implement a function,
// init , which returns a List consisting of all but the last element of a List . So,
// given List(1,2,3,4) , init will return List(1,2,3) . Why can't this
// function be implemented in constant time like tail ?
// def init[A](l: List[A]): List[A]
 
 def init[A](l: List[A]): List[A] = l match{
   case Nil => Nil
   case Cons(_,Nil) => Nil
   case Cons(h,t) => Cons(h,init(t))
 }
 
// EXERCISE 7: Can product implemented using foldRight immediately
// halt the recursion and return 0.0 if it encounters a 0.0 ? Why or why not?
// Consider how any short-circuiting might work if you call foldRight with a
// large list. This is a deeper question that we'll return to a few chapters from now.

NO

// EXERCISE 8: See what happens when you pass Nil and Cons themselves to
// foldRight ,
// like
// this:
// foldRight(List(1,2,3),
// Nil:List[Int])(Cons(_,_)) . 10 What do you think this says about the
// relationship between foldRight and the data constructors of List ?

List (1,2,3) will be returned

// EXERCISE 9: Compute the length of a list using foldRight .
// def length[A](l: List[A]): Int

def length[A](l: List[A]): Int ={
  foldRight(l,0)((_,acc)=> acc+1)
}

// EXERCISE 10: foldRight is not tail-recursive and will StackOverflow
// for large lists. Convince yourself that this is the case, then write another general
// list-recursion function, foldLeft that is tail-recursive, using the techniques we
// discussed in the previous chapter. Here is its signature: 11
// def foldLeft[A,B](l: List[A], z: B)(f: (B, A) => B): B

def foldLeft[A,B](l: List[A], z: B)(f: (B, A) => B): B = l match{
  case Nil => z
  case Cons(h,t) => foldLeft(t,f(z,h))(f) 
  
}

// EXERCISE 11: Write sum , product , and a function to compute the length of
// a list using foldLeft .

val x= List(1,2,3,4)
val sum = foldLeft(x,0)(_+_)
val prod = foldLeft(x,1)(_*_)
val length = foldLeft(x,0)((_,acc)=> acc + 1)

// EXERCISE 12: Write a function that returns the reverse of a list (so given
// List(1,2,3) it returns List(3,2,1) ). See if you can write it using a fold.

def rev[A](l: List[A]): List[A] = l match{
  case Nil => Nil
  case Cons(h,t) => foldRight(l,List[A]()((acc,h)=> Cons(h,acc))
}

// EXERCISE 13 (hard): Can you write foldLeft in terms of foldRight ?
// How about the other way around?

def foldLeftintermsoffoldRight[A,B](l: List[A], z: B)(f:(B,A) => B): B = l match{

}


// EXERCISE 14: Implement append in terms of either foldLeft or
// foldRight .

def appendLists[A](l: List[A], r: List[A]): List[A] = {
  foldRight(l,r)(Cons(_,_))
}

// EXERCISE 15 (hard): Write a function that concatenates a list of lists into a
// single list. Its runtime should be linear in the total length of all lists. Try to use
// functions we have already defined.

def concat[A](l:List[List[A]]) : List[A] = {
  foldRight(l,Nil:List[A])(append)
}

// EXERCISE 16: Write a function that transforms a list of integers by adding 1
// to each element. (Reminder: this should be a pure function that returns a new
// List !)

def add1[A](l: List[A]) : List[A] = {
  foldRight(l,Nil:List[A])((h,t)=> Cons(h+1,t))
}

// EXERCISE 17: Write a function that turns each value in a List[Double]
// into a String .

def DoubletoString[A](l:List[Double]): List[Double] = {
  foldRight(l,Nil: List[Double])((h,t)=> Cons(h.toString,t))
}


// EXERCISE 18: Write a function map , that generalizes modifying each element
// in a list while maintaining the structure of the list. Here is its signature: 

// def map[A,B](l: List[A])(f: A => B): List[B]

def map[A,B](l: List[A])(f: A => B): List[B] = {
  foldRight(l,Nil: List[A])((h,t)=> Cons(f(h),t))
}


// EXERCISE 19: Write a function filter that removes elements from a list
// unless they satisfy a given predicate. Use it to remote all odd numbers from a
// List[Int] .

def filter[A](l: List[A])(f: A => B): List[B] = {
  foldRight(l, Nil:List[A])((h,t) => if (f(h)) Cons(h,t) else t)
}

// EXERCISE 20: Write a function flatMap , that works like map except that
// the function given will return a list instead of a single result, and that list should be
// inserted into the final resulting list. Here is its signature:
// def flatMap[A,B](l: List[A])(f: A => List[B]): List[B]

def flatMap[A,B](l: List[A])(f: A => List[B]): List[B] = {
  concat(map(l)(f))
}


// EXERCISE 21: Can you use flatMap to implement filter ?

def filter[A](l: List[A])(f: A => Boolean): List[B] = {
flatMap(l)(a => if(f(a)) List(a) else Nil) 
}


// EXERCISE 22: Write a function that accepts two lists and constructs a new list
// by adding corresponding elements. For example, List(1,2,3) and
// List(4,5,6) becomes List(5,7,9) .

def addList(l1: List[A], l2: List[A]) : List[A] = (l1,l2) match{
case (Nil,_) => Nil
case (_,Nil) => Nil
case (Cons(h1,t1),Cons(h2,t2)) => Cons(h1+h2)
}

// EXERCISE 23: Generalize the function you just wrote so that it's not specific to
// integers or addition.

def addList(l1: List[A], l2: List[A],f(A,B)=>C) : List[A] = (l1,l2) match{
case (Nil,_) => Nil
case (_,Nil) => Nil
case (Cons(h1,t1),Cons(h2,t2)) => Cons(f(h1,h2))
}
// EXERCISE 24 (hard): As an example, implement hasSubsequence for
// checking whether a List contains another List as a subsequence. For instance,
// List(1,2,3,4) would have List(1,2) , List(2,3) , and List(4) as
// subsequences, among others. You may have some difficulty finding a concise
// purely functional implementation that is also efficient. That's okay. Implement the
// function however comes most naturally. We will return to this implementation in a
// couple of chapters and hopefully improve on it. Note: any two values, x , and y ,
// can be compared for equality in Scala using the expression x == y .
// def hasSubsequence[A](l: List[A], sub: List[A]): Boolean

 def hasSubsequence[A](l: List[A], sub: List[A]): Boolean = l match{
   case Nil => false
   case Cons(h,t) => if(starts(Cons(h,t),sub)) true else hasSubsequence(t,sub)
 }
def starts[A](l: List[A],start: List[A]) : Boolean = (l,start) match{
  case (_,Nil) => true
  case (Cons(h1,t1),Cons(h2,t2) => if(h1==h2) starts(t1,t2) else false
}

// EXERCISE 25: Write a function size that counts the number of nodes (leaves
// and branches) in a tree.

def size[A](t: Tree[A]): Int = t match {
  case Leaf(_) => 1
  case Branch(l,r) => 1 + size(l) + size(r)
}
// EXERCISE 26: Write a function maximum that returns the maximum element
// in a Tree[Int] . (Note: in Scala, you can use x.max(y) or x max y to
// compute the maximum of two integers x and y .)

def maximum(t: Tree[Int]): Int = t match {
  case Leaf(n) => n
  case Branch(l,r) => maximum(l) max maximum(r)
}

// EXERCISE 27: Write a function depth that returns the maximum path length
// from the root of a tree to any leaf.

def depth[A](t: Tree[A]): Int = t match {
  case Leaf(_) => 0
  case Branch(l,r) => 1 + (depth(l) max depth(r))
}

// EXERCISE 28: Write a function map , analogous to the method of the same
// name on List , that modifies each element in a tree with a given function.

def map[A,B](t: Tree[A])(f: A => B): Tree[B] = t match {
  case Leaf(a) => Leaf(f(a))
  case Branch(l,r) => Branch(map(l)(f), map(r)(f))
}

// EXERCISE 29: Generalize size , maximum , depth , and map , writing a new
// function fold that abstracts over their similarities. Reimplement them in terms of
// this more general function. Can you draw an analogy between this fold function
// and the left and right folds for List ?

def fold[A,B](t: Tree[A])(f: A => B)(g: (B,B) => B): B = t match {
  case Leaf(a) => f(a)
  case Branch(l,r) => g(fold(l)(f)(g), fold(r)(f)(g))
}

def sizeViaFold[A](t: Tree[A]): Int = 
  fold(t)(a => 1)(1 + _ + _)

def maximumViaFold(t: Tree[Int]): Int = 
  fold(t)(a => a)(_ max _)

def depthViaFold[A](t: Tree[A]): Int = 
  fold(t)(a => 0)((d1,d2) => 1 + (d1 max d2))