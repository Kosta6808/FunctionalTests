package Load;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    public static void loadEnv() {
        Dotenv dotenv = Dotenv.load(); // Ищет .env в корне и загружает
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
}