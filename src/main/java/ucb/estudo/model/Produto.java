package ucb.estudo.model;

import java.util.List;

public class Produto {
    private int id;
    private String nome;
    private double precoVenda;
    private double precoCusto;
    private double peso;
    private int quantidadeEstoque;
    private int categoriaId;

    // Associação com Estoque
    private List<Estoque> estoques;

    // Construtores
    public Produto() {}

    public Produto(int id, String nome, double precoVenda, double precoCusto, double peso, int categoriaId) {
        this.id = id;
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.precoCusto = precoCusto;
        this.peso = peso;
        this.categoriaId = categoriaId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public List<Estoque> getEstoques() {
        return estoques;
    }

    public void setEstoques(List<Estoque> estoques) {
        this.estoques = estoques;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", precoVenda=" + precoVenda +
                ", precoCusto=" + precoCusto +
                ", peso=" + peso +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
