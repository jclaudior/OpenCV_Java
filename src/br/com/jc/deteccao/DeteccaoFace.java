package br.com.jc.deteccao;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import static br.com.jc.deteccao.Utilitarios.convertMatToImage;
import static br.com.jc.deteccao.Utilitarios.mostraImagem;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_COLOR;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class DeteccaoFace {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat imagemColorida = imread("src\\br\\com\\jc\\pessoas\\joao.jpg", CV_LOAD_IMAGE_COLOR);
        //Size sz = new Size(300,400);
        Mat imagemCinza = new Mat();
        Imgproc.cvtColor(imagemColorida, imagemCinza, Imgproc.COLOR_BGR2GRAY);
        //Imgproc.resize( imagemCinza, imagemCinza, sz );
        //Imgproc.resize( imagemColorida, imagemColorida, sz );


        CascadeClassifier classificador = new CascadeClassifier("src\\br\\com\\jc\\cascades\\haarcascade_frontalface_default.xml");

        MatOfRect facesDetectadas = new MatOfRect();
        classificador.detectMultiScale(
                imagemCinza,
                facesDetectadas,
                1.43,
                1,
                0,
                new Size(30,30),
                new Size(100,100));

        System.out.println(facesDetectadas.toArray().length);

        for(Rect rect: facesDetectadas.toArray()){
            System.out.println(rect.x + " " + rect.y + " " + rect.width + " " + rect.height);
            Imgproc.rectangle(
                    imagemColorida,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width,rect.y + rect.height),
                    new Scalar(0,0,255),
                    2
                    );
        }

        mostraImagem(convertMatToImage(imagemColorida));


    }
}
