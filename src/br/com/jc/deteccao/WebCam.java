/*
 * Created by JFormDesigner on Sun Jan 10 14:41:46 BRT 2021
 */

package br.com.jc.deteccao;

import javax.swing.*;
import net.miginfocom.swing.*;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.awt.*;
import java.awt.image.BufferedImage;

import static br.com.jc.deteccao.Utilitarios.*;

/**
 * @author unknown
 */
public class WebCam extends JFrame{
    //private static JPanel jPanel1;
    private static javax.swing.JPanel jPanel1;
        public static void main(String args[])
        {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            WebCam janela = new WebCam();
            janela.setBounds(0,0,700,500);
            janela.setBackground(Color.gray);
            janela.setVisible(true);
            jPanel1 = new JPanel();
            jPanel1.setVisible(true);
            janela.add(jPanel1);
            janela.mostrarVideo();
        }

        public void mostrarVideo(){
            Mat video = new Mat();
            VideoCapture captura = new VideoCapture(0);
            if(captura.isOpened()){
                while (true){
                    captura.read(video);
                    if(!video.empty()){
                        setSize(video.width()+50, video.height()+70);
                        Mat imagemColorida = video;
                        Mat imagemCinza = new Mat();
                        Imgproc.cvtColor(imagemColorida, imagemCinza, Imgproc.COLOR_BGR2GRAY);
                        CascadeClassifier classificador = new CascadeClassifier("src\\br\\com\\jc\\cascades\\haarcascade_frontalface_default.xml");
                        MatOfRect faceDetectadas = new MatOfRect();
                        classificador.detectMultiScale(
                                imagemCinza,
                                faceDetectadas,
                                1.1,
                                1,
                                0,
                                new Size(150,150),
                                new Size(200,200)
                        );
                        for (Rect rect: faceDetectadas.toArray()) {
                            System.out.println(rect.x + " " + rect.y + " " + rect.width + " " + rect.height);
                            Imgproc.rectangle(
                                    imagemColorida,
                                    new org.opencv.core.Point(rect.x, rect.y),
                                    new Point(rect.x + rect.width,rect.y + rect.height),
                                    new Scalar(0,0,255),
                                    2
                            );
                        }

                        MatOfRect olhosDetectados = new MatOfRect();
                        CascadeClassifier classificadorOlho = new CascadeClassifier("src\\br\\com\\jc\\cascades\\haarcascade_eye.xml");
                        classificadorOlho.detectMultiScale(
                                imagemCinza,
                                olhosDetectados,
                                1.5,
                                4,
                                0,
                                new Size(15,15),
                                new Size(45,45));
                        for (Rect rect: olhosDetectados.toArray()  ) {
                            System.out.println(rect.x + " " + rect.y + " " + rect.width + " " + rect.height);
                            Imgproc.rectangle(
                                    imagemColorida,
                                    new Point(rect.x, rect.y),
                                    new Point(rect.x + rect.width,rect.y + rect.height),
                                    new Scalar(255,255,0),
                                    2
                            );
                        }

                        BufferedImage imagem = convertMatToImage(imagemColorida);
                        Graphics g = jPanel1.getGraphics();
                        g.drawImage(imagem, 10, 10, imagem.getWidth(), imagem.getHeight(), null);
                    }
                }
            }
        }
}

