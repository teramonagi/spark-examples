/* App.scala */
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object App {
  def main(args: Array[String]) = {
    val conf = new SparkConf().setAppName("Estimating PI")
    val sc = new SparkContext(conf)
    val size = args(0).toInt
    val count = sc.parallelize(1 to size).map { i =>
      val x = Math.random()
      val y = Math.random()
      if(x*x + y*y < 1) 1 else 0
    }.reduce((x:Int, y:Int) => x+y)
    println("Pi is roughly " + 4.0 * count / size)
    sc.stop()
  }
}