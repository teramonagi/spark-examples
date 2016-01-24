# Estimating call option price

## Build
`
activator asssembly
or
sbt asssembly 
`

## How to run
```
gcloud beta dataproc clusters create dataproc01
gcloud beta dataproc jobs submit spark --cluster dataproc01 --class App --jars spark-optionpricing-assembly-1.0.jar

```