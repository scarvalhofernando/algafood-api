alter table restaurante add ativo tinyint(1) not null;
UPDATE restaurante SET ativo = true;