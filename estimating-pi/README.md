# Estimating PI

## Build
`
activator asssembly
`

## How to run
```
gcloud beta dataproc clusters create dataproc01
gcloud beta dataproc jobs submit spark --cluster dataproc01 --class App --jars estimating-pi-assembly-1.0.jar 77777
// or, run after upload jar file to google storage
// gcloud beta dataproc jobs submit spark --cluster dataproc01 --class App --jars gs://<your_path>/estimating-pi-assembly-1.0.jar 77777
```