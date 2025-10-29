package ucb.estudo.model;

public class Estoque {
    private int estoqueId;
    private int quantidade;
    private int produtoId;

    // Associação com Produto
    private Produto produto;

    // Construtores
    public Estoque() {}

    public Estoque(int estoqueId, int quantidade, int produtoId) {
        this.estoqueId = estoqueId;
        this.quantidade = quantidade;
        this.produtoId = produtoId;}

    // Getters e Setters
    public int getEstoqueId() {
        return estoqueId;
    }

    public void setEstoqueId(int estoqueId) {
        this.estoqueId = estoqueId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Estoque{" +
                "estoqueId=" + estoqueId +
                ", quantidade=" + quantidade +
                ", produtoId=" + produtoId +
                '}';
    }
}
