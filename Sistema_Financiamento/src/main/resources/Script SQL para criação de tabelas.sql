Create database sisFinanciamento

CREATE TABLE pessoa (
    id VARCHAR(50) PRIMARY KEY NOT NULL,
    nome VARCHAR(60),
    telefone VARCHAR(20),
    inscricaoMunicipal VARCHAR(50),
    tituloEleitor VARCHAR(50),
    dataAposentadoria DATE,
    tipoPessoa VARCHAR(50)
);

CREATE TABLE emprestimo (
    id int AUTO_INCREMENT PRIMARY KEY,
    quantidadeMeses INT,
    quantidadeParcelasPagas INT,
    valorJuros DOUBLE,
    valorEmprestimo DOUBLE,
    valorParcela DOUBLE,
    valorTotalEmprestimo DOUBLE,
    tipoFinanciamento VARCHAR(50),
    idPessoa VARCHAR(50),
    FOREIGN KEY (IdPessoa) REFERENCES pessoa(id)
);