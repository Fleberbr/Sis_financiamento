## Inicio
Olá, tudo bem ? 
Bem vindo ao meu primeiro programa com as aplicações de orientação a objeto, e a utilização do JDBC.



## Oque é necessário para rodar a aplicação ? 

Baixar e instalar o banco de dados MySQL Community Server 8.3.0 Innovation
Efetuar a instalação a versão full do MySQl.
<href>https://dev.mysql.com/downloads/mysql/</href>


Instalar o MySQLWorkbench (caso não tinha sido instalado na opção acima), 

Baixar o mysql-connector-j-8.3.0
Copiar o arquivo "mysql-connector-j-8.3.0.jar", colocar em uma pasta de sua preferencia onde possa ser guardado as libs do projeto

Sugestão pessoal:
C:\java-libs\jdbc-connector


## Configurar a senha no Workbench.
Criar um usuário no workbench.

Importante! 
Os campos abaixo, precisam ser atualizado no arquivo db.properties do projeto

user=developer
password=101112
dburl=jdbc:mysql://localhost:3306/sisfinanciamento


## No Workbanch - 
Criar a base de dados (Schema) e criar as tabelas.
Executar o Script SQl que está na pasta resources.

sisEmprestimo\br.com.lira.portifolio.sistema_emprestimo\src\main\resources


