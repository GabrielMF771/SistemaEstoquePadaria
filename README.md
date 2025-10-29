# Sistema de Estoque de uma Padaria em Java

## Propósito e Escopo
Este projeto implementa um sistema de gerenciamento de produtos e estoque para uma padaria, em MySQL e interface de linha de comando. Escopo: operações básicas de consulta e manutenção de produtos, categorias e estoque, integração com banco de dados e demonstração funcional.

## Objetivos Específicos
1. Manter cadastro de categorias de produtos.
2. Manter cadastro de produtos associados a categorias.
3. Controlar e exibir o estoque por produto.
4. Fornecer persistência em MySQL via padrão DAO.
5. Permitir execução e demonstração no ambiente de desenvolvimento (Maven).

## Funcionalidades Principais
1. Listar categorias e produtos.
2. Consultar quantidade em estoque por produto.
3. CRUD básico (DAO) para Categoria, Produto e Estoque.
4. Conexão configurável com MySQL.

## Histórias de Usuário (HU) selecionadas para desenvolvimento
- HU-01: Listar produtos cadastrados — Implementada.
- HU-02: Listar categorias — Implementada.
- HU-03: Visualizar estoque de um produto — Implementada.
- HU-04: Cadastrar/Editar/Remover produto — Suporte via DAO; UI básica pode estar parcial.
- HU-05: Cadastrar/Editar/Remover categoria — Suporte via DAO; UI básica pode estar parcial.

---

## Configuração do Banco de Dados:
>
> * Configure as credenciais do banco de dados no arquivo `src/main/java/ucb/estudo/dao/ConexaoMySQL`.
> * 2. Criar o banco e rodar os scripts SQL (fornecidos no README em [Scripts SQL](#scripts-sql)

---

## Estrutura do Projeto

* `ucb.estudo.app`
    Contém as classes principais do aplicativo:

  * `CategoriaMenu` – gerencia o menu de categorias de produtos
  * `ProdutoMenu` – gerencia o menu de produtos
  * `SistemaPadaria` - classe principal que inicia o sistema
  * `InputUtils` - utilitário para entrada de dados do usuário


* `ucb.estudo.dao`
  Contém classes para acesso ao banco de dados:

    * `ConexaoMySQL` – gerencia conexão com MySQL
    * `CategoriaDAO` – implementa operações CRUD para categorias
    * `ProdutoDAO` – implementa operações CRUD para produtos
    * `EstoqueDAO` – implementa operações CRUD para estoque
    * `DAO` - interface genérica para operações CRUD

* `ucb.estudo.model`
  Contém a classe modelo:

    * `Produto` – representa os produtos
    * `Categoria` – representa as categorias de produtos
    * `Estoque` – representa o estoque de produtos

---

## Diagrama do Banco de Dados

![Diagrama do banco](https://imgur.com/kfHgTZl.png)

---

## Scripts SQL

Script para criar as tabelas no banco de dados MySQL:

```sql
CREATE DATABASE IF NOT EXISTS Padaria;
USE Padaria;

-- Criação das Tabelas

-- CATEGORIA (Tipo de Produto)
CREATE TABLE Categoria (
    categoria_id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);

-- PRODUTO
CREATE TABLE Produto (
    produto_id INT PRIMARY KEY AUTO_INCREMENT, 
    nome VARCHAR(150) NOT NULL,
    preco_custo DECIMAL(10,2),
    preco_venda DECIMAL(10,2),
    peso DECIMAL(10,3),
    categoria_id INT,
    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id) REFERENCES Categoria(categoria_id)
);

-- ESTOQUE
CREATE TABLE Estoque (
    estoque_id INT PRIMARY KEY AUTO_INCREMENT,
    quantidade INT NOT NULL DEFAULT 0,
    produto_id INT NOT NULL,
    CONSTRAINT fk_estoque_produto FOREIGN KEY (produto_id) REFERENCES Produto(produto_id)
);
```

Script para popular as tabelas com dados iniciais:

```sql
USE Padaria;

-- CATEGORIAS
INSERT INTO Categoria (nome, descricao) VALUES 
('Pães', 'Produtos de panificação variados como francês, integral e de forma'),
('Bolos', 'Bolos caseiros, recheados e decorados'),
('Salgados', 'Salgados assados e fritos, como coxinha, empada e pastel'),
('Doces', 'Doces diversos como brigadeiro, doce de leite e tortas'),
('Bebidas', 'Bebidas quentes e frias, incluindo café, sucos e refrigerantes'),
('Lanches', 'Sanduíches e lanches rápidos para consumo imediato'),
('Outros', 'Produtos que não se enquadram nas categorias anteriores');

-- PRODUTOS
INSERT INTO Produto (nome, preco_custo, preco_venda, peso, categoria_id) VALUES
('Pão Francês', 0.30, 0.50, 0.050, 1),
('Bolo de Chocolate', 18.00, 25.00, 1.200, 2),
('Croissant', 3.00, 4.50, 0.100, 3),
('Pão de Queijo', 0.80, 1.20, 0.070, 3),
('Torta de Frango', 22.00, 35.00, 1.500, 3),
('Sonho de Creme', 2.50, 3.80, 0.120, 4),
('Pão Integral', 4.50, 6.50, 0.400, 1),
('Biscoito Caseiro', 8.00, 12.00, 0.300, 7),
('Fatia de Pizza', 4.50, 7.00, 0.250, 7),
('Café Expresso', 2.50, 5.00, 0.080, 5);

-- ESTOQUE
INSERT INTO Estoque (produto_id, quantidade) VALUES
(1, 1000),  -- Pão Francês
(2, 20),    -- Bolo de Chocolate
(3, 50),    -- Croissant
(4, 200);   -- Pão de Queijo
```

## Observações finais
- Configure corretamente `ConexaoMySQL` antes de executar.
- Caso precise, os scripts SQL de criação e povoamento estão incluídos no README original e podem ser salvos como arquivos `.sql` e executados no cliente MySQL.