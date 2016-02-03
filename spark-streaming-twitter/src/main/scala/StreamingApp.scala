package org.littlewings.spark

import org.apache.lucene.analysis.ja.JapaneseAnalyzer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.spark.SparkConf
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Durations, StreamingContext}

object TwitterStreaming {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Twitter Streaming")
    val ssc = new StreamingContext(conf, Durations.minutes(1L))

    val filter = if (args.isEmpty) Nil else args.toList
    val stream = TwitterUtils.createStream(ssc, None, filter)

    stream
      .flatMap { status =>
        val text = status.getText

        val analyzer = new JapaneseAnalyzer
        val tokenStream = analyzer.tokenStream("", text)
        val charAttr = tokenStream.addAttribute(classOf[CharTermAttribute])

        tokenStream.reset()

        try {
          Iterator
            .continually(tokenStream.incrementToken())
            .takeWhile(identity)
            .map(_ => charAttr.toString)
            .toVector
        } finally {
          tokenStream.end()
          tokenStream.close()
        }
      }
      .map(word => (word, 1))
      .reduceByKey((a, b) => a + b)
      .saveAsTextFiles("output/tweet")

    ssc.start()
    ssc.awaitTermination()
  }
}
