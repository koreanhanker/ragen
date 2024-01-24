//package example;
//
//import org.datavec.api.records.reader.RecordReader;
//import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
//import org.datavec.api.split.FileSplit;
//import org.datavec.api.writable.DoubleWritable;
//import org.datavec.api.writable.IntWritable;
//import org.datavec.api.writable.Text;
//import org.datavec.api.writable.Writable;
//import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
//import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
//import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
//import org.deeplearning4j.nn.conf.layers.DenseLayer;
//import org.deeplearning4j.nn.conf.layers.OutputLayer;
//import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
//import org.deeplearning4j.nn.weights.WeightInit;
//import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
//import org.nd4j.evaluation.regression.RegressionEvaluation;
//import org.nd4j.linalg.activations.Activation;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
//import org.nd4j.linalg.learning.config.Adam;
//import org.nd4j.linalg.lossfunctions.LossFunctions;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class LottoNumberPrediction {
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        // CSV 파일에서 데이터를 읽어옴
//        CSVRecordReader recordReader = new CSVRecordReader(0, ',') {
//            @Override
//            public List<Writable> next() {
//                List<Writable> list = super.next();
//                // 모든 열의 값을 숫자로 변환
//                for (int i = 0; i < list.size(); i++) {
//                    int numericValue = Integer.parseInt(list.get(i).toString());
//                    list.set(i, new IntWritable(numericValue));
//                }
//                return list;
//            }
//        };
//        recordReader.initialize(new FileSplit(new File("C:\\Users\\SAMSUNG\\Downloads\\lotto.csv")));
//
//
//        // DataSetIterator 생성
//        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, 1080, 0, 5, true);
//
//        // 네트워크 구성 설정
//        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
//                .seed(123)
//                .updater(new Adam(0.01))
//                .list()
//                .layer(new DenseLayer.Builder()
//                        .nIn(6)
//                        .nOut(64)
//                        .activation(Activation.RELU)
//                        .weightInit(WeightInit.XAVIER)
//                        .build())
//                .layer(new DenseLayer.Builder()
//                        .nIn(64)
//                        .nOut(32)
//                        .activation(Activation.RELU)
//                        .weightInit(WeightInit.XAVIER)
//                        .build())
//                .layer(new OutputLayer.Builder()
//                        .nIn(32)
//                        .nOut(6)
//                        .activation(Activation.IDENTITY)
//                        .lossFunction(LossFunctions.LossFunction.MSE)
//                        .build())
//                .build();
//
//        MultiLayerNetwork model = new MultiLayerNetwork(conf);
//        model.init();
//
//        // 모델 학습
//        int numEpochs = 50;
//        for (int i = 0; i < numEpochs; i++) {
//            model.fit(iterator);
//        }
//
//        // 예측 평가
//        RegressionEvaluation eval = model.evaluateRegression(iterator);
//        // System.out.println("Mean Squared Error: " + eval.meanSquaredError(0));
//
//        // 새로운 로또 번호 예측
//        List<Double> testData = new ArrayList<>();
//        testData.add(13.0);
//        testData.add(16.0);
//        testData.add(23.0);
//        testData.add(31.0);
//        testData.add(36.0);
//        testData.add(44.0);
//
//        // Convert to double array and create INDArray
//        double[] dataArray = testData.stream().mapToDouble(Double::doubleValue).toArray();
//        INDArray inputArray = org.nd4j.linalg.factory.Nd4j.create(dataArray);
//        INDArray newNumbers = model.output(inputArray, false);
//        System.out.println("Predicted Lotto Numbers: " + newNumbers);
//
//        // 모델 저장
//        model.save(new File("lotto_model.zip"));
//    }
//}
