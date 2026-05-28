//Bruna Romero 11913896
//Daniela Costa da Silva 14613625


public class Item {
    private String name;
    
    /**
     * Construtor que cria um item com um nome específico.
     * 
     * @param name O nome do item. Não pode ser null.
     * 
     * @pre name != null
     *      name não é uma string vazia (recomendado)
     * 
     * @post Um objeto Item é criado com o nome especificado.
     *       O nome não pode ser alterado após a criação (imutável).
     */
    public Item(String name) { 
        this.name = name;
    }
    
    /**
     * Retorna o nome do item.
     * 
     * @pre Nenhum pré-requisito.
     * 
     * @post O estado do item permanece inalterado.
     *       Nenhum efeito colateral.
     * 
     * @return O nome do item como String.
     */
    public String getName() {
        return name;
    }
}