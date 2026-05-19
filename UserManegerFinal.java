//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

//código com os tratamentos de erros implementados
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class UserManegerFinal {

    private String[] userLines;      // Array para armazenar os dados dos usuários
    private String[] userSessions;   // Array para gerenciar sessões
    private Scanner scanner;         // Para ler entrada do usuário

    //construtor
    public UserManegerFinal() {
        scanner = new Scanner(System.in);
        userSessions = new String[100]; // Supondo um máximo de 100 sessões
    }

    //método de login do usuário
    private void logError(Exception e, String contexto) {
        try (FileWriter fw = new FileWriter("error.log", true);
             PrintWriter pw = new PrintWriter(fw)) {
            
            Date dataHora = new Date();
            pw.println("[" + dataHora + "] " + 
                       "Contexto: " + contexto + " | " +
                       "Tipo: " + e.getClass().getSimpleName() + " | " +
                       "Mensagem: " + e.getMessage());
            
            // Opcional: imprimir stack trace no log
            // e.printStackTrace(pw);
            
        } catch (IOException io) {
            System.out.println("ERRO CRÍTICO: Não foi possível escrever no arquivo de log!");
            io.printStackTrace();
        }
    }

    //carrega os usuários do arquivo
    public boolean loadUsersFromFile(String filePath) {
        System.out.println("\n--- Carregando arquivo: " + filePath + " ---");
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(filePath));
            
            // Primeiro passo contar linhas
            int lineCount = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
            }
            
            if (lineCount == 0) {
                System.out.println("Arquivo vazio!");
                userLines = new String[0];
                return false;
            }
            
            // Volta ao início e carrega de verdade
            reader.close();
            reader = new BufferedReader(new FileReader(filePath));
            userLines = new String[lineCount];
            
            for (int i = 0; i < lineCount; i++) {
                userLines[i] = reader.readLine();
            }
            
            System.out.println("Arquivo carregado! Total de usuários: " + userLines.length);
            return true;
            
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo '" + filePath + "' não encontrado!");
            logError(e, "loadUsersFromFile - Arquivo não encontrado");
            userLines = new String[0];
            return false;
            
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            logError(e, "loadUsersFromFile - Erro de leitura");
            userLines = new String[0];
            return false;
            
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("Arquivo fechado com sucesso.");
                } catch (IOException e) {
                    System.out.println("Aviso: Erro ao fechar o arquivo.");
                    logError(e, "loadUsersFromFile - Erro ao fechar arquivo");
                }
            }
        }
    }
    
    //método para o login do usuário
    public boolean login(String username, String password) {
        System.out.println("\n--- Tentando login: " + username + " ---");
        
        // Verificação de segurança primeiro (evita NullPointerException)
        if (userLines == null) {
            System.out.println("Erro: Dados de usuários não carregados. Faça o load primeiro.");
            return false;
        }
        
        try {
            // Login normal
            for (String userLine : userLines) {
                String[] parts = userLine.split(";");
                
                // Verificação para evitar ArrayIndexOutOfBounds
                if (parts.length < 3) {
                    System.out.println("Linha mal formatada ignorada: " + userLine);
                    continue;
                }
                
                String nome = parts[1];
                String senha = parts[2];
                
                if (nome.equals(username) && senha.equals(password)) {
                    System.out.println("Login bem-sucedido!");
                    
                    // Tenta criar uma sessão (pode causar ArrayIndexOutOfBounds)
                    try {
                        for (int i = 0; i < userSessions.length; i++) {
                            if (userSessions[i] == null) {
                                userSessions[i] = username;
                                break;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Aviso: Não foi possível criar sessão, mas login continua válido.");
                        logError(e, "login - ArrayIndexOutOfBounds ao criar sessão");
                    }
                    
                    return true;
                }
            }
            
            System.out.println("Falha no login! Usuário ou senha incorretos.");
            return false;
            
        } catch (NullPointerException e) {
            System.out.println("Erro interno no sistema de login. Contate o administrador.");
            logError(e, "login - NullPointerException");
            return false;
        }
    }
    
    // método para buscar os dados do usuário
    public void fetchUserData() {
        System.out.println("\n--- Buscar dados de usuário ---");
        
        // Verifica se dados foram carregados
        if (userLines == null || userLines.length == 0) {
            System.out.println("Erro: Nenhum usuário carregado. Execute loadUsersFromFile primeiro.");
            return;
        }
        
        boolean idValido = false;
        
        while (!idValido) {
            try {
                System.out.print("Digite o ID do usuário: ");
                String input = scanner.nextLine();
                
                // Tenta converter para número
                int userId = Integer.parseInt(input);
                
                // Verifica se o índice é válido 
                if (userId <= 0) {
                    System.out.println("ID deve ser um número positivo!");
                    continue;
                }
                
                // Busca o usuário
                boolean found = false;
                for (String line : userLines) {
                    String[] parts = line.split(";");
                    
                    if (parts.length < 3) {
                        continue;
                    }
                    
                    int id = Integer.parseInt(parts[0]);
                    
                    if (id == userId) {
                        System.out.println("Usuário encontrado:");
                        System.out.println("   ID: " + parts[0]);
                        System.out.println("   Nome: " + parts[1]);
                        System.out.println("   Senha: " + parts[2]);
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    System.out.println("Usuário com ID " + userId + " não encontrado.");
                    System.out.println("   Tente novamente ou digite 'sair' para cancelar.");
                } else {
                    idValido = true; // Sai do loop se encontrou
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Erro: ID inválido! Digite apenas números.");
                System.out.println("   Tente novamente (exemplo: 1, 2, 3...):");
                logError(e, "fetchUserData - NumberFormatException ao digitar ID");
                // Continua no loop pedindo novo ID
            } catch (NullPointerException e) {
                System.out.println("Erro interno na busca de dados.");
                logError(e, "fetchUserData - NullPointerException");
                break; // Sai do loop se for erro grave
            }
        }
    }
    
    //método para atualizar os dados do usuário
    public void updateUserData() {
        System.out.println("\n--- Atualizar dados de usuário ---");
        
        if (userLines == null || userLines.length == 0) {
            System.out.println("Erro: Nenhum usuário carregado. Execute loadUsersFromFile primeiro.");
            return;
        }
        
        try {
            System.out.print("Digite o ID do usuário a ser atualizado: ");
            int userId = Integer.parseInt(scanner.nextLine());
            
            boolean encontrado = false;
            int indiceEncontrado = -1;
            
            // Busca o usuário no array
            for (int i = 0; i < userLines.length; i++) {
                String[] parts = userLines[i].split(";");
                if (parts.length >= 3) {
                    int id = Integer.parseInt(parts[0]);
                    if (id == userId) {
                        encontrado = true;
                        indiceEncontrado = i;
                        break;
                    }
                }
            }
            
            if (!encontrado) {
                System.out.println("Usuário com ID " + userId + " não encontrado.");
                return;
            }
            
            // Pega novos dados
            System.out.print("Novo nome: ");
            String novoNome = scanner.nextLine();
            System.out.print("Nova senha: ");
            String novaSenha = scanner.nextLine();
            
            // Atualiza no array
            userLines[indiceEncontrado] = userId + ";" + novoNome + ";" + novaSenha;
            System.out.println("Dados atualizados na memória: " + userLines[indiceEncontrado]);
            
            // Salva no arquivo COM tratamento de erro
            saveUsersToFile("users.txt");
            
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
            logError(e, "updateUserData - NumberFormatException");
        } catch (NullPointerException e) {
            System.out.println("Erro interno ao atualizar dados.");
            logError(e, "updateUserData - NullPointerException");
        }
    }
    
    // salva os dados no arquivo
    private void saveUsersToFile(String filePath) {
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            
            for (String line : userLines) {
                writer.write(line);
                writer.newLine();
            }
            
            System.out.println("Arquivo salvo com sucesso!");
            
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            logError(e, "saveUsersToFile - Erro ao salvar");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                    System.out.println("Arquivo fechado.");
                } catch (IOException e) {
                    System.out.println("Aviso: Erro ao fechar o arquivo.");
                    logError(e, "saveUsersToFile - Erro ao fechar");
                }
            }
        }
    }
    
    // método para testar ArrayIndexOutOfBoundsException 
    public void testArrayIndexOutOfBounds() {
        System.out.println("\n--- Teste: Acessar índice inválido do array ---");
        
        try {
            // Tentativa de acessar índice fora dos limites
            System.out.println("Tentando acessar posição 99 do array de sessões...");
            String valor = userSessions[99];
            System.out.println("Valor: " + valor);
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exceção capturada com sucesso!");
            System.out.println("   Erro: Tentativa de acessar índice fora do array.");
            System.out.println("   O programa continua normalmente sem quebrar.");
            logError(e, "testArrayIndexOutOfBounds - Acesso a índice inválido");
        }
    }
    
    // mostra todos os usuários carregados 
    public void showAllUsers() {
        System.out.println("\n=== LISTA DE USUÁRIOS ===");
        
        if (userLines == null || userLines.length == 0) {
            System.out.println("Nenhum usuário carregado.");
            return;
        }
        
        for (int i = 0; i < userLines.length; i++) {
            String[] parts = userLines[i].split(";");
            if (parts.length >= 3) {
                System.out.println("ID: " + parts[0] + " | Nome: " + parts[1] + " | Senha: " + parts[2]);
            } else {
                System.out.println("Linha inválida: " + userLines[i]);
            }
        }
    }
    
    // ========== MENU PRINCIPAL ==========
    public void showMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE GERENCIAMENTO      ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ 1 - Carregar arquivo de usuários  ║");
        System.out.println("║ 2 - Fazer login                   ║");
        System.out.println("║ 3 - Buscar usuário por ID         ║");
        System.out.println("║ 4 - Atualizar usuário             ║");
        System.out.println("║ 5 - Mostrar todos os usuários     ║");
        System.out.println("║ 6 - Testar ArrayIndexOutOfBounds  ║");
        System.out.println("║ 0 - Sair                          ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");
    }

    //metodo principal para rodar o programa
    public static void main(String[] args) {
        UserManegerFinal manager = new UserManegerFinal();
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        
        while (choice != 0) {
            manager.showMenu();
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite um número.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    manager.loadUsersFromFile("users.txt");
                    break;
                case 2:
                    System.out.print("Usuário: ");
                    String user = scanner.nextLine();
                    System.out.print("Senha: ");
                    String pass = scanner.nextLine();
                    manager.login(user, pass);
                    break;
                case 3:
                    manager.fetchUserData();
                    break;
                case 4:
                    manager.updateUserData();
                    break;
                case 5:
                    manager.showAllUsers();
                    break;
                case 6:
                    manager.testArrayIndexOutOfBounds();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        
        scanner.close();
        System.out.println("Programa finalizado!");
    }
}
