/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg03des;

/**
 *
 * @author Alumno
 */
import java.security.*;
import javax.crypto.*;
import java.io.*;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        if(args.length != 1){
            mensajeAyuda();
            System.exit(1);
        }
        
        System.out.println("1.- Generar la clave DES");
        //Vamos a ocupar la clase KeyGenerator
        KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
        //ahora hay que definir el tamaño de la clave
        generadorDES.init(56);
        //Generamos clave secreta
        SecretKey clave = generadorDES.generateKey();
        //Para poder ver la clave necesito un metodo para darle formato
        mostrarBytes();
        System.out.println("Veamos la clave: " + clave);
        //es momento de definir los elementos para cifrar
        /*
            DES es un cifrado por bloques, por lo que tenemos que dar reglas
            de como se va a manejar el bloque
            Modo cifrado ECB (Electronic Code Book)
            Estandar PKCS5Padding
        */
        System.out.println("2.- Cifrar con DES el archivo"+args[0]+ ", generamos el resultado en " + args[0]+".cifrado");
        //Lo feo a cifrar
        Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        
        //Ciframos
        cifrador.init(Cipher.ENCRYPT_MODE, clave);
        
        //leer el archivo y definir de cuanto en cuanto bytes de lectura
        byte[] buffer = new byte[1000];
        byte[] bufferCifrado;
        
        FileInputStream in = new FileInputStream(args[0]);
        FileOutputStream out = new FileOutputStream(args[0]+".cifrado");
        
        //Leo cada archivo
        int bytesleidos = in.read(buffer,0,1000);
        while(bytesleidos != -1){
            //que no he terminado de leer el archivo
            bufferCifrado = cifrador.update(buffer,0,bytesleidos);
            bytesleidos = in.read(buffer,0,bytesleidos);            
        }
        bufferCifrado = cifrador.doFinal();
        out.write(bufferCifrado);
        
        in.close();
        out.close();
        
        System.out.println("Vamos a Descifrar el archivo" +args[0]+".cifrado"+" , y el resultado está en "+args[0]+".descifrado");
        //copiamos lo de arriba
        cifrador.init(Cipher.DECRYPT_MODE, clave);
        
        //leer el archivo y definir de cuanto en cuanto bytes de lectura
        byte[] buffer = new byte[1000];
        byte[] bufferCifrado;
        
        in = new FileInputStream(args[0]);
        out = new FileOutputStream(args[0]+".cifrado");
        
        //Leo cada archivo
        bytesleidos = in.read(buffer,0,1000);
        while(bytesleidos != -1){
            //que no he terminado de leer el archivo
            bufferCifrado = cifrador.update(buffer,0,bytesleidos);
            bytesleidos = in.read(buffer,0,bytesleidos);            
        }
        bufferCifrado = cifrador.doFinal();
        out.write(bufferCifrado);
        
        in.close();
        out.close();
    }

    public static void mensajeAyuda() {
        System.out.println("Ejemplo de cifrado DES");
        System.out.println("Debe de tener a fuerza un archivo cargado");
    }

    public static void mostrarBytes(byte[] buffer) {
        System.out.write(buffer,0,buffer.length);
    }
    
}
