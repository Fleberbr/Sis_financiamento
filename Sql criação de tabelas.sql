CREATE TABLE TipoEmprestimo (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    tipoEmprestimo VARCHAR(20)
);


CREATE TABLE pessoa (
    Id VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(50),
    telefone VARCHAR(20),
    tipoPessoa VARCHAR(20),
    tituloEleitor VARCHAR(20),
    dataAposentadoria date,
    numInscricaoMunicipal VARCHAR(20)
);

CREATE TABLE emprestimo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    valorEmprestimo DOUBLE,
    quantidadeMeses INT,
    quantidadeParcelasPagas INT,
    idTipoFinanciamento INT,
    IdPessoa VARCHAR(20),
    FOREIGN KEY (idTipoFinanciamento) REFERENCES TipoEmprestimo(Id),
    FOREIGN KEY (IdPessoa) REFERENCES pessoa(Id)
);