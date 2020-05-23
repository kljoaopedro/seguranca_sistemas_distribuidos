package server;

import security.Security;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    private void createServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private Socket waitingConnection() throws IOException {
        return serverSocket.accept();

    }

    private void maintenceConnection(Socket socket) throws Exception {

        try {

            Security secutiry = new Security();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            String message = inputStream.readUTF();
            showMessagesLog(secutiry, message);

            outputStream.writeUTF("A mensagem descriptografada é: " + secutiry.decode(message));
            outputStream.flush();

            outputStream.close();
            inputStream.close();

        } catch (IOException exception) {
            System.out.println("Problema na conexão do cliente: " + socket.getInetAddress());
            System.out.println("Erro: " + exception.getMessage());
        } finally {
            closeSocket(socket);
        }

    }

    private void showMessagesLog(Security secutiry, String message) throws Exception {
        System.out.println("=== SERVER SIDE ===");
        System.out.println("Mensagem recebida...");
        System.out.println("Opa, parece que essa é uma mensagem criptografada: " + message);

        System.out.println("Realizando engenharia reversa...");
        System.out.println("Aguarde...");
        System.out.println("A mensagem descriptografada é: " + secutiry.decode(message));

        System.out.println("===================");
    }

    private void closeSocket(Socket socket) throws IOException {
        System.out.println("Finalizando conexão");
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        try {
            Server server = new Server();
            server.createServer(7777);

            while (true) {
                System.out.println("Aguardando nova conexão...");
                Socket socket = server.waitingConnection();
                System.out.println("Conexão com o servidor bem sucessida!");

                server.maintenceConnection(socket);
            }

        } catch (IOException exception) {
            System.out.println("Erro: " + exception.getMessage());
        }
    }
}
