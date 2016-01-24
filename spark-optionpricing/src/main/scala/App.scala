import org.apache.spark.mllib.random.StandardNormalGenerator
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scala.math

object App {
  def main(args: Array[String]) = {
    val random = new StandardNormalGenerator()
    // Monte Carlo setting
    val size = math.pow(10, 10).toInt
    // market information
    val volatility = 0.3
    val spotPrice = 100.0
    val rate = 0.01
    // option parameters
    val maturity = 5.0
    val strike = 102.0

    // Spart setting
    // For local use
    //val conf = new SparkConf().setAppName("Estimating Call option price").setMaster("local[*]")
    val conf = new SparkConf().setAppName("Estimating Call option price")
    val sc = new SparkContext(conf)

    //Monte Cralo
    val result = sc.parallelize(1 to size).map { i =>
      val noize =  volatility * math.sqrt(maturity) * random.nextValue()
      val drift = (rate - 0.5 * volatility * volatility) *  maturity
      val price = spotPrice * math.exp( drift + + noize )
      math.max(price - strike, 0.0)
    }.sum * ( math.exp( -rate * maturity) )/ size
    // Show result
    println("Option price is: " + result)
    sc.stop()
  }
}
