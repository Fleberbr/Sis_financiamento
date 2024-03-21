tipopessoatipopessoaCREATE TABLE emprestimo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quantidadeMeses INT,
    quantidadeParcelasPagas INT,
    valorEmprestimo DOUBLE,
    valorJuros DOUBLE,
    valorTotalEmprestimo DOUBLE,
    idTipoFinanciamento INT,
    IdPessoa VARCHAR(20),
    FOREIGN KEY (idTipoFinanciamento) REFERENCES TipoEmprestimo(Id),
    FOREIGN KEY (IdPessoa) REFERENCES pessoa(Id)
);

CREATE TABLE pessoa (
    Id VARCHAR(20) PRIMARY KEY NOT NULL,
    nome VARCHAR(60),
    telefone VARCHAR(20),
    inscricaoMunicipal VARCHAR(20),
    dataAposentadoria DATE,
    IdtipoPessoa INT,
    FOREIGN KEY (IdtipoPessoa) REFERENCES TipoPessoa(Id)
);

CREATE TABLE TipoEmprestimo (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    descricao TEXT
);

CREATE TABLE TipoPessoa (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    descricao TEXT
);
