//Answer 1
def map[B](f: A => B): Option[B] = this match {
  case None => None
  case Some(a) => Some(f(a))
}

def getOrElse[B>:A](default: => B): B = this match {
  case None => default
  case Some(a) => a
}

def flatMap[B](f: A => Option[B]): Option[B] = 
  map(f) getOrElse None

def flatMap_1[B](f: A => Option[B]): Option[B] = this match {
  case None => None
  case Some(a) => f(a)
}

def orElse[B>:A](ob: => Option[B]): Option[B] = 
  this map (Some(_)) getOrElse ob
def filter(f: A => Boolean): Option[A] = this match {
  case Some(a) if f(a) => this
  case _ => None
}
//Answer 2
def variance(xs: Seq[Double]): Option[Double] = 
  mean(xs) flatMap (m => mean(xs.map(x => math.pow(x - m, 2))))

  //Answer 3
  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
  a flatMap (aa => b map (bb => f(aa, bb)))

  //Answer 4

  def sequence[A](a: List[Option[A]]): Option[List[A]] =
  a match {
    case Nil => Some(Nil)
    case h :: t => h flatMap (hh => sequence(t) map (hh :: _))
  }

//Answer 5

def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
  a match {
    case Nil => Some(Nil)
    case h::t => map2(f(h), traverse(t)(f))(_ :: _)
  }

  //Answer 6

  def map[B](f: A => B): Either[E, B] = 
  this match {
    case Right(a) => Right(f(a))
    case Left(e) => Left(e)
  }
  
def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] =
  this match {
    case Left(e) => Left(e)
    case Right(a) => f(a)
  }
def orElse[EE >: E, AA >: A](b: => Either[EE, AA]): Either[EE, AA] =
  this match {
    case Left(_) => b
    case Right(a) => Right(a)
  }
def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): 
  Either[EE, C] = for { a <- this; b1 <- b } yield f(a,b1)

  //Answer 7

  def traverse[E,A,B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] = 
  es match {
    case Nil => Right(Nil)
    case h::t => (f(h) map2 traverse(t)(f))(_ :: _)
  }

def traverse_1[E,A,B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] = 
  es.foldRight[Either[E,List[B]]](Right(Nil))((a, b) => f(a).map2(b)(_ :: _))

def sequence[E,A](es: List[Either[E,A]]): Either[E,List[A]] = 
  traverse(es)(x => x)

  