package br.com.jc.deteccao;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import static br.com.jc.deteccao.Utilitarios.convertMatToImage;
import static br.com.jc.deteccao.Utilitarios.mostraImagem;
import static org.opencv.imgcodecs.Imgcodecs.*;

public class TesteOpenCV {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.VERSION);

        Mat imagemColorida  = imread("src\\br\\com\\jc\\deteccao\\megumin2.jpg", CV_LOAD_IMAGE_COLOR);
        Utilitarios utilitarios = new Utilitarios();
       mostraImagem(convertMatToImage(imagemColorida));

        Mat imagemCinza = new Mat();
        Imgproc.cvtColor(imagemColorida, imagemCinza, Imgproc.COLOR_BGR2GRAY);

        mostraImagem(convertMatToImage(imagemCinza));

    }
}
