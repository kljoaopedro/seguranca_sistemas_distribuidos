package client;

import security.Security;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class Client {

    public static void main(String[] args) {

        String rawMessage;

        try {
            Security security = new Security();

            Socket socket = new Socket("localhost", 7777);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("=== CLIENT SIDE ===");
            System.out.println("Digite abaixo sua mensagem");

            Scanner typerCapture = new Scanner(System.in);

            rawMessage = typerCapture.nextLine();
            System.out.println("Criptografando mensagem...");

            String encodedMessage = security.encripty(rawMessage);
            System.out.println("Sua mensagem criptografada é: " + encodedMessage);
            System.out.println("Enviando mensagem criptografada para o servidor...");

            outputStream.writeUTF(encodedMessage);
            outputStream.flush();

            String response = inputStream.readUTF();
            System.out.println("Resposta do servidor: " + response);
            System.out.println("===================");

            inputStream.close();
            outputStream.close();
        } catch (IOException exception) {
            Logger.getLogger(Client.class.getName()).log(SEVERE, exception.getMessage());
        }

    }
}
