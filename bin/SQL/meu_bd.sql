create table if not exists professor (
	idProfessor int not null primary key,
    nomeProfessor varchar(50) not null
) default charset=utf8mb4;

use meu_db;

delete from professor_efetivo where nomeProfEfetivo = 'Manel'