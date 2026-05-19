//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.Buffer;

public class UserManegerInicial {

    private String[] users; //array que armazena os usuários
    private String[] userSessions;   // Array para causar ArrayIndexOutOfBoundsException (bug)
    private String tempNull;         // Para causar NullPointerException (bug)
    
    //carregar os usuários do arquivo users.txt
    public void loadUsersFromFile(String filePath) {
        //não faz tratamento de erros
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        //conta o numero de linhas para criar o array
        int linecount = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            linecount++;
        }
        reader.close();

        //lendo o arquivo e preenchendo o array
        reader = new BufferedReader(new FileReader(filePath));
        users = new String[linecount];

        for(int i = 0;i < linecount; i++) {
            users[i] = reader.readerLine();
        }

        reader.close();

        //mensagem 
        System.out.println("Users loaded successfully from file.");
    }

    //login do usuário
    public boolean login(String users, String password) {

        //mensagem
        System.out.println("Attempting to log in user: " + users);

        for(String users: users) {
            String[] parts = users.split(":");
            String username = parts[1];
            String pass = parts[2];
        }

        if(users.equals(users) && password.equals(password)) {
            System.out.println("Login successful for user: " + users);
            return true;
        } else {
            System.out.println("Login failed for user: " + users);
            return false;
        }
    }

    //método de busca do usuário
    public void fetchUserData(String userIdStr) {
        System.out.println("\n--- Buscando usuário com ID: " + userIdStr + " ---");
        
        // Converte string para número - pode causar NumberFormatException
        int userId = Integer.parseInt(userIdStr);  
        
        // Busca o usuário
        boolean found = false;
        for (String line : userLines) {
            String[] parts = line.split(";");
            int id = Integer.parseInt(parts[0]);
            
            if (id == userId) {
                System.out.println("Usuário encontrado: " + line);
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Usuário com ID " + userId + " não encontrado.");
        }
    }
    
    //atualiza os dados do usuário
    // BUG: Não trata IOException ao salvar arquivo
    public void updateUserData(int userId, String newName, String newPassword) throws IOException {
        System.out.println("\n--- Atualizando usuário ID: " + userId + " ---");
        
        // Atualiza no array
        for (int i = 0; i < userLines.length; i++) {
            String[] parts = userLines[i].split(";");
            int id = Integer.parseInt(parts[0]);
            
            if (id == userId) {
                userLines[i] = userId + ";" + newName + ";" + newPassword;
                System.out.println("Dados atualizados na memória: " + userLines[i]);
                break;
            }
        }
        writer.close();  
        
        System.out.println("Arquivo salvo!");
    }
    
    // método para mostrar os usuários carregados (para teste)
    public void showAllUsers() {
        System.out.println("\n=== LISTA DE USUÁRIOS ===");
        if (userLines == null) {
            System.out.println("Nenhum usuário carregado.");
            return;
        }
        for (String line : userLines) {
            System.out.println(line);
        }
    }
}