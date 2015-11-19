CREATE TABLE clientes(
id serial primary key,
nome varchar(50) not null,
RG char(10) unique not null,
telefone varchar(11) not null);

select * from clientes

alter table clientes 
add constraint un_RG unique(RG);

--drop table clientes

create table aviao(
id serial primary key,
nome varchar(20) unique not null);  

select * from aviao
alter table aviao add constraint uniq_nome UNIQUE (nome);

--drop table aviao cascade

create table cidades(
id serial primary key,
nome varchar(50) not null,
uf char(2) not null);

--drop table cidades cascade

----   INSERTS DAS CIDADES
insert into cidades (nome, uf) values
('PORTO ALEGRE', 'RS'),
('SAO PAULO', 'SP'),
('RIO DE JANEIRO','RJ'),
('BELO HORIZONTE','MG'),
('MACEIO','AL'),
('NATAL','RN');

select * from cidades

create table pontes_aereas(
id serial primary key,
origem integer references cidades,
destino integer references cidades);

select * from pontes_aereas

--drop table pontes_aereas cascade

-------INSERTS DAS PONTES
insert into pontes_aereas(origem, destino) values
(1, 2),
(2, 1),
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(6, 5),
(5, 4),
(4, 3);

create table voo(
id serial primary key,
horario_voo timestamp not null,
ponte integer references pontes_aereas,
aviao integer references aviao);

--drop table voo;

create table passagem(
id serial primary key,
cliente integer references clientes,
voo integer references voo,
horario_compra timestamp not null);

--drop table passagem;

select * from voo

select * from pontes_aereas

----Select's para os Dao
 
------Listar Voo

select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id AS ponte, voo.id as voo, voo.horario_voo, voo.aviao, a.nome 
from cidades as o
inner join pontes_aereas as p
on o.id = p.origem
inner join cidades as d
on d.id = p.destino
inner join voo
on p.id = voo.ponte
inner join aviao as a
on voo.aviao = a.id 
ORDER BY voo.id

------Listar Voo por id
select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome
from cidades as o
inner join pontes_aereas as p 
on o.id = p.origem
inner join cidades as d
on d.id = p.destino
inner join voo
on p.id = voo.ponte
inner join aviao as a
on voo.aviao = a.id
where voo.id = 1

----Listar por origem
select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome--não foi renomeada pois não usarei pelo banco
from cidades as o
inner join pontes_aereas as p 
on o.id = p.origem
inner join cidades as d
on d.id = p.destino
inner join voo
on p.id = voo.ponte
inner join aviao as a
on voo.aviao = a.id
where p.origem = 1

----Listar Pontes

SELECT d.id, d.nome, d.uf,o.id, o.nome, o.uf, p.id FROM cidades as o 
inner join pontes_aereas as p
on o.id = p.origem
inner join cidades as d
on d.id = p.destino

----Listar Pontes por Id
SELECT d.id as ID_Destino, d.nome as Destino, d.uf,o.id as ID_Origem, o.nome as Origem, o.uf, p.id as Ponte FROM cidades as o 
inner join pontes_aereas as p
on o.id = p.origem
inner join cidades as d
on d.id = p.destino
where p.id = 9

----Listar Passagem
select o.id, o.nome, o.uf, d.id, d.nome, d.uf, p.id, voo.id, voo.horario_voo, voo.aviao, a.nome, pass.id, pass.cliente, pass.horario_compra, c.nome, c.telefone,c.RG 
from cidades as o
inner join pontes_aereas as p
on o.id = p.origem
inner join cidades as d
on d.id = p.destino
inner join voo
on p.id = voo.ponte
inner join aviao as a
on voo.aviao = a.id
inner join passagem as pass
on pass.voo = voo.id
inner join clientes as c
on c.id = pass.cliente
order by pass.id
--where pass.id = 1

select * from pontes_aereas

select * from voo 

select * from passagem

select * from clientes

------------------------RASCUNHOS------------

/*		Testes (rascunhos de SQL)
select p.id, o.nome, d.nome from pontes_aereas as p inner join cidades as origem, cidades as destino on
origem.id = p.origem and destino.id = p.destino
 
select p.id, o.nome as Origem, d.nome as Destino from pontes_aereas p
inner join cidades as o on o.id = p.origem
inner join cidades as d on d.id = p.destino

select v.id, o.nome as Origem, o.uf, d.nome as Destino, d.uf, v.horario_voo as Partida, from voo inner join (pontes_aereas p
inner join cidades as o on o.id = p.origem
inner join cidades as d on d.id = p.destino)*/

SET DateStyle TO 'DMY'

ALTER DATABASE Venda_Passagem SET DateStyle TO ISO, DMY;

SHOW DateStyle;

SELECT * FROM clientes WHERE telefone = '3248-0755'



----INSERT's para  a massa de DADOS  
insert into clientes(nome, telefone, RG) values 
('jose','3248-0755 ', '8888888888'),
('joao','3248-0755 ', '4444444444'),
('maria','3248-0755 ', '5555555555'),
('adolph','3248-0755 ', '6666666666'),
('hitler','3248-0755 ', '7777777777');

insert into clientes(nome, telefone, RG) values ------para testar delete nos crud
('jose','3248-0755 ', '1298345678'),
('joao','3248-0755 ', '0934526754'),
('maria','3248-0755 ', '1652347689'),
('adolph','3248-0755 ', '0974324512'),
('hitler','3248-0755 ', '0973421324');

select * from clientes

insert into aviao (nome) values
('boing 266'),
('boing 270'),
('boing 268'),
('boing 269');

insert into voo (horario_voo, ponte, aviao) values
('2016-04-13 19:15:00', 3,1),
('2017-10-10 22:05:00', 3,16),
('2017-03-23 04:05:00', 3,19),
('2017-10-03 14:00:00', 3,20),
('2017-01-11 11:00:00', 3,23),
('2017-04-30 07:00:00', 3,17),
('2017-04-07 09:00:00', 3,2);

insert into passagem (cliente, voo, horario_compra) values
(1, 3, '2016-10-13 16:15:00'),
(2, 5, '2016-04-10 12:15:00'),
(15, 7, '2016-07-20 10:15:00'),
(12, 4, '2016-11-22 20:15:00'),
(14, 2, '2017-01-01 01:15:00'),
(5, 1, '2016-04-13 19:15:00');


select * from voo order by id

select * from clientes

delete from aviao where id > 3 and id <14

select* from passagem

select nome from cidades where id = 4

select * from pontes_aereas