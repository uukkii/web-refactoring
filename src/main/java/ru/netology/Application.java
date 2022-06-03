package ru.netology;

public class Application {
    public static void main(String[] args) {
        Server server = new Server(64);
        server.start(9999);
    }
}
