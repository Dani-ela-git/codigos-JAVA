//Bruna Romero 11913896
//Daniela Costa da Silva 14613625


/**
 * Representa a conta bancária do jogador no jogo "World of Zuil".
 * Gerencia depósitos, saques e saldo do jogador.
 * 
 * @author Bruna Romero
 * @author Daniela Costa da Silva
 * @version 1.0
 * @since 2026-05-28
 */
public class BankAccount {
    private double balance;
    
    /**
     * Construtor padrão que inicializa a conta bancária.
     * 
     * @pre Nenhum pré-requisito necessário.
     * 
     * @post O saldo da conta é definido como R$ 1000,00.
     *       O objeto BankAccount é criado em estado válido.
     */
    public BankAccount() {
        this.balance = 1000.0;
    }
    
    /**
     * Deposita um valor positivo na conta bancária.
     * 
     * @param amount O valor a ser depositado. Deve ser maior que zero.
     * 
     * @pre amount > 0
     *      amount é um número válido (não null, não NaN, não infinito)
     * 
     * @post Se o depósito for bem-sucedido:
     *       balance == balance@pre + amount
     *       Uma mensagem de confirmação é exibida.
     *       Retorna true.
     *       
     *       Se o depósito falhar (amount <= 0):
     *       balance permanece inalterado (balance == balance@pre)
     *       Uma mensagem de erro é exibida.
     *       Retorna false.
     * 
     * @return true se o depósito foi realizado com sucesso,
     *         false caso contrário (valor inválido).
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount. Must be positive.");
            return false;
        }
        this.balance += amount;
        System.out.printf("Deposited R$ %.2f. New balance: R$ %.2f\n", amount, this.balance);
        return true;
    }
    
    /**
     * Saca um valor positivo da conta bancária, se houver saldo suficiente.
     * 
     * @param amount O valor a ser sacado. Deve ser maior que zero
     *               e menor ou igual ao saldo atual.
     * 
     * @pre amount > 0
     *      amount <= balance (saldo suficiente)
     *      amount é um número válido (não null, não NaN, não infinito)
     * 
     * @post Se o saque for bem-sucedido:
     *       balance == balance@pre - amount
     *       balance >= 0 (nunca negativo)
     *       Uma mensagem de confirmação é exibida.
     *       Retorna true.
     *       
     *       Se o saque falhar (amount <= 0 ou amount > balance):
     *       balance permanece inalterado (balance == balance@pre)
     *       Uma mensagem de erro apropriada é exibida.
     *       Retorna false.
     * 
     * @return true se o saque foi realizado com sucesso,
     *         false caso contrário (valor inválido ou saldo insuficiente).
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount. Must be positive.");
            return false;
        }
        if (amount > this.balance) {
            System.out.printf("Insufficient funds! Balance: R$ %.2f, Requested: R$ %.2f\n", this.balance, amount);
            return false;
        }
        this.balance -= amount;
        System.out.printf("Withdrew R$ %.2f. New balance: R$ %.2f\n", amount, this.balance);
        return true;
    }
    
    /**
     * Retorna o saldo atual da conta bancária.
     * 
     * @pre Nenhum pré-requisito.
     * 
     * @post O saldo permanece inalterado (somente leitura).
     *       Nenhum efeito colateral no estado do objeto.
     * 
     * @return O saldo atual como um valor double.
     */
    public double getBalance() {
        return balance;
    }
}