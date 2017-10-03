package com.example.estudiante.social;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

/**
 * Created by estudiante on 03/05/17.
 */

public class ComunicacionCliente extends Observable implements Runnable {

    private static ComunicacionCliente instance = null;
    private boolean online;
    private Socket s = null;
    private String ipServidor = null;

    private ComunicacionCliente() {

    }

    public static ComunicacionCliente getInstance() {

        if (instance == null) {
            instance = new ComunicacionCliente();
            new Thread(instance).start();
        }
        return instance;

    }

    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }

    private void iniciarSocket() {

        if (s == null) {
            try {
                s = new Socket(InetAddress.getByName(ipServidor), 5000);
                online = true;
                System.out.println("=========== INICIALIZO EL SOCKET ==========");

            } catch (IOException e) {
                setChanged();
                notifyObservers("servidor_outline");
                clearChanged();
            }
        }
    }

    public void peticion_acceso(final Usuario u) {
        System.out.println("======= se envio una peticion ==========");
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    ObjectOutputStream salida = new ObjectOutputStream(s.getOutputStream());
                    salida.writeObject(u);
                    salida.flush();
                } catch (IOException e) {
                    System.out.println("======= problema de conexion ==========");
                    e.printStackTrace();

                }
            }
        }).start();


    }

    private Object recibir() {
        ObjectInputStream entrada = null;
        if (s != null) {
            try {

                entrada = new ObjectInputStream(s.getInputStream());
                return entrada.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void enviar(final Object object) {

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    System.out.println("se mando algo");
                    ObjectOutputStream salida = new ObjectOutputStream(s.getOutputStream());
                    salida.writeObject(object);
                    salida.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("======================================================================================");
                    System.err.println("======================================================================================");
                    System.err.println("                                      ERROR AL ENVIAR                                  ");
                    System.err.println("======================================================================================");
                    System.err.println("======================================================================================");

                }

            }
        }).start();


    }


    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("=========== entro al RUN ==========");
                iniciarSocket();

                setChanged();
                notifyObservers(recibir());
                clearChanged();

                Thread.sleep(60);
            } catch (InterruptedException e) {
                System.out.println("=========== Error al abrir el socket ==========");
                e.printStackTrace();
            }
        }
    }
}